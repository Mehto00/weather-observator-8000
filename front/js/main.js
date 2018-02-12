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
};

const postObservation = "http://207.154.212.16:8080/weather-app/perceptions";
const allObservations = "http://207.154.212.16:8080/weather-app/all/perceptions";

/* Function for fetching the API-data and appending it to DOM via correct span tags*/
var getApiData = function () {
	for(key in apiUrls){
	let temp = key; // Name of the current property e.g amsterdam
	   fetch(apiUrls[key]) // Value of the current property
	.then((res) => { return res.json()})
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

const openObservationsList = document.querySelectorAll(".openObservationsList");

/* Event Listener for catching clicks on "All observations" -links and printing
out the data from api to a pop up window with the help of toggling css class showPopups */
Array.from(openObservationsList).forEach(function(element) {
	element.addEventListener("click", function (e) {
		document.querySelector(".popups").classList.toggle("showPopups");
		
		fetch(allObservations)
		.then((res) => { return res.json() })
		           .then((data) => {
		            let printString = 
		            "<div><p class=\"closePopup\">&#10006;</p></div>" + 
		            "<h3 class=\"content-box-header\">All Observations</h3>" + 
		            "<div class=\"observationList\">"
		            for (var i = 0; i < data.length; i++) {
		             printString = printString + 
		             "<div class=\"content-box\">" + 
		             "<p>Station: " + data[i].station + "</p>" +
		    "<p>Temperature: " + data[i].temperature + "</p>" +
		    "<p>Time: " + data[i].timestamp + "</p></div>" +     
		    // A dublicate block to complish odds/even css -styling
		    "<div class=\"content-box light-grey-bg\">" + 
		             "<p>Station: " + data[i].station + "</p>" +
		    "<p>Temperature: " + data[i].temperature + "</p>" +
		    "<p>Time: " + data[i].timestamp + "</p></div>";    
		            } 
		            printString = printString + "</div>";
		            document.querySelector("#observationsBox").innerHTML = printString;
		           });
	});
});

/* Event Listener for catching clicks on "Contact" -link and popping up the Contact info */
document.querySelector("#openContact").addEventListener("click", function (e) {
	document.querySelector(".popups").classList.toggle("showPopups");
	let printString =  	"<div><p class=\"closePopup\">&#10006;</p></div>" +
						"<h3 class=\"content-box-header\">Here's my contact info</h3>" + 
						"<img src=\"../img/meika.png\" />" +
						"<p>Graphic designer, almost Haaga-Helia graduate and a lifelong learner on a path to become a Full Stack developer.<br>I’m living in Helsinki, learning every day to be better at things like coding and cooking.<br>And generally in life of course.</p>" +
						"<ul><li><a href=\"http://www.linkedin.com/in/mikkometso/\" target=\"_blank\">Linkedin</a></li><br>" +
						"<li><a href=\"http://www.github.com/Mehto00\" target=\"_blank\">Github</a></li></ul>"
	document.querySelector("#contactBox").innerHTML = printString;
});

/* For hiding the previously mentioned popup -windows with a click targeting the .closePopup -mark */
document.querySelector(".popups").addEventListener("click", function (e) {
	// Check if user clickes the popup close mark 
	if (e.target == document.querySelector(".closePopup")) { 
	 document.querySelector(".popups").classList.toggle("showPopups");
	 document.querySelector("#observationsBox").innerHTML = "";
	 document.querySelector("#contactBox").innerHTML = "";
	}
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












