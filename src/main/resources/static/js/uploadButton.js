/**
 * 
 */
var upload = document.getElementById("upload");
upload.disabled = true;
var fileChosen = false;
var deckChosen = false;

var file = document.getElementById("file")
file.addEventListener("input", function(){
	fileChosen = true;
	enableUpload();
})

var deck = document.getElementById("deckDropDown")
deck.addEventListener("change", function(){
	deckChosen = true;
	enableUpload();
})

function enableUpload(){
	if(fileChosen===true&&deckChosen===true){
		upload.disabled = false;
	}
}
