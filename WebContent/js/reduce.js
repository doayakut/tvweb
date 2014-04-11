jQuery(document)
		.ready(
				function($) {
					$('h4').each(function(){
						$(this).next().children('p:gt(-3)').remove();
					});
				});