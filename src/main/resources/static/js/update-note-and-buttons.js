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
}




function updateNote() {
	var name = document.getElementById("name").textContent;
//	console.log(name);
	var note = document.getElementById("note").value;
//	console.log(note);
	console.log(sketchList);
	for (var i = 0; i < sketchList.length; i += 1) {
		if (name === sketchList[i].name) {
			var sketchId = sketchList[i].id;
			sketchList[i].note = note;
		}
	}
	
	submitNote(sketchId, note);
}

function submitNote(id, note) {
	xhr.open("POST", id + "/note/" + note, true);
	xhr.send();
}

document.getElementById("note").addEventListener("keydown",
		function(e) {
			if (!e) {
				var e = window.event;
			}

			// Enter is pressed
			if (e.keyCode == 13) {
				updateNote();
			}
		}, false);
