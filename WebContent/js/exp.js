jQuery(document)
		.ready(
				function($) {
					if(localStorage['tvweb_mobile_state'] == 'started'){
						// last start time = get current time
						localStorage['tvweb_start'] = new Date().getTime();
						if (localStorage['tvweb_elapsed'] == null)
							localStorage['tvweb_elapsed'] = 0;

						console.log("loading: start: "
								+ localStorage['tvweb_start']);
						console.log("loading: elapsed: "
								+ localStorage['tvweb_elapsed']);
					}
					var controller = $('<div class="controller exp-div exp-controller"></div>');
					var form = $('<form method="post" action=/tvweb2/home>');
					var elapse = $('<input id="elapsed" type="hidden" name="elapsed" value="0">');
					var url = $('<input id="url" type="hidden" name="url" value="">');
					var action = $('<input id="action" type="hidden" name="action" value="next">');
					var button = $('<button type="button" disable>');
					var sbutton = $('<button class="exp-btn exp-btn-default" disabled ><span class="exp-lead">Start</span></button> <!-- Add state to button -->');
					var cbutton = $('<button type="submit" disabled>');
					var qbutton = $('<button type="button" id="qbutton" disabled> ');
					function getCookie(c_name) {
						var c_value = document.cookie;
						var c_start = c_value.indexOf(" " + c_name + "=");
						if (c_start == -1) {
							c_start = c_value.indexOf(c_name + "=");
						}
						if (c_start == -1) {
							c_value = null;
						} else {
							c_start = c_value.indexOf("=", c_start) + 1;
							var c_end = c_value.indexOf(";", c_start);
							if (c_end == -1) {
								c_end = c_value.length;
							}
							c_value = unescape(c_value
									.substring(c_start, c_end));
						}
						return c_value;
					}

					var address = "/tvweb2/home?action=get&username=" + getCookie("username");
					// get question
					//alert(address);
					localStorage['tvweb_question'] = "";
					$.ajax({
						url : address
					}).done(function(data) {
						var res = data.split("|");
						$('#question-text').text(res[0]);
						$('#definition-text').text(res[1]);
					});
					var modaldiv = $('<div id="questionModal" class="exp-modal" hidden>'
							+ '<div class="exp-modal-dialog">'
							+ '<div class="exp-modal-content">'
							+ '<div class="exp-modal-body">'
							//+ '<button id="modal-start-button" type="button" class="exp-btn exp-btn-danger" style="float:right; width: 3em" data-dismiss="modal" aria-hidden="true">&times;</button>'
							+ '<div style="text-align:left; margin-top: 3em;">'
							+ '<h2>Question:</h2>'
							+ '<span id="question-text" style="font-family: Georgia; line-height: 130%; font-size:22px; font-weight: 500;"></span>'
							+ '</div>'
							+ '<hr style="color: #ddd;background-color: #ddd; height:2px"/>'
							+ '<div style="text-align:left">'
							+ '<span id="definition-text" style="font-family: Georgia; line-height: 130%; font-size:22px; font-weight: 500; color: #000"></span>'
							+ '</div>'
							+ '</div>'
							+ '</div><!-- /.modal-content -->'
							+ '</div><!-- /.modal-dialog -->'
							+ '</div><!-- /.modal -->');
					var span = $('<span id="controller-span">');
					var qspan = $('<span >');
					var cspan = $('<span >');
					var blockdiv = $('<div id="blockDiv"></div>');

					blockdiv.hide();

					controller.addClass('exp-controller');
					
					if(localStorage['tvweb_mobile_state'] == 'started'){
						button.addClass('exp-btn');
						button.addClass('exp-btn-danger');
						button.removeAttr('disabled');
						
						cbutton.addClass('exp-btn');
						cbutton.addClass('exp-btn-default');

						qbutton.addClass('exp-btn-question');
						qbutton.addClass('exp-btn-info');
						qbutton.removeAttr('disabled');
					}
					else {

						button.addClass('exp-btn');
						button.addClass('exp-btn-default');
						
						cbutton.addClass('exp-btn');
						cbutton.addClass('exp-btn-success');
						cbutton.removeAttr('disabled');
						

						qbutton.addClass('exp-btn');
						qbutton.addClass('exp-btn-default');

					}
					

					

					span.text('Stop');
					span.addClass('exp-lead');
					qspan.html('Question');
					qspan.addClass('exp-lead');
					cspan.html('Continue');
					cspan.addClass('exp-lead');

					button.prepend(span);
					qbutton.prepend(qspan);
					cbutton.prepend(cspan);
					form.prepend(url);
					form.prepend(elapse);
					form.prepend(action);
					form.prepend(qbutton);
					form.prepend(cbutton);
					form.prepend(button);
					form.prepend(sbutton);
					controller.prepend(form);

					$('body').children(":first").css('margin-top', '100px');
					$('body').prepend(controller);
					$('body').prepend(modaldiv);
					$('body').prepend(blockdiv);
					$('body').css('margin', '0px');
					$('body').css('padding', '0px');

					blockdiv.on('click', function(e) {
						// stop propagation and prevent default by returning
						// false
						return false;
					});
					$("#modal-start-button").on('click', function(event){
						localStorage['tvweb_start'] = new Date().getTime();

						console.log("unpause: elapsed:"
								+ parseInt(localStorage['tvweb_elapsed']));
						console.log("unpause: start:"
								+ parseInt(localStorage['tvweb_start']));
					});
					$('#qbutton').on('click', function(event){
						window.location.href = "/tvweb2/home?action=get&what=question";
					});
					button.on('click',function(event) {

							event.preventDefault();
							
							button.addClass('exp-btn-default');
							button.removeClass('exp-btn-danger');
							button.attr('disabled', true);
							
							cbutton.addClass('exp-btn-success');
							cbutton.removeClass('exp-btn-default');
							cbutton.removeAttr('disabled');
							
							qbutton.addClass('exp-btn-default');
							qbutton.removeClass('exp-btn-info');
							qbutton.attr('disabled', true);
							
							localStorage['tvweb_mobile_state'] = 'stopped';

							var elapsed = parseInt(localStorage['tvweb_elapsed']);
							var start = parseInt(localStorage['tvweb_start']);
							var currenttime = new Date()
									.getTime();

							// get time (current time - last
							// start time) + elapsed total
							elapsed = elapsed
									+ (currenttime - start);
							// update elapsed field (#elapsed)
							$("#elapsed").val(elapsed);
							$("#url").val(document.URL);
							console.log(document.URL);

							console.log("sending: elapsed:"
									+ elapsed);
							localStorage['tvweb_sending'] = 1;

							// zero elapsed time
							localStorage['tvweb_elapsed'] = 0;

							blockdiv.show();
							$("body").css("overflow", "hidden");
							

					});

					$('a')
							.on(
									'click',
									function(event) {
										if ($(this).hasClass('tag'))
											event.preventDefault();
										else if($(this).closest('li').hasClass('dropdown')){
											event.preventDefault();
										}
										else {
											if (event.target != "")
												window.location = '/tvweb2/home?action=exp&url='
														+ event.target;
										}
									});

					$(window).unload(function() {

										if (localStorage['tvweb_sending'] != 1) {

											var elapsed = parseInt(localStorage['tvweb_elapsed']);
											var start = parseInt(localStorage['tvweb_start']);
											var currenttime = new Date()
													.getTime();

											// get time (current time - last
											// start time) + elapsed total
											elapsed = elapsed
													+ (currenttime - start);
											localStorage['tvweb_elapsed'] = elapsed;

											console.log("leaving: elapsed: "
													+ elapsed);
										} else {
											localStorage['tvweb_sending'] = 0;
										}

									});

					
				});