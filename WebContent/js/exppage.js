$( document ).ready( function() {	
	
	
	var controller;
	
	var timer = {
		'lastStart' : 0,
		'state': 'paused',
		'elapsed': 0,
		'startTimer' : function(){
			if(this.state == 'paused'){
				this.lastStart = $.now();
				this.state = 'going';
			}
		},
		'pauseTimer' : function(){
			if(this.state == 'going'){
				this.elapsed += $.now() - this.lastStart;
				this.state = 'paused';
			}
		}
	};


	
	var Content = function(){
		console.log('new content');
		this.element = $('#content');

		this.viewtype = this.element.data('viewtype');
		this.folder = this.element.data('folder');
		
	};
	
	Content.prototype = {
		constructor: Content,
		change: function(src){
			var address = '/tvweb2/testpages/' + this.folder + "/"+ src;
			console.log(address);
			$('#content').load(address);
			
			controller.setState('stop');
			timer.startTimer();
		}
	
	};

	
	var content = new Content();
	
	$(document).on('click', 'a', function(event){
		event.preventDefault();
		content.change($(event.target).attr('href'));
		alert("Loaded: " + $(event.target).attr('href') );
	});
	
	var Controller = function(){
		console.log('new controller');
		this.element = $('#controller');
		this.state = 'start'; 
		this.original = false;
		
		var newClick = this.click.bind(this);
		var newLoaded = this.loaded.bind(this);
		$(document).on(
            'click', '#controller', newClick
        );
		
		$(document).on(
            'loaded', this.element, newLoaded
        );
	};
	
	Controller.prototype = {
		constructor: Controller,
		setState: function(state){
			if(state == 'loading'){
				this.element.html('<i class="icon-spinner icon-spin"> </i> Loading');
				this.element.removeClass('btn-success');
				this.element.removeClass('btn-danger');
				this.element.addClass('btn-default');
				this.element.attr('disabled',true);
			}
			if(state == 'stop'){
				this.element.html('Stop');
				this.element.removeClass('btn-success');
				this.element.removeClass('btn-default');
				this.element.addClass('btn-danger');
				this.element.removeAttr('disabled');
		    	
			}
			if(state == 'sending'){
				this.element.html('<i class="icon-spinner icon-spin"> </i> Sending Data');
				this.element.removeClass('btn-success');
				this.element.removeClass('btn-danger');
				this.element.addClass('btn-default');
				this.element.attr('disabled',true);
			}
	    	this.state = state;
		},

		
		click : function(event){
			
			if(this.state == 'start'){
				this.setState('loading');

				content.change(content.viewtype + '.html');
			}
			else if(this.state == 'stop'){
				alert('clicked?');
				this.setState('sending');
				
				timer.pauseTimer();
					
				var elapsedArg = '&elapsed=' + (timer.elapsed/1000);
				window.location.href = '/tvweb2/home?action=next' + elapsedArg;

			}

		},
			
		loaded : function(event){
		}
			
		
	};
	
	controller = new Controller();
	
});	

