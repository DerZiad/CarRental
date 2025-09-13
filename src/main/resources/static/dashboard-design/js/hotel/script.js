// Ziad Bougrine
const gold = "#fdd835";
const plat = "#b6b8c3";
deletetedId = null;
hotels = [];
lieux = [];
countries = [];
like = [];
$(function() {
	$('.select-stateForm').selectize({
		sortField: 'text'
	});
});

function deleteHotel(idHotel) {
	deletetedId = idHotel;
}

function refreshCountryByHotel(keyCountry) {
	var contenue = "";
	for (country of countries) {
		if (keyCountry == country.keyCountry) {
			contenue = contenue + '<option value="' + country.keyCountry + '" selected >' + country.valueCountry + '</option>';
		} else {
			contenue = contenue + '<option value="' + country.keyCountry + '">' + country.valueCountry + '</option>';
		}
	}
	$('select[name=countryEdit]').html(contenue);
}
function refreshLieuxByHotel(idLieu) {
	var country = $('select[name=countryEdit]').val();
	var contenue = "";
	for (lieu of lieux) {
		if (lieu.country.keyCountry === country) {
			if (lieu.idLieu == idLieu) {
				contenue = contenue + '<option value="' + lieu.id + '" selected >' + lieu.label + '</option>';
			} else {
				contenue = contenue + '<option value="' + lieu.id + '">' + lieu.label + '</option>';
			}

		}

	}
	$('select[name=stateEdit]').html(contenue);
}

function refreshLieuxEdit() {
	$.ajax({
		url: '/api/lieux',
		type: 'get',
		data: {},
		success: function(response) {
			lieux = response;
			var country = $('select[name=countryEdit]').val();
			var contenue = "";
			for (lieu of lieux) {
				if (lieu.country.keyCountry === country) {
					contenue = contenue + '<option value="' + lieu.id + '">' + lieu.label + '</option>';
				}
			}
			$('select[name=stateEdit]').html(contenue);
		}
	});

}

function makeEditHotel(idHotel) {
	$('input[name=idHotel]').val(idHotel);
	var selectedHotel = null;
	for (hotel of hotels) {
		if (hotel.id == idHotel) {
			selectedHotel = hotel;
		}
	}
	hotel = selectedHotel;
	$("input[name=hotelnameEdit]").val(hotel.nomHotel);
	for (let i = 1; i <= 5; i++) {
		if (i <= hotel.nombreEtoile) {
			$('#star' + i + 'Edit').css("color", gold);
		} else {
			$('#star' + i + 'Edit').css("color", plat);
		}
	}
	$("input[name=starEdit]").val(hotel.nombreEtoile);
	refreshCountryByHotel(hotel.ville.country.keyCountry);
	refreshLieuxByHotel(hotel.ville.idLieu);
}



function deleteAll() {
	for (hotel of hotels) {
		if ($('#checkbox' + hotel.id).is(":checked")) {
			$.ajax({
				type: "Delete",
				url: "/api/hotel?id=" + hotel.id,
				success: function(response) {
					refreshotels();
				}
			});
		}
	}
	refreshotels();
}

function updateStar(number) {
	$('input[name=star]').val(number);
}

function updateStarEdit(number) {
	$('input[name=starEdit]').val(number);
}

function starManagementEdit() {
	$('#star1Edit').click(function() {
		$('#star1Edit').css("color", gold);
		$('#star2Edit').css("color", plat);
		$('#star3Edit').css("color", plat);
		$('#star4Edit').css("color", plat);
		$('#star5Edit').css("color", plat);
		updateStarEdit(1);
	});

	$('#star2Edit').click(function() {
		$('#star1Edit').css("color", gold);
		$('#star2Edit').css("color", gold);
		$('#star3Edit').css("color", plat);
		$('#star4Edit').css("color", plat);
		$('#star5Edit').css("color", plat);
		updateStarEdit(2);
	});

	$('#star3Edit').click(function() {
		$('#star1Edit').css("color", gold);
		$('#star2Edit').css("color", gold);
		$('#star3Edit').css("color", gold);
		$('#star4Edit').css("color", plat);
		$('#star5Edit').css("color", plat);
		updateStarEdit(3);
	});

	$('#star4Edit').click(function() {
		$('#star1Edit').css("color", gold);
		$('#star2Edit').css("color", gold);
		$('#star3Edit').css("color", gold);
		$('#star4Edit').css("color", gold);
		$('#star5Edit').css("color", plat);
		updateStarEdit(4);
	});
	$('#star5Edit').click(function() {
		$('#star1Edit').css("color", gold);
		$('#star2Edit').css("color", gold);
		$('#star3Edit').css("color", gold);
		$('#star4Edit').css("color", gold);
		$('#star5Edit').css("color", gold);
		updateStarEdit(5);
	});
}


function starManagement() {
	$('#star1').click(function() {
		$('#star1').css("color", gold);
		$('#star2').css("color", plat);
		$('#star3').css("color", plat);
		$('#star4').css("color", plat);
		$('#star5').css("color", plat);
		updateStar(1);
	});

	$('#star2').click(function() {
		$('#star1').css("color", gold);
		$('#star2').css("color", gold);
		$('#star3').css("color", plat);
		$('#star4').css("color", plat);
		$('#star5').css("color", plat);
		updateStar(2);
	});

	$('#star3').click(function() {
		$('#star1').css("color", gold);
		$('#star2').css("color", gold);
		$('#star3').css("color", gold);
		$('#star4').css("color", plat);
		$('#star5').css("color", plat);
		updateStar(3);
	});

	$('#star4').click(function() {
		$('#star1').css("color", gold);
		$('#star2').css("color", gold);
		$('#star3').css("color", gold);
		$('#star4').css("color", gold);
		$('#star5').css("color", plat);
		updateStar(4);
	});
	$('#star5').click(function() {
		$('#star1').css("color", gold);
		$('#star2').css("color", gold);
		$('#star3').css("color", gold);
		$('#star4').css("color", gold);
		$('#star5').css("color", gold);
		updateStar(5);
	});
}

function clearAddCache() {

	$('input[name=hotelname]').val("");
	$('input[name=star]').val("1");
	$('#star1').css("color", gold);
	$('#star2').css("color", plat);
	$('#star3').css("color", plat);
	$('#star4').css("color", plat);
	$('#star5').css("color", plat);
	updateStar(1);
	$('#nomHotelError').html("");
	$('#nombreEtoileError').html("");
	$('#villeError').html("");
	refreshLieux();
	refreshCountry();
}

function refreshCountry() {
	var contenue = "";
	for (country of countries) {
		contenue = contenue + '<option value="' + country.keyCountry + '">' + country.valueCountry + '</option>';
	}
	$('select[name=country]').html(contenue);
}
function refreshLieux() {
	$.ajax({
		url: '/api/lieux',
		type: 'get',
		data: {},
		success: function(response) {
			lieux = response;
			var country = $('select[name=country]').val();
			var contenue = "";
			for (lieu of lieux) {
				if (lieu.country.keyCountry === country) {
					contenue = contenue + '<option value="' + lieu.id + '">' + lieu.label + '</option>';
				}
			}
			$('select[name=state]').html(contenue);
		}
	});

}

function getPictures(idHotel) {
	$.ajax({
		url: '/api/hotel/pictures?id=' + idHotel,
		type: 'get',
		data: {},
		success: function(response) {
			pictures = response;
			var contenue = "";
			var i = 0;
			for (picture of pictures) {
				if (i == 0) {
					contenue = contenue + '<div class="carousel-item active"><img class="d-block w-100" style="height:200px;width:100px" src="data:image/jpeg;base64,' + picture.base64 + '" alt="' + picture.id + '"></div>\n';

				} else {
					contenue = contenue + '<div class="carousel-item"><img class="d-block w-100" style="height:200px;width:100px" src="data:image/jpeg;base64,' + picture.base64 + '" alt="' + picture.id + '"></div>\n';
				}
				i++;
			}

			$('#galerie').html(contenue);
		}, error: function(xhr, ajaxOptions, thrownError) {
			var contenue = "";
			contenue = contenue + '<div class="carousel-item active"><img class="d-block w-100" src="/images/notfound.jpg" alt="Not found"></div>\n';
			$('#galerie').html(contenue);
		}
	});
}

function refreshotels() {
	$.ajax({
		url: '/api/hotel',
		type: 'get',
		data: {},
		success: function(response) {
			hotels = response;
			var contenue = "";
			for (hotel of hotels) {
				contenue = contenue + '<tr>\n';
				contenue = contenue + '<td><span class="custom-checkbox"> <input type="checkbox" id="checkbox' + hotel.id + '" name="options[]" value="1"> <label for="checkbox1"></label></span></td>\n';
				contenue = contenue + '<td>' + hotel.nomHotel + '</td>\n'
				contenue = contenue + '<td>' + hotel.nombreEtoile + '</td>\n'
				contenue = contenue + '<td>' + hotel.ville.label + '</td>\n'
				contenue = contenue + '<td><a href="#addImageModal" onclick="getPictures(' + hotel.id + ')" class="addpicture" data-toggle="modal"><i data-toggle="tooltip" title="Edit" class="fas fa-images"></i></a><a href="#editEmployeeModal" onclick="makeEditHotel(' + hotel.id + ')" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a> <a href="#deleteEmployeeModal" onclick="deleteHotel(' + hotel.id + ')" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a><a href="/admin/hotel/picture?id=' + hotel.id + '" class="edit" data-toggle="modal"><i class="fa fa-plus" aria-hidden="true"></i></a></td>\n'
				contenue = contenue + '</tr>\n'
			}


			$('#hotelslist').html(contenue);
		}
	});
}

function initialize() {


	refreshotels();
	refreshLieux();

	$('select[name=country]').change(function() {
		refreshLieux();
	});

	$('input[name=add]').click(function() {
		var nomHotel = $('input[name=hotelname]').val();
		var star = $('input[name=star]').val();
		var state = $('select[name=state]').val();
		datas = {
			'nomHotel': nomHotel,
			'nombreEtoile': star,
			'idLieu': state
		}
		datas = JSON.stringify(datas);
		$.ajax({
			type: "POST",
			headers: { Accept: "application/json" },
			contentType: "application/json",
			url: "/api/hotel",
			data: datas,
			success: function(response) {
				hotel = response;
				refreshotels();
				clearAddCache();
			}, error: function(xhr, ajaxOptions, thrownError) {
				var message = xhr['responseJSON'].message;
				message = JSON.parse(message);
				keys = Object.keys(message);
				for (let i = 0; i < keys.length; i++) {
					$('#' + keys[i] + 'Error').html(message[keys[i]]);
				}
			}
		});
	});
	starManagement();
	starManagementEdit();
}

jQuery(document).ready(function() {
	initialize();
	$('input[name=delete]').click(function() {
		$.ajax({
			type: "Delete",
			url: "/api/hotel?id=" + deletetedId,
			success: function(response) {
				refreshotels();
			}
		});
	});
	$('#cancelAdd').click(function() {
		clearAddCache();
	});
	$('input[name=deleteAll]').click(function() {
		deleteAll();
	});
	$.ajax({
		url: '/api/country',
		type: 'get',
		data: {},
		success: function(response) {
			countries = response;
		}
	});
	
	$('select[name=countryEdit]').change(function(){
		refreshLieuxEdit();
	});
	
	$('#editEdit').click(function() {
		var nomHotel = $('input[name=hotelnameEdit]').val();
		var star = $('input[name=starEdit]').val();
		var state = $('select[name=stateEdit]').val();
		var idHotel = $('input[name=idHotel]').val();
		datas = {
			'id': "" + idHotel,
			'nomHotel': nomHotel,
			'nombreEtoile': star,
			'idLieu': state
		}
		datas = JSON.stringify(datas);
		$.ajax({
			type: "PUT",
			headers: { Accept: "application/json" },
			contentType: "application/json",
			url: "/api/hotel",
			data: datas,
			success: function(response) {
				hotel = response;
				refreshotels();
				clearAddCache();
			}, error: function(xhr, ajaxOptions, thrownError) {
				var message = xhr['responseJSON'].message;
				message = JSON.parse(message);
				keys = Object.keys(message);
				for (let i = 0; i < keys.length; i++) {
					$('#' + keys[i] + 'ErrorEdit').html(message[keys[i]]);
				}
			}
		});
	});
});



