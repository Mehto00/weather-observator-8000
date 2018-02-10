const form = document.querySelector("#postPerceptions");
const station = document.querySelector("#station");
const temperature = document.querySelector("#temperature");

var apiUrls = { 
	amsterdam : "http://207.154.212.16:8080/weather-app/latest/amsterdam",
 	dubai : "http://207.154.212.16:8080/weather-app/latest/dubai",
 	helsinki : "http://207.154.212.16:8080/weather-app/latest/helsinki",
 	new_york : "http://207.154.212.16:8080/weather-app/latest/new_york",
 	tokyo : "http://207.154.212.16:8080/weather-app/latest/tokyo"
}

const postPerception = "http://207.154.212.16:8080/weather-app/perceptions";

var getApiData = function () {
	for(key in apiUrls){
	let temp = key; // Name of the current property e.g amsterdam
    fetch(apiUrls[key]) // Value of the current property
	.then((res) => { return res.json() })
            .then((data) => {
            	document.querySelector(`#${temp}-latest`).innerHTML = data[0].temperature;
            	document.querySelector(`#${temp}-date`).innerHTML = data[0].timestamp;
			});
	}
	console.log('Api data updated')
};

getApiData();

form.addEventListener("submit", function (e) {
	e.preventDefault();

	let fd = new FormData();
	fd.set("station", station.value);
	fd.set("temperature", temperature.value);

	fetch(postPerception, {
	method: 'POST', // or 'PUT'
	mode: 'no-cors',
	body: fd, 
	headers: new Headers({
		'Content-Type': 'application/json'
	})
	}).then(res => console.log('Data succefully sent'))
	.catch(error => console.error('Error:', error))
})

// TODO: call getApiData() again after submit to update the new data to DOM












