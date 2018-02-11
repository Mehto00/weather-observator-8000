const form = document.querySelector("#postObservations");
const station = document.querySelector("#station");
const temperature = document.querySelector("#temperature");

// A object for collecting all the stations API urls; 
var apiUrls = { 
	amsterdam : "http://207.154.212.16:8080/weather-app/latest/amsterdam",
 	dubai : "http://207.154.212.16:8080/weather-app/latest/dubai",
 	helsinki : "http://207.154.212.16:8080/weather-app/latest/helsinki",
 	new_york : "http://207.154.212.16:8080/weather-app/latest/new_york",
 	tokyo : "http://207.154.212.16:8080/weather-app/latest/tokyo"
}

const postObservation = "http://207.154.212.16:8080/weather-app/perceptions";
const allObservations = "http://207.154.212.16:8080/weather-app/all/perceptions"


/* Function for fetching the API-data and appending it to DOM via correct span tags*/
var getApiData = function () {
	for(key in apiUrls){
	let temp = key; // Name of the current property e.g amsterdam
    fetch(apiUrls[key]) // Value of the current property
	.then((res) => { return res.json() })
            .then((data) => {
            	// Appends fetched data to correct span by property 
            	document.querySelector(`#${temp}-latest`).innerHTML = data[0].temperature;
            	document.querySelector(`#${temp}-date`).innerHTML = data[0].timestamp;
            	// Checks if api data exist (hence the apostrophes) and after that appends it to correct span
            	if (data[1].max !== "null") {
            	document.querySelector(`#${temp}-max-min`).innerHTML = "Max: " + data[1].max + " Min: " + data[1].min;
            	}          	
			});
	}
	console.log('Api data updated')
};


/* Event Listener for catching clicks on "All observations" -link and printing
them out to a pop up window with the help of toggling css class showPopups */
document.querySelector("#listObservations").addEventListener("click", function (e) {
	document.querySelector(".popups").classList.toggle("showPopups");

	fetch(allObservations)
	.then((res) => { return res.json() })
            .then((data) => {
            	let printString = "<p class=\"close\">&#10006;</p><h3>All the Observations</h3><p class=\"blackFont\">";
            	for (var i = 0; i < data.length; i++) {
            	printString = printString + "Station: " + data[i].station + 
				" | Temperature: " + data[i].temperature + 
				" | Time: " + data[i].timestamp + "<br>";				
            	}
            	printString = printString + "</p>"
            	document.querySelector("#ObservationsBox").innerHTML = printString;
            });
})

/* For hiding the previous popup -window with a click */
document.querySelector(".popups").addEventListener("click", function (e) {
	document.querySelector(".popups").classList.toggle("showPopups");
	document.querySelector("#ObservationsBox").innerHTML = "";
})

/* Event Listener for submitting data to API with http POST */
form.addEventListener("submit", function (e) {
	e.preventDefault();

	let fd = new FormData();
	fd.set("station", station.value);
	fd.set("temperature", temperature.value);

	fetch(postObservation, {
	method: 'POST', // or 'PUT'
	mode: 'no-cors',
	body: fd, 
	headers: new Headers({
		'Content-Type': 'application/json'
	})
	}).then(res => console.log('Data succefully sent'))
	.catch(error => console.error('Error:', error))

	document.location.reload(true);
})

getApiData();












