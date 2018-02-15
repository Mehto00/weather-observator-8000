
// A object for collecting all the stations API urls; 
var apiUrls = { 
	amsterdam : "http://207.154.212.16:8080/weather-app/latest/amsterdam",
	dubai : "http://207.154.212.16:8080/weather-app/latest/dubai",
	helsinki : "http://207.154.212.16:8080/weather-app/latest/helsinki",
	new_york : "http://207.154.212.16:8080/weather-app/latest/new_york",
	tokyo : "http://207.154.212.16:8080/weather-app/latest/tokyo"
};

const postObservation = "http://207.154.212.16:8080/weather-app/post-observation";
const allObservations = "http://207.154.212.16:8080/weather-app/all/observations";

// querySelector for submit form
const form = document.querySelector("#postObservation");

// querySelectorAll for anchor -links openObservationsList -class
const openObservationsList = document.querySelectorAll(".openObservationsList");

/* Function for fetching the API-data and appending it to DOM via correct span tags when script loads */
function getApiData() {
	for(key in apiUrls){
	let temp = key; // Name of the current property e.g amsterdam
   	fetch(apiUrls[key]) // Value of the current property
   	.then((res) => { return res.json()})
   	.then((data) => {
				// Appends fetched data to correct span by property 
	            document.querySelector(`#${temp}-latest`).innerHTML = data[0].temperature;
	            document.querySelector(`#${temp}-date`).innerHTML = "Submitted " + data[0].timestamp;
	            // Checks if api data exist (hence the apostrophes) and after that appends it to correct span
	            if (data[1].max !== "null") {
	            	document.querySelector(`#${temp}-max-min`).innerHTML = "Max: " + data[1].max + " / Min: " + data[1].min;
	            }           
	        });
   }
   console.log('Api data updated')
}; // END OF function getApiData()


/* ========== Event Listeners  ========== */

// Get data from API
document.addEventListener("load", getApiData())

/* Event Listener for submitting data to API with http POST */
form.addEventListener("submit", function (e) {
	e.preventDefault();

	let fd = new FormData(form);
	const wait = ms => new Promise(resolve => setTimeout(resolve, ms));

	//Check if user submitted fahrenheit and convert to celsius
	if (fd.get("tempScale") == "fahrenheit") {
		let fheit = fd.get("temperature");
		fd.set("temperature", ((fheit - 32) * 5/9).toFixed(1));
	}

	// Check if all the form fields has value before submitting
	if (!fd.get("station") || !fd.get("tempScale") || !fd.get("temperature")) {
		// Dislpay error message and add red box as styling
		document.querySelector("#submitInfo").classList.add("submitError");
		document.querySelector("#submitText").innerHTML = "All fields are mandatory";
	} else {
	fetch(postObservation, {
	  method: 'post',
	  body: fd, // post body 
	  headers: {
	    'Accept': 'multipart/form-data'
	  },
	  credentials: 'same-origin', // send cookies
	  credentials: 'include',     // send cookies, even in CORS
	})	
	.then(res => {
		if (!res.ok) { throw res }			
		console.log(res.status, "Hooray! Your observation was successfully submitted");	
		document.querySelector("#submitInfo").classList.remove("submitError"); // Removes red box if user has continued after unsuccessful form submit
		document.querySelector("#submitInfo").classList.toggle("submitSuccess"); // Adds greenbox 
		document.querySelector("#submitText").innerHTML = "Observation successfully submitted"; // and feedback about succesful submit

	})
	.then(() => getApiData())
	.then(() => wait(2000))
	.then(() => clearClassesAndClose())
	.catch(res => {
			if (res.status >= 400 && res.status < 500) {
				console.error(res.status, "Upsy-Daisy! Something went wrong when filling the form");
				document.querySelector("#submitText").innerHTML = "All fields are mandatory";
			} else if (res.status >= 500 && res.status < 600) {
				console.error(res.status, "Upsy-Daisy! Something's wrong on the Server side");
				document.querySelector("#submitText").innerHTML = "Something's wrong on the Server side";
			}
			document.querySelector("#submitInfo").classList.toggle("submitError");
		}
	);
	} // END OF else {fetch(postObservation, ..}

	// For clearing the form values after the submit
	document.querySelectorAll("input").forEach(function (item) {
		if (item.checked) {
			item.checked = false;
		} else if (item.type == "number") {
			item.value = "";	
		}
	});

	function clearClassesAndClose() {
		document.querySelector("#submitInfo").classList.remove("submitSuccess"); // For resetting styling
		document.querySelector("#submitInfo").classList.remove("submitError"); // For resetting styling
		document.querySelector("#submitText").innerHTML = "" // For resetting #submitText
		document.querySelector(".formBox").classList.remove("formBoxShown"); // And lastly hides the formBox
	}
}) // END OF form.addEventListener("submit"..)

/* Event Listener for catching clicks on "All observations" -links and printing
out the data from api to a pop up window with the help of toggling css class showPopups */
Array.from(openObservationsList).forEach(function(element) {
	element.addEventListener("click", function (e) {
		document.querySelector(".popups").classList.toggle("showPopups");
		
		fetch(allObservations)
		.then((res) => { return res.json() })
		.then((data) => {			
			let printString = "<div><p class=\"closePopupCross\">&#10006;</p></div>" + 
			"<h3 class=\"content-box-header\">All Observations</h3>" + 
			"<div class=\"observationList\">"
			data.reverse(); // Display the latest submit first
			for (var i = 0; i < data.length; i++) {
				printString = printString + 
				"<div class=\"content-box\">" + 
				"<p>Station: " + toTitleCase(data[i].station) + "</p>" +
				"<p>Temperature: " + data[i].temperature + "</p>" +
				"<p>Time: " + data[i].timestamp + "</p></div>";     
		    } 
		    printString = printString + "</div>";
		    document.querySelector("#observationsBox").innerHTML = printString;
		});
	});

	// toTitleCase for formatting listed station names
	var toTitleCase = function (str) {
		str = str.toLowerCase().split(' ');
		for (var i = 0; i < str.length; i++) {
			str[i] = str[i].charAt(0).toUpperCase() + str[i].slice(1);
		}
		return str.join(' ');
	};

}); // END OF Array.from(openObservationsList).forEach..)

/* Event Listener for catching clicks on "Contact" -link and popping up the Contact info */
document.querySelector("#openContact").addEventListener("click", function (e) {
	document.querySelector(".popups").classList.toggle("showPopups");
	let printString = "<div>" + 
	"<p class=\"closePopupCross\">&#10006;</p></div>" +
	"<img src=\"img/meika.png\" />" +
	"<h3 class=\"content-box-header\">Here's the deal</h3>" + 
	"<p>I am a nearly graduated Haaga-Helia student looking for an internship.<br>" + 
	"I'm also a graphic designer and a lifelong learner on a path to become a full stack developer.<br>" + 
	"I’m living in Helsinki, learning every day to be better at things like coding and cooking.<br>" + 
	"And generally in life, of course.</p>" +
	"<ul><li><a href=\"http://www.linkedin.com/in/mikkometso/\" target=\"_blank\">You can find my Linkedin profile from here</a>.</li><br>" +
	"<li><a href=\"https://github.com/Mehto00/weather-observator-8000\" target=\"_blank\">And the Github repository about this project here</a>.</li></ul>"
	document.querySelector("#contactBox").innerHTML = printString;
}); // END OF document.querySelector("#openContact")

/* For hiding the previously mentioned "All observations" and  popup -windows with a click targeting the .closePopupCross -mark */
document.querySelector(".popups").addEventListener("click", function (e) {
	// Check if user clickes the popup close mark 
	if (e.target == document.querySelector(".closePopupCross")) { 
		document.querySelector(".popups").classList.toggle("showPopups");
		document.querySelector("#observationsBox").innerHTML = "";
		document.querySelector("#contactBox").innerHTML = "";
	}
})

/* Event Listener for catching clicks on Submit observation section button and popping up the submit form */
document.querySelector("#openForm").addEventListener("click", function (e) {
	document.querySelector(".formBox").classList.toggle("formBoxShown");
});

/* Event Listener for hiding the submit form */
document.querySelector(".closePopupCross").addEventListener("click", function (e) {
	document.querySelector("#submitText").innerHTML = ""
	document.querySelector("#submitInfo").classList.remove("submitError");
	document.querySelector(".formBox").classList.remove("formBoxShown");
});

