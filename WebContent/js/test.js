jQuery(document).ready(function ($) {
	
 /**
 * 
 */
var count = 0;
	Object.keys(localStorage).forEach(function(key){
	            if(/^(tvweb_user_)/.test(key)){
	                var value = localStorage.getItem(key);
					var values = value.split('&tvweb_input_');
					var str = '';
                    					
                    for (var i = 0; i < values.length; i++) {
                      str += '<dt>' + values[i].split('=').join('</dt><dd>') + '</dd>';
                      // Do something with element i.
                    }
					var panel =  $('<div class="panel panel-default">'
							+ '<div class="panel-heading">'
							+ ' <h4 class="panel-title">'
							+ '<a data-toggle="collapse" data-parent="#accordion" href="#collapse'+ count + '">'
					        +	key.replace('tvweb_user_','')
					        + '</a>'
					        + '</h4>'
					        + '</div>'
					        + '<div id="collapse' + count + '" class="panel-collapse collapse">'
					        + '<div class="panel-body" style=""><dl class="dl-horizontal">'
					        + '<p style="word-wrap:break-word;">' + value + '</p>'
                            + '</dl></div>'
					        + '</div>'
					        + '</div>');
					$('#accordion').append(panel);
					count++;
	            }
	});
	
});