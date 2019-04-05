/**
 * 
 */

var dropDown = document.getElementById("deckDropDown");
var textBox = document.getElementById("deckTextBox")
dropDown.addEventListener('change', dropDownChanged);

function dropDownChanged(){
	if(dropDown.value === "New SketchDeck..."){
		textBox.style.display = "block";
		dropDown.setAttribute("name", "");
		textBox.setAttribute("name", "sketchDeckName");
	}else{
		dropDown.setAttribute("name", "sketchDeckName");
		textBox.setAttribute("name", "");
		textBox.style.display = "none";
	}
}
