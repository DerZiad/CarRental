lieux = [];
function refreshLieux() {
	var country = $('select[name=pays]').val();
	var contenue = "";
	for (lieu of lieux) {
		if (lieu.country.keyCountry === country) {
			contenue = contenue + '<option value="' + lieu.id + '">' + lieu.label + '</option>';
		}
	}
	$('select[name=lieu]').html(contenue);
}
jQuery(document).ready(function() {
	$.ajax({
		url: '/api/lieux',
		type: 'get',
		data: {},
		success: function(response) {
			lieux = response;
			refreshLieux();
		}
		
	});
	$('select[name=pays]').change(refreshLieux);
});

/**
 *
 */