/**
Add a reorder menu to the end of each "name-and-image" element
 */
window.onload = function(){
	drawButtons();
}
function drawButtons(){
	var eachSketch = document.getElementsByClassName("name-and-image");
	for(i=0; i < eachSketch.length; i++){
		var pButton = document.createElement("button");
		pButton.innerHTML = "<";
		pButton.setAttribute("id", i);
	
		var nButton = document.createElement("button");
		nButton.innerHTML = ">";
		nButton.setAttribute("id", i);
	
		var menu = document.createElement("div");
		menu.setAttribute("class", "reorderMenu");
	
		eachSketch[i].appendChild(menu);
		menu.appendChild(pButton);
		var text = document.createElement("a");
		text.setAttribute("class", "reorderText");
		text.innerHTML = "";
		menu.appendChild(text);
		menu.appendChild(nButton);
	
		pButton.addEventListener("click", previous);
		nButton.addEventListener("click", next);
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
}

function previous(sketch){
	if(sketch.target.id > 0){
		var idOne = sketch.target.parentElement.parentElement.href;
		idOne = idOne.split("=")[1];
		var idTwo = sketch.target.id - 1;
		idTwo = document.getElementById(idTwo).parentElement.parentElement.href;
		idTwo = idTwo.split("=")[1];
		submitOrder(idOne, idTwo);
	}
}

function next(sketch){
	var idOne = sketch.target.id;
	var idTwo = parseInt(idOne, 10) + 1;
	idTwo = document.getElementById(idTwo);
	if(idTwo != null){
		idOne = sketch.target.parentElement.parentElement.href;
		idOne = idOne.split("=")[1];
		idTwo = idTwo.parentElement.parentElement.href;
		idTwo = idTwo.split("=")[1];
		submitOrder(idOne, idTwo);
	}
}

var sketchList = document.getElementsByClassName("sketches-list")[0];
const xhr = new XMLHttpRequest();
xhr.onreadystatechange = function() {
	if(xhr.readyState === 4 && xhr.status === 200){
		const res = xhr.response;
		sketchList.innerHTML = res;
		drawButtons();
	}
}
function submitOrder(idOne, idTwo){
	xhr.open("POST", idOne + "/reorder/" + idTwo, true);
	xhr.send();
}

