var lieux = [];
var voyages = [];
var voyfiltre = [];
var voyDate = [];
$(document).ready(function() {
	refreshVoyages();
	$('input[name=date_arrive]').click(function() {
		verifi();
	});
	$('select[name=country]').change(function() {
		refreshLieux();
		refreshVoyagesByCountry();
	});
	$('.date').change(function() {
		refreshVoyagesByDATE();
	});
	$('input[name=budget]').change(function() {
		refreshVoyagesByBudget();
	})

});
function verifi() {
	var dd = $('input[name=date_depart]').val();
	var da = $('input[name=date_arrive]').val();
	document.getElementById('dr').setAttribute("min", dd);
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
				if (lieu.country.valueCountry === country) {
					contenue = contenue + '<option value="' + lieu.id + '">' + lieu.label + '</option>';
				}
			}
			$('select[name=state]').html(contenue);
		}
	});

}

function refreshVoyages() {
	$.ajax({
		url: '/api/voyage',
		type: 'get',
		data: {},
		success: function(response) {
			voyages = response;
			//console.log("voyages.lentgh =" + voyages.length);
			//document.getElementById("srch").setAttribute("value", "search  " + voyages.length);
		}
	});
	document.getElementById("srch").setAttribute("value", "search  " /*+ voyages.length*/);

}

function refreshVoyagesByCountry() {
	var country = $('select[name=country]').val();
	var i = 0;
	refreshVoyages();
	for (voy of voyages) {
		if (voy['destination'] == country) {//voy['destination']
			voyfiltre.push(voy);
			i = i + 1;
		}

	}
	document.getElementById("srch").setAttribute("value", "search  " + i);

}



function refreshVoyagesByDATE() {
	var dated = $('input[name=date_depart]').val();
	var dater = $('input[name=date_arrive]').val();
	refreshVoyages();
	var i = 0;
	/*alert("jhhh");*/
	for (voy of voyfiltre) {
		console.log("dATABASE " + voy['dateDepartDate']);
		if (voy['dateDepartDate'] >= dated && voy['dateArriveeDate'] <= dater) {
			i = i + 1;
			voyDate.push(voy);
		}


	}
	document.getElementById("srch").setAttribute("value", "search  " + i);
}



function refreshVoyagesByBudget() {
	var prix = $('input[name=budget]').val();
	refreshVoyages();
	var i=0;
	for (voy of voyDate) {
		console.log(voyDate);
		if (voy['prix'] <= prix){
			i = i + 1;

		}
	}
	document.getElementById("srch").setAttribute("value", "search  " + i);


}
