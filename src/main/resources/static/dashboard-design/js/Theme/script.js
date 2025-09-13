$(document).ready(function() {
	$('button[id=rst]').click(function(){
		clearAddCache();
		clearImage();
		window.location.href = '/admin/theme';
		});

});
function clearAddCache() {
	$('input[name=id]').val("");
	$('input[name=name]').val("");
	$('input[name=description]').val("");
	/*$('img').​
	$('input[name=SousActdescrip]').val("");*/
	
}
function clearImage(){
	var x = document.getElementById("base64image");
	x.style.display="none";
}