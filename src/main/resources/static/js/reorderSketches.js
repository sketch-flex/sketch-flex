/**
Add a reorder menu to the end of each "name-and-image" element
 */
var eachSketch = document.getElementsByClassName("name-and-image");

for(i=0; i < eachSketch.length; i++){
	var pButton = document.createElement("button");
	pButton.innerHTML = "<";
	pButton.setAttribute("id", "p"+i);
	
	var nButton = document.createElement("button");
	nButton.innerHTML = ">";
	nButton.setAttribute("id", "n"+i);
	
	var menu = document.createElement("div");
	menu.setAttribute("class", "reorderMenu");
	
	eachSketch[i].appendChild(menu);
	menu.appendChild(pButton);
	var text = document.createElement("a");
	text.setAttribute("class", "reorderText");
	text.innerHTML = "Reorder Sketch";
	menu.appendChild(text);
	menu.appendChild(nButton);
	
	pButton.addEventListener("click", previous);
	nButton.addEventListener("click", next);
}

function previous(sketch){
	console.log(sketch.target)
}

function next(sketch){
	console.log(sketch.target)
}

/**
Disable the link to single sketch view when reorder menu is hovered.
 */
var menus = document.getElementsByClassName("reorderMenu");
for(i=0; i<menus.length; i++){
	menus[i].addEventListener("mouseover", dontLink);
	menus[i].addEventListener("mouseout", link);
}

function dontLink(){
	for(i=0; i < eachSketch.length; i++){
		eachSketch[i].setAttribute("onClick", "return false");
	}
}
function link(){
	for(i=0; i < eachSketch.length; i++){
		eachSketch[i].setAttribute("onClick", "return true");
	}
}

