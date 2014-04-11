jQuery(document).ready(function ($) {
	
    localStorage['tvweb_states'] = JSON.stringify(new Array(
        "tvweb_intro",
        "tvweb_init",
        "tvweb_instruct",
        "tvweb_prestudy",
        "tvweb_general",
        "tvweb_training",
        "tvweb_answer1",
        "tvweb_evaluation1",
        "tvweb_answer2",
        "tvweb_evaluation2",
        "tvweb_answer3",
        "tvweb_evaluation3",
        "tvweb_answer4",
        "tvweb_evaluation4",
        "tvweb_answer5",
        "tvweb_evaluation5",
        "tvweb_answer6",
        "tvweb_evaluation6",
        "tvweb_experience",
        "tvweb_screenshots",
        "tvweb_submit"));

    var toggleDiv = function(name){

        $('div[id^="tvweb_"').hide();
        $('div[id="' + name + '"]').show();
        
        var states = JSON.parse(localStorage['tvweb_states']);
        var curr = states.indexOf(name);
        localStorage['tvweb_state'] = curr;
        
		$('#questionnaire-header').text($('h2', $('div#'+name)).text());
		
		$('#qbutton').show();
		if(curr != 0)
			$('#bbutton').removeAttr('disabled');
		else
			$('#bbutton').attr('disabled', true);
		
			
		
		if(curr == states.length - 1 )
			$('#qbutton').hide();
		else if(curr == states.length - 2 )
			$('#qbutton').html(' Submit ');
		else
			$('#qbutton').html(' Continue <i class="icon-double-angle-right"></i> ');
	};
	
	var isElementEmpty = function(el){
		if(getElementValue(el) === undefined || getElementValue(el) === '' || getElementValue(el) == null)
			return true;
		else 
			return false;
	};
	
	var getElementValue = function(el){
		if(el.attr('type') == 'radio')
			return $('input[name="' + el.attr('name') + '"]:checked').val();
		else
			return el.val();
	};
	
	var verifyField = function(name, alert){
		var empty = false;
		var field = $('[name="' + name + '"]');
		var sname = name.replace('tvweb_input_', ''); 
		
//		if(sname == 'aim1' || sname == 'aim2' || sname == 'aim3' || name == 'edu'){
//			if((getElementValue(field) == "0" && $('[id="' + sname + '_str"]').val() == '') || isElementEmpty(field))
//				empty = true;
//		} 
//		else  
		if(sname == 'questionnaire1_8' || sname == 'questionnaire2_8' || sname == 'questionnaire3_8' || sname == 'questionnaire4_8' || sname == 'questionnaire5_8' || sname == 'questionnaire6_8' || sname.indexOf("_str") !== -1){
			
		}
		else {
			empty = isElementEmpty(field);

			if(alert){
				if(empty){

					if(name.indexOf('questionnaire') !== -1 || name.indexOf('familiarity') !== -1 || name.indexOf('pref') !== -1){
						var control = field.closest('.parent-form-control');
						control.first().addClass('text-danger');
					}
					else {
						var control = field.closest('.form-group');
			        	control.addClass("has-error");
					}
				}
				else{
					if(name.indexOf('questionnaire') !== -1 || name.indexOf('familiarity') !== -1 || name.indexOf('pref') !== -1){
						console.log(name + ": " + isElementEmpty(field));
						var control = field.closest('.parent-form-control');
						control.first().removeClass('text-danger');
					}
					else {
						console.log(name + ": " + isElementEmpty(field));
						var control = field.closest('.form-group');
						if(name == "tvweb_input_edu")
							console.log(control);
			        	control.removeClass("has-error");
					}
				}
			}
		}
		return empty;
	};
	
	var verifyFields = function(alert){
		var $eles = $("[name^='tvweb_input_']");

		// CHECK USER ID
		var empties = [];
		$eles.each(function( index ) {
			var name = $(this).attr('name');
			var empty = verifyField(name, alert);
			if(empty)
				empties.push(name);
		});
		
		return empties;
	    
	};
    
	var resetForm = function(){
		// empty all tvweb_input_* 

        localStorage['tvweb_state'] = "0";
        localStorage['tvweb_submitted'] = "true";
        
        Object.keys(localStorage).forEach(function(key){
            if(/^(tvweb_input_)/.test(key)){
            	localStorage.removeItem(key);
            }
        });
	};
	
	
	
	var populateForm = function(){
		// fill up all fields with tvweb_input_*
        Object.keys(localStorage).forEach(function(key){
            if(/^(tvweb_input_)/.test(key)){
                var value = localStorage.getItem(key);
                var field = $('[name=' + key + ']');
                if(field.attr('type') == 'radio'){
                    $('input[name=' + key + '][value=' + value + ']').prop('checked', true);
                }
                if(field.attr('type') == 'text' || field.is('textarea') || field.is('select')){
                	field.val(value);
                }
            }
        });
	};
	
	var submitForm = function(){
		
		// Check fields
	    $('#tvweb_submit_label').text('Checking Data...');
	    var empties = verifyFields(true);
	    if(empties.length != 0){
	    	var input = $('[name="'+empties[0]+'"]');
	    	var pagename = input.closest('div[id^="tvweb_"]').attr('id');
	    	console.log('empty: ' + empties[0] + ' in '+ pagename);
	    	input.focus();
	    	toggleDiv(pagename);
	        return;
		}
	    

	    // check if username exists
		$.post('/tvweb2/home?action=get&what=user&username=' + $('[name="tvweb_input_userid"]').val() + '&type=' + $('[name="tvweb_input_usertype"]').val())
		.done(function(data){
			var isPermitted = false;
			if(data == "success"){
				isPermitted = true;
			}
			else if(data == "questionnaire exists"){
				var r=confirm("A previous entry for user already exists. Are you sure you would like to override the data?");
				if (r==true)
					isPermitted = true;
			}
			
			if(isPermitted ){
				
		        $('#tvweb_submit_label').text('Sending Data');
		        // send  data with ajax and wait for "success" string
		        alert("Please call researcher for data verification");
		        localStorage['tvweb_user_'+ $('[name="tvweb_input_userid"]').val()] = $('#questionnaire-form').serialize();
		        alert(localStorage['tvweb_user_'+ $('[name="tvweb_input_userid"]').val()]);
		        
		    	console.log($('#questionnaire-form').serialize());
		    	$.post('/tvweb2/home?' + $('#questionnaire-form').serialize()).done(function(data){
		    		if(data == "success"){
		    			$('#tvweb_submit_label').text('Successful data entry');
		    			$('#tvweb_submit_icon').removeClass('icon-spin');
		    			$('#tvweb_submit_icon').removeClass('icon-spinner');
		    			$('#tvweb_submit_icon').addClass('icon-ok');
		    			resetForm();
		    		}
		    		else{
		    			$('#tvweb_submit_label').html('Could not submit data:' + data);
		    			$('#tvweb_submit_icon').removeClass('icon-spin');
		    			$('#tvweb_submit_icon').removeClass('icon-spinner');
		    			$('#tvweb_submit_icon').addClass('icon-remove');
		    		}	
		    	}).fail(function() {
	
	    			$('#tvweb_submit_label').text('Could not submit data ');
	    			$('#tvweb_submit_icon').removeClass('icon-spin');
	    			$('#tvweb_submit_icon').removeClass('icon-spinner');
	    			$('#tvweb_submit_icon').addClass('icon-remove');
		    	});
	    	}
			else{
				alert("One or more empty fields");
				var input = $('[name="tvweb_input_userid"]');
	        	var control = input.closest('.form-group');
	        	var pagename = input.closest('div[id^="tvweb_"]').attr('id');
	        	control.addClass("has-error");
	        	input.focus();
	        	toggleDiv(pagename);
			}
	    })
	    .fail(function() {
			alert('Username problem. Please consult with researcher');
			var input = $('[name="tvweb_input_userid"]');
        	var control = input.closest('.form-group');
        	var pagename = input.closest('div[id^="tvweb_"]').attr('id');
        	control.addClass("has-error");
        	input.focus();
        	toggleDiv(pagename);
    	});

	};
	

    if(localStorage['tvweb_submitted'] != "false"){
    	localStorage['tvweb_submitted'] = "false";
        toggleDiv("tvweb_intro");
    }
    else{
        var states = JSON.parse(localStorage['tvweb_states']);
        var curr = parseInt(localStorage['tvweb_state']);
        var curr_str = states[curr];

    	console.log(curr + ": " + curr_str + ": " + localStorage['tvweb_submitted']);
        toggleDiv(curr_str);
        populateForm();
        verifyFields(false);
        
        if(curr_str == "tvweb_submit")
        	submitForm();

        if($('[name="tvweb_input_edu"]:checked').val() == 0) {
            $('[name="tvweb_input_edu_str"]').attr('disabled', false);
            $('[name="tvweb_input_edu_str"]').css('text-decoration','none');
        }
        if($('[name="tvweb_input_aim1"]').val() == 0) {
            $('[name="tvweb_input_aim1_str"]').attr('disabled', false);
            $('[name="tvweb_input_aim1_str"]').css('text-decoration','none');
        }
        if($('[name="tvweb_input_aim2"]').val() == 0) {
            $('[name="tvweb_input_aim2_str"]').attr('disabled', false);
            $('[name="tvweb_input_aim2_str"]').css('text-decoration','none');
        }
        if($('[name="tvweb_input_aim3"]').val() == 0) {
            $('[name="tvweb_input_aim3_str"]').attr('disabled', false);
            $('[name="tvweb_input_aim3_str"]').css('text-decoration','none');
        }
    }
    
    $('#bbutton').on('click', function(event){
    	var states = JSON.parse(localStorage['tvweb_states']);
        var curr = parseInt(localStorage['tvweb_state']);
        var prev = curr - 1;
        var prev_str = states[prev];

        localStorage['tvweb_state'] = prev;
        toggleDiv(prev_str);

    });

	$('#qbutton').on('click', function(event){

        var states = JSON.parse(localStorage['tvweb_states']);
        var curr = parseInt(localStorage['tvweb_state']);
        var curr_str = states[curr];
        var next = curr + 1;
        var next_str = states[next];


        if(curr_str != "tvweb_screenshots" ){
            localStorage['tvweb_state'] = next;
            toggleDiv(next_str);
        }
        else {
        	// Adjust divs
        	toggleDiv('tvweb_submit');
        	submitForm();
        	
        }
	});


    $('[id^="aim"]').on('change', function(event){
    	var aim_str = $(this).attr('id') + '_str';
        if($(this).val() == '0'){
            $('[id="' + aim_str + '"]').attr('disabled', false);
            $('[id="' + aim_str + '"]').css('text-decoration','none');
        }
        else {
            $('[id="' + aim_str + '"]').attr('disabled', true);
            $('[id="' + aim_str + '"]').css('text-decoration','line-through');
        }
    });
    $('[name="tvweb_input_edu"]').on('change', function(event){
    	var edu_str = 'tvweb_input_edu_str';
        if($(this).val() == '0'){
            $('[name="' + edu_str + '"]').attr('disabled', false);
            $('[name="' + edu_str + '"]').css('text-decoration','none');
        }
        else {
            $('[name="' + edu_str + '"]').attr('disabled', true);
            $('[name="' + edu_str + '"]').css('text-decoration','line-through');
        }
    });


    $('input:radio').on('change',function(event){
        localStorage[$(this).attr('name')] =  $(this).val();
        verifyField($(this).attr('name'), true);
    });

    $('input').on('keyup',function(event){
        localStorage[$(this).attr('name')] =  $(this).val();
        verifyField($(this).attr('name'), true);
    });

    $('textarea').on('keyup',function(event){
        localStorage[$(this).attr('name')] =  $(this).val();
        verifyField($(this).attr('name'), true);
    });

    $('select').on('change',function(event){
        localStorage[$(this).attr('name')] =  $(this).val();
        verifyField($(this).attr('name'), true);
    });
    
    $('input:radio').mousedown(function(event){
    	if(event.which == 3){
    		localStorage.removeItem($(this).attr('name'));
    		$(this).prop('checked', false);
    	}
        verifyField($(this).attr('name'), true);
    });
    
});