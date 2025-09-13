var reservations = []
var etat1 = "";
var reseCountry=[];
var reseDate=[];
var reseBudget=[];
$(document).ready(function() {
	console.log("hh	");

	refreshReservation();
	$('select[name=country]').change(function() {
		refreshReservationBYcountry();

	});
	$('input[name=dateArriveDate]').change(function() {
		refreshReservationByDATEARV();
	});
	$('input[name=budget]').change(function() {
		refreshReservationBYBudget();
	});
});
function refreshReservation() {
	$.ajax({
		url: '/api/reservation',
		type: 'get',
		data: {},
		success: function(response) {
			reservations = response;
			var contenue = "";
			for (rese of reservations) {
				contenue = contenue + '<tr>\n';

				contenue = contenue + '<td><span class="custom-checkbox"> <input type="checkbox" id="checkbox' + rese['id']['idVoyage'] + '" name="options[]" value="1"> <label for="checkbox1"></label></span></td>\n'
				contenue = contenue + '<td>' + rese['voyage']['titre'] + '</td>\n'
				contenue = contenue + '<td>' + rese['person']['cne'] + " " + rese['person']['prenom'] + " " + rese['person']['nom'] + '</td>\n'
				contenue = contenue + '<td>' + rese['voyage']['destination'] + '</td>\n'
				contenue = contenue + '<td>' + rese['voyage']['prix'] + '</td>\n'
				var etat1 = "";
				if (rese.confirmed) {
					etat1 = "Confirmee"
					contenue = contenue + '<td class="text-center success">' + etat1 + '</td>\n'
				}else{
					etat1 = "Pas Confirmee"
					contenue = contenue + '<td class="text-center danger">' + etat1 + '</td>\n'
				}

				contenue = contenue + '</tr>\n'
			}
			$('#reservationslist').html(contenue);
		}
	});
}
function refreshReservationBYcountry() {
	var country = $('select[name=country]').val();
	/*console.log(kcountry);sdfghjkl
	var country = getCountryByKey(kcountry);
	console.log(country); */
	var contenue = "";
	
	for (rese of reservations) {
		console.log(rese['voyage']['destination']);
		if (rese['voyage']['destination'] === country) {
			contenue = contenue + '<tr>\n';

			contenue = contenue + '<td><span class="custom-checkbox"> <input type="checkbox" id="checkbox' + rese['id']['idVoyage'] + '" name="options[]" value="1"> <label for="checkbox1"></label></span></td>\n'
			contenue = contenue + '<td>' + rese['voyage']['titre'] + '</td>\n'
			contenue = contenue + '<td>' + rese['person']['cne'] + " " + rese['person']['prenom'] + " " + rese['person']['nom'] + '</td>\n'
			contenue = contenue + '<td>' + rese['voyage']['destination'] + '</td>\n'
			contenue = contenue + '<td>' + rese['voyage']['prix'] + '</td>\n'

			if (rese.confirmed) {
				etat1 = "Confirmee"
				contenue = contenue + '<td class="text-center success">' + etat1 + '</td>\n'
			} else {
				etat1 = "Pas Confirmee"
				contenue = contenue + '<td class="text-center danger">' + etat1 + '</td>\n'
			}

			contenue = contenue + '</tr>\n'
			reseCountry.push(rese);
		}
	}
	$('#reservationslist').html(contenue);

}

function refreshReservationByDATEARV() {
	var date = $('input[name=dateArriveDate]').val();
	console.log(date);
	var contenue = "";
	for (rese of reseCountry) {
		console.log("res1  " + rese['voyage']['dateArriveeDate']);
		console.log(rese['voyage']['dateArriveeDate'] === date);
		if (rese['voyage']['dateArriveeDate'] === date) {
			contenue = contenue + '<tr>\n';

			contenue = contenue + '<td><span class="custom-checkbox"> <input type="checkbox" id="checkbox' + rese['id']['idVoyage'] + '" name="options[]" value="1"> <label for="checkbox1"></label></span></td>\n'
			contenue = contenue + '<td>' + rese['voyage']['titre'] + '</td>\n'
			contenue = contenue + '<td>' + rese['person']['cne'] + " " + rese['person']['prenom'] + " " + rese['person']['nom'] + '</td>\n'
			contenue = contenue + '<td>' + rese['voyage']['destination'] + '</td>\n'
			contenue = contenue + '<td>' + rese['voyage']['prix'] + '</td>\n'

			if (rese.confirmed) {
				etat1 = "Confirmee"
				contenue = contenue + '<td class="text-center success">' + etat1 + '</td>\n'
			} else {
				etat1 = "Pas Confirmee"
				contenue = contenue + '<td class="text-center danger">' + etat1 + '</td>\n'
			}

			contenue = contenue + '</tr>\n'
			reseDate.push(rese)
		}

	}
	$('#reservationslist').html(contenue);

}

function refreshReservationBYBudget() {
	var prix = $('input[name=budget]').val();
	console.log(prix);
	var contenue = "";
	for (rese of reseDate) {
		if (rese['voyage']['prix'] <= prix) {
			contenue = contenue + '<tr>\n';

			contenue = contenue + '<td><span class="custom-checkbox"> <input type="checkbox" id="checkbox' + rese['id']['idVoyage'] + '" name="options[]" value="1"> <label for="checkbox1"></label></span></td>\n'
			contenue = contenue + '<td>' + rese['voyage']['titre'] + '</td>\n'
			contenue = contenue + '<td>' + rese['person']['cne'] + " " + rese['person']['prenom'] + " " + rese['person']['nom'] + '</td>\n'
			contenue = contenue + '<td>' + rese['voyage']['destination'] + '</td>\n'
			contenue = contenue + '<td>' + rese['voyage']['prix'] + '</td>\n'

			if (rese.confirmed) {
				etat1 = "Confirmee"
				contenue = contenue + '<td class="text-center success">' + etat1 + '</td>\n'
			} else {
				etat1 = "Pas Confirmee"
				contenue = contenue + '<td class="text-center danger">' + etat1 + '</td>\n'
			}

			contenue = contenue + '</tr>\n'
			reseBudget.push(rese)
		}
		
	}
	$('#reservationslist').html(contenue);

}



/*function getCountryByKey(keyCountry){
	var daata=JSON.stringify(keyCountry);
	var country ="";
	$.ajax({
		type:"POST",
		headers : { Accept : "application/json"},
		contentType:"application/json",
		url:"/api/lieux",
		data:daata,
		success:function(response){
			country=response;
		}
	});
	return country;

}*/

