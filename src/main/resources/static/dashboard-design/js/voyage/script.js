// Ziad Bougrine
deletetedId = null;
voyages = [];
lieux = [];
countries = [];
equipes = [];
function deleteHotel(idHotel) {
	deletetedId = idHotel;
}

function refreshEquipeByVoyage(idEquipe) {
	var contenue = "";
	for (equipe of equipes) {
		if (idEquipe == equipe.id) {
			contenue = contenue + '<option value="' + equipe.id + '" selected >' + equipe.label + '</option>';
		} else {
			contenue = contenue + '<option value="' + equipe.id + '">' + equipe.label + '</option>';
		}
	}
	$('select[name=idequipeEdit]').html(contenue);
}

function refreshCountryByVoyage(valueCountry) {
	var contenue = "";
	for (country of countries) {
		if (valueCountry == country.valueCountry) {
			contenue = contenue + '<option value="' + country.valueCountry + '" selected >' + country.valueCountry + '</option>';
		} else {
			contenue = contenue + '<option value="' + country.valueCountry + '">' + country.valueCountry + '</option>';
		}
	}
	$('select[name=destinationEdit]').html(contenue);
}

function makeEditVoyage(idVoyage) {
	var selectedVoyage = null;
	for (voyage of voyages) {
		if (voyage.id == idVoyage) {
			selectedVoyage = voyage;
		}
	}
	var voyage = selectedVoyage;	
	$("input[name=titreEdit]").val(voyage.titre);
	$("input[name=prixEdit]").val(voyage.prix);
	$("input[name=nbrPersonnesEdit]").val(voyage.nbrPersonnes);
	$("input[name=nbKilometresEdit]").val(voyage.nbKilometres);
	$("input[name=ageMinEdit]").val(voyage.ageMin);
	$("input[name=ageMaxEdit]").val(voyage.ageMax);
	$("input[name=dateDepartDateEdit]").val(voyage.dateDepartDate);
	$("input[name=dateArriveeDateEdit]").val(voyage.dateArriveeDate);
	$("input[name=descriptionEdit]").val(voyage.description);
	$("select[name=typeVoyageEdit]").val(voyage.typeVoyage);
	$("input[name=reductionEdit]").val(voyage.reduction);
	refreshCountryByVoyage(voyage.destination);
	refreshEquipeByVoyage(voyage.equipe.id)
	$('input[name=idVoyageEdit]').val(idVoyage);
	
}



function deleteAll() {
	for (voyage of voyages) {
		if ($('#checkbox' + voyage.id).is(":checked")) {
			$.ajax({
				type: "Delete",
				url: "/api/voyage?id=" + voyage.id,
				success: function(response) {
					refreshotels();
				}
			});
		}
	}
	refreshvoyages();
}

function clearAddCache() {
	$('input[name=titre]').val("");
	$('input[name=prix]').val("");
	$('input[name=nbrPersonnes]').val("");
	$('input[name=nbKilometres]').val("");
	$('input[name=ageMin]').val("");
	$('input[name=ageMax]').val("");
	$('input[name=dateDepartDate]').val("");
	$('input[name=dateArriveeDate]').val("");
	$('input[name=description]').val("");
	$('input[name=typeVoyage]').val("");
	refreshCountry();
}

function refreshEquipe(){
	var contenue = "";
	for (equipe of equipes) {
		contenue = contenue + '<option value="' + equipe.id + '">' + equipe.label + '</option>';
	}
	$('select[name=idequipe]').html(contenue);
}

function refreshCountry() {
	var contenue = "";
	for (country of countries) {
		contenue = contenue + '<option value="' + country.valueCountry + '">' + country.valueCountry + '</option>';
	}
	$('select[name=destination]').html(contenue);
}

function getPictures(idVoyage) {
	$.ajax({
		url: '/api/voyage/pictures?id=' + idVoyage,
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

function refreshvoyages() {
	$.ajax({
		url: '/api/voyage',
		type: 'get',
		data: {},
		success: function(response) {
			console.log("refresh")
			voyages = response;
			var contenue = "";
			for (voyage of voyages) {
				contenue = contenue + '<tr>\n';
				contenue = contenue + '<td><span class="custom-checkbox"> <input type="checkbox" id="checkbox' + voyage.id + '" name="options[]" value="1"> <label for="checkbox1"></label></span></td>\n';
				contenue = contenue + '<td>' + voyage.titre + '</td>\n'
				contenue = contenue + '<td>' + voyage.destination + '</td>\n'
				contenue = contenue + '<td>' + voyage.dateDepartDate + '</td>\n'
				contenue = contenue + '<td>' + voyage.dateArriveeDate + '</td>\n'
				contenue = contenue + '<td>' + voyage.description + '</td>\n'
				contenue = contenue + '<td>' + voyage.nbrPersonnes + '</td>\n'
				contenue = contenue + '<td>' + voyage.nombrePersonneTotal+ '</td>\n'
				contenue = contenue + '<td>' + voyage.prix+ '</td>\n'
				contenue = contenue + '<td>' + voyage.reduction+ '</td>\n'
				contenue = contenue + '<td><a href="#addImageModal" onclick="getPictures(' + voyage.id + ')" class="addpicture" data-toggle="modal"><i data-toggle="tooltip" title="Edit" class="fas fa-images"></i></a><a href="#editEmployeeModal" onclick="makeEditVoyage(' + voyage.id + ')" class="edit" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Edit">&#xE254;</i></a> <a href="#deleteEmployeeModal" onclick="deleteHotel(' + voyage.id + ')" class="delete" data-toggle="modal"><i class="material-icons" data-toggle="tooltip" title="Delete">&#xE872;</i></a><a href="/admin/voyage/picture?id=' + voyage.id + '" class="edit" data-toggle="modal"><i class="fa fa-plus" aria-hidden="true"></i></a>			<a href="/admin/voyage/themes?id=' + voyage.id + '" class="theme" data-toggle="modal"><i class="fas fa-archive"></i></a><a href="/admin/voyage/hoteles?id=' + voyage.id + '" class="hotele" data-toggle="modal"><i class="fas fa-hotel"></i></a><a href="/admin/voyage/lieux?id=' + voyage.id + '" class="lieux" data-toggle="modal"><i class="fas fa-city"></i></a><a href="/admin/activite?id=' + voyage.id + '" data-toggle="modal"><i class="fas fa-snowboarding"></i></a></td>\n'
				contenue = contenue + '</tr>\n'
			}
		
			$('#voyageslist').html(contenue);
		}
	});
}

function initialize() {
	refreshvoyages();
	$('input[name=add]').click(function() {
		var titre = $('input[name=titre]').val();
		var destination = $('select[name=destination]').val();
		var prix = $('input[name=prix]').val();
		var nbrPersonnes = $('input[name=nbrPersonnes]').val();
		var nbKilometres = $('input[name=nbKilometres]').val();
		var ageMin = $('input[name=ageMin]').val();
		var ageMax = $('input[name=ageMax]').val();
		var dateDepartDate = $('input[name=dateDepartDate]').val();
		var dateArriveeDate  = $('input[name=dateArriveeDate]').val();
		var description = $('input[name=description]').val();
		var equipe = $('select[name=idequipe]').val();
		var typeVoyage = $("select[name=typeVoyage]").val();
		var reduction = $("input[name=reduction]").val();
		datas = {
			'titre': titre,
			'destination': destination,
			'prix': prix,
			'nbrPersonnes': nbrPersonnes,
			'nbKilometres': nbKilometres,
			'ageMin': ageMin,
			'ageMax': ageMax,
			'dateDepartDate': dateDepartDate,
			'dateArriveeDate': dateArriveeDate,
			'description':description,
			'idEquipe':equipe,
			'reduction':reduction,
			'typeVoyage':typeVoyage
		}
		datas = JSON.stringify(datas);
		console.log(datas)
		$.ajax({
			type: "POST",
			headers: { Accept: "application/json" },
			contentType: "application/json",
			url: "/api/voyage",
			data: datas,
			success: function(response) {
				voyages = response;
				refreshvoyages();
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
}

jQuery(document).ready(function() {
	initialize();
	$('input[name=delete]').click(function() {
		$.ajax({
			type: "Delete",
			url: "/api/voyage?id=" + deletetedId,
			success: function(response) {
				refreshvoyages();
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
			refreshCountry();
		}
	});
	
	$.ajax({
		url: '/api/equipes',
		type: 'get',
		data: {},
		success: function(response) {
			equipes = response;
			refreshEquipe();
		}
	});
	
	
	$('input[name=edit]').click(function() {
		var idVoyage = $('input[name=idVoyageEdit]').val();
		var titre = $('input[name=titreEdit]').val();
		var destination = $('select[name=destinationEdit]').val();
		var prix = $('input[name=prixEdit]').val();
		var nbrPersonnes = $('input[name=nbrPersonnesEdit]').val();
		var nbKilometres = $('input[name=nbKilometresEdit]').val();
		var ageMin = $('input[name=ageMinEdit]').val();
		var ageMax = $('input[name=ageMaxEdit]').val();
		var dateDepartDate = $('input[name=dateDepartDateEdit]').val();
		var dateArriveeDate  = $('input[name=dateArriveeDateEdit]').val();
		var description = $('input[name=descriptionEdit]').val();
		var equipe = $('select[name=idequipeEdit]').val();
		var typeVoyage = $("select[name=typeVoyageEdit]").val();
		var reduction = $("input[name=reductionEdit]").val();
		datas = {
			'id':idVoyage,
			'titre': titre,
			'destination': destination,
			'prix': prix,
			'nbrPersonnes': nbrPersonnes,
			'nbKilometres': nbKilometres,
			'ageMin': ageMin,
			'ageMax': ageMax,
			'dateDepartDate': dateDepartDate,
			'dateArriveeDate': dateArriveeDate,
			'description':description,
			'idEquipe':equipe,
			'typeVoyage':typeVoyage,
			'reduction':reduction
		}
		datas = JSON.stringify(datas);
		$.ajax({
			type: "PUT",
			headers: { Accept: "application/json" },
			contentType: "application/json",
			url: "/api/voyage",
			data: datas,
			success: function(response) {
				refreshvoyages();
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



