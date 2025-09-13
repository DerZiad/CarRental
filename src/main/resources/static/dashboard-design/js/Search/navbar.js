var themes = []
$(document).ready(function() {
	$('ul.nav li.dropdown').hover(function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(200).fadeIn(500);
		refreshActivite();
	}, function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(200).fadeOut(500);
	});

});
function refreshActivite() {
	$.ajax({
		url: '/api/activite/theme',
		type: 'get',
		data: {},
		success: function(response) {
			themes = response;
			var contenue = "";
			for (the of themes) {
				contenue = contenue + '<li ><a style="color:black" href="/'+the['label']+'">' + the['label'] + '</a></li>';
			}
			console.log(themes);
		
		$('.dropdown-menu').html(contenue);
	}
	})
}
