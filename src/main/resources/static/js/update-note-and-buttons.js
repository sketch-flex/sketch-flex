
var previousButton = document.createElement("button");
previousButton.innerHTML = "&#10094";

var nextButton = document.createElement("button");
nextButton.innerHTML = "&#10095";

var displayName = document.getElementById("name");
var displayImage = document.getElementById("image");
var displayNote = document.getElementById("note");

//gets json data created by GSON in the sketch controller.
var json = document.getElementById("json").textContent;
var sketchList = JSON.parse(json);

var nextMenu = document.getElementsByClassName("next-button")[0];
nextMenu.insertBefore(previousButton, displayImage);
nextMenu.appendChild(nextButton);


nextButton.addEventListener("click", function() {

	for (var i = 0; i < sketchList.length; i += 1) {
		if (displayName.textContent === sketchList[i].name) {
			var currentIndex = i;
		}
	}
	if (currentIndex < sketchList.length-1) {
		currentIndex += 1;
		displayName.textContent = sketchList[currentIndex].name;
		displayImage.src= sketchList[currentIndex].imageLocation;
		displayNote.value = sketchList[currentIndex].note;
	}
	console.log(sketchList);
});

previousButton.addEventListener("click", function() {

	for (var i = 0; i < sketchList.length; i += 1) {
		if (displayName.textContent === sketchList[i].name) {
			var currentIndex = i;
		}
	}
	if (currentIndex >= 1) {
		currentIndex -= 1;
		displayName.textContent = sketchList[currentIndex].name;
		displayImage.src= sketchList[currentIndex].imageLocation;
		displayNote.value = sketchList[currentIndex].note;
	}
	console.log(sketchList);
});



const xhr = new XMLHttpRequest();
xhr.onreadystatechange = function() {
	if(xhr.readyState === 4 && xhr.status === 200){
		const res = xhr.response;
//		note.innerHTML = res;
	}
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
