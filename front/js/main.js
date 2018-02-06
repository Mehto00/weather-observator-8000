
const amsterdamLatest = document.querySelector("#amsterdam-latest");
const dubaiLatest = document.querySelector("#dubai-latest");
const helsinkiLatest = document.querySelector("#helsinki-latest");
const new_yorkLatest = document.querySelector("#new_york-latest");
const tokyoLatest = document.querySelector("#tokyo-latest");

const form = document.querySelector("#postPerceptions");

const url = "http://207.154.212.16:8080/weather-app/all/perceptions";

fetch(url)
	.then((res) => { return res.json() })
            .then((data) => {
            	console.log(data[0].temperature);
            	for(var i = 0, length1 = data.length; i < length1; i++) {
	            	if (data[i].station == "dubai") {
	            		console.log(data[i].temperature);
						amsterdamLatest.innerHTML = data[i].temperature
					}
				}
			});

form.addEventListener("submit", function (e) {
	e.preventDefault();
	console.log(e.content);
	console.log('testi')
	// fetch(url, {
	//   method: 'POST', // or 'PUT'
	//   body: JSON.stringify(), 
	//   headers: new Headers({
	//     'Content-Type': 'application/json'
	//   })
	// }).then(res => res.json())
	// .catch(error => console.error('Error:', error))
	// .then(response => console.log('Success:', response));
})

// $.getJSON("http://207.154.212.16:8080/weather-app/all/perceptions", function( data ) {	
// 	for(var i = 0, length1 = data.length; i < length1; i++) {
// 		if (data[i].station == "amsterdam") {
// 			amsterdamLatest.innerHTML = data[4].temperature
// 		} else if (data[i].station == "dubai") {
// 			dubaiLatest.innerHTML = data[3].temperature
// 		} else if (data[i].station == "helsinki") {
// 			helsinkiLatest.innerHTML = data[2].temperature
// 		} else if (data[i].station == "new_york") {
// 			new_yorkLatest.innerHTML = data[5].temperature
// 		} else if (data[i].station == "tokyo") {
// 			tokyoLatest.innerHTML = data[7].temperature
// 		}
// 	}			
// });









