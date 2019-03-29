
var previousButton = document.createElement("button");
previousButton.innerHTML = "Previous";

var nextButton = document.createElement("button");
nextButton.innerHTML = "Next";

var nextMenu = document.getElementById("next-previous-menu");
nextMenu.appendChild(previousButton);
nextMenu.appendChild(nextButton);

var displayName = document.getElementById("name");
var displayImage = document.getElementById("image");

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
