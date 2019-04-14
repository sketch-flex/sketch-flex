window.onload = function(){
	drawButtons();
}

var displayName = document.getElementById("name");
var displayImage = document.getElementById("image");



var main = document.getElementById("single-sketch-main");
const xhr = new XMLHttpRequest();
xhr.onreadystatechange = function() {
	if(xhr.readyState === 4 && xhr.status === 200){
		const res = xhr.response;
		main.innerHTML = res;
		drawButtons();
		var titleText = document.getElementsByTagName("h1")[0].innerText;
		var titleHead = document.getElementsByTagName("title")[0];
		titleHead.innerText = titleText;
	}
}

function drawButtons(){
	var sketchId = document.getElementById("getId").href;
	sketchId = sketchId.split("=")[1];
	var displayNote = document.getElementById("note");
	
	var previousButton = document.createElement("button");
	previousButton.innerHTML = "&#10094";
	
	var nextButton = document.createElement("button");
	nextButton.innerHTML = "&#10095";
	
	var nextMenu = document.getElementsByClassName("next-button")[0];
	displayImage = document.getElementById("image");
	nextMenu.insertBefore(previousButton, displayImage);
	nextMenu.appendChild(nextButton);
	
	nextButton.addEventListener("click", function() {
		xhr.open("POST", "/next/" + sketchId, true);
		xhr.send();
	});

		previousButton.addEventListener("click", function() {

		xhr.open("POST", "/previous/" + sketchId, true);
		xhr.send();
	});
	document.getElementById("note").addEventListener("keydown",	function(e) {
		if (!e) {
			var e = window.event;
		}
			
		// Enter is pressed
		if (e.keyCode == 13) {
			updateNote();
		}
	}, false);
}




function updateNote() {
	var note = document.getElementById("note").value;
	var sketchId = document.getElementById("getId").href;
	sketchId = sketchId.split("=")[1];
	
	submitNote(sketchId, note);
}

function submitNote(id, note) {
	xhr.open("POST", id + "/note/" + note, true);
	xhr.send();
}

