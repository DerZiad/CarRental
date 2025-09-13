$(document).ready(function() {
	$('button[id=rst]').click(function(){
		clearAddCache();
		window.location.href = '/admin/lieux';
		});

});

function clearAddCache() {
	$('input[name=id]').val("");
	$('input[name=name]').val("");
	$('input[name=description]').val("");
	document.getElementById('slct').value='NOM DU PAYS';
	/*$('img').​
	$('input[name=SousActdescrip]').val("");*/
	
}