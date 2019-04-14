
var previousButton = document.createElement("button");
previousButton.innerHTML = "&#10094";

var nextButton = document.createElement("button");
nextButton.innerHTML = "&#10095";

var displayName = document.getElementById("name");
var displayImage = document.getElementById("image");

var nextMenu = document.getElementsByClassName("name-and-image")[0];
nextMenu.insertBefore(previousButton, displayImage);
nextMenu.appendChild(nextButton);


nextButton.addEventListener("click", function() {
	//gets json data created by GSON in the sketch controller.
	var json = document.getElementById("json").textContent;
	var sketchList = JSON.parse(json);

	for (var i = 0; i < sketchList.length; i += 1) {
		if (displayName.textContent === sketchList[i].name) {
			var currentIndex = i;
		}
	}
	if (currentIndex < sketchList.length-1) {
		currentIndex += 1;
		displayName.textContent = sketchList[currentIndex].name;
		displayImage.src= sketchList[currentIndex].imageLocation;
	}
});

previousButton.addEventListener("click", function() {
	var json = document.getElementById("json").textContent;
	var sketchList = JSON.parse(json);

	for (var i = 0; i < sketchList.length; i += 1) {
		if (displayName.textContent === sketchList[i].name) {
			var currentIndex = i;
		}
	}
	if (currentIndex >= 1) {
		currentIndex -= 1;
		displayName.textContent = sketchList[currentIndex].name;
		displayImage.src= sketchList[currentIndex].imageLocation;
	}
});
