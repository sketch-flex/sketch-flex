var sketchbox = document.createElement("canvas");
var main = document.getElementById("main");
var ctx = sketchbox.getContext('2d');
var isMouseDown = false;
currentSize = 1;
var linesArray = [];
var currentColor = "rgb(0,0,0)";
var currentOpacity = "1";


defineInitialCanvas();


// Create Sketch Pad
function defineInitialCanvas() {
   
	sketchbox.id = "sketchbox";
	//sketchbox.style.border = "1px solid";
	sketchbox.style.boxShadow = "2px 2px 5px 2px #666";
	sketchbox.style.backfaceVisibility = "hidden";
	const style = getComputedStyle(main);
    const style2 = getComputedStyle(document.getElementById("sidetools"));
    let width = parseInt(style.width) - parseInt(style.paddingLeft) - parseInt(style.paddingRight) - parseInt(style.columnGap) - parseInt(style2.width);
    let height = parseInt(style.height) - parseInt(style.paddingTop) - parseInt(style.paddingBottom);
    const style3 = getComputedStyle(document.getElementById("collapsablebtns"));
    if((style3.display === "flex" || style3.display === "none") && style3.flexDirection === "row"){
     width = parseInt(style.width) - parseInt(style.paddingLeft) -  parseInt(style.paddingRight);
     height = parseInt(style.height) - parseInt(style.paddingTop) - parseInt(style.paddingBottom) - parseInt(style2.height)- parseInt(style.rowGap);
     console.log("this happened");
    }
	sketchbox.setAttribute("width",width);
	sketchbox.setAttribute("height",height);
	ctx.fillStyle = "white";
	ctx.fillRect(0, 0, sketchbox.width, sketchbox.height);
	main.appendChild(sketchbox);
}


//dynamic-resize
window.addEventListener('resize', evt => {  
  main.removeChild(sketchbox);
  defineInitialCanvas();
  redraw();
  
});

// PC Drawing Event Handlers

sketchbox.addEventListener('mousedown', function (event) { 
event.preventDefault();
mousedown(sketchbox, event); 
});

sketchbox.addEventListener('mousemove', function () { mousemove(sketchbox, event); });
document.addEventListener('mouseup', mouseup);

// Touch Drawing Event Handlers
sketchbox.addEventListener('touchstart', function (event) {
	event.preventDefault();
	mousedown(sketchbox,event);
});

sketchbox.addEventListener('touchmove', function (event) {
	event.preventDefault();
	mousemove(sketchbox,event);
});

sketchbox.addEventListener('touchend', function (event) {
    event.preventDefault();
    mouseup();
});

//Button Event Handler
document.getElementById('eraser').addEventListener('click', eraser);
document.getElementById('draw').addEventListener('click', draw);
// document.getElementById('download').addEventListener('click', function () {
// 	downloadCanvas(this, 'sketchbox', document.getElementById('textbox').value);
// }, false);
document.getElementById("clear").addEventListener('click', function(){
  defineInitialCanvas();
  linesArray = [];
});
document.getElementById("penColor").addEventListener('click', function () {
	const colorPicker = document.getElementById("colorPicker");
	colorPicker.click();
});
document.getElementById("colorPicker").addEventListener('change',function(){
	currentColor = this.value;
});


document.getElementById("pensizesmall").addEventListener('click',function(){
	currentSize = 1;
	if(document.querySelector(".inUse") === null){
	  document.getElementById("pensizesmall").classList.toggle("inUse");
	}
	document.querySelector(".inUse").classList.toggle("inUse");
	document.getElementById("pensizesmall").classList.toggle("inUse");
	
})
document.getElementById("pensizemedium").addEventListener('click',function(){
	currentSize = 5;
	if(document.querySelector(".inUse") === null){
	  document.getElementById("pensizemedium").classList.toggle("inUse");
	}
	document.querySelector(".inUse").classList.toggle("inUse");
	document.getElementById("pensizemedium").classList.toggle("inUse");
})
document.getElementById("pensizelarge").addEventListener('click',function(){
	currentSize = 10;
	if(document.querySelector(".inUse") === null){
	  document.getElementById("pensizelarge").classList.toggle("inUse");
	}
	document.querySelector(".inUse").classList.toggle("inUse");
	document.getElementById("pensizelarge").classList.toggle("inUse");
})
document.getElementById("collapse").addEventListener('click',function(){
   collapse();
})

document.getElementById('save').addEventListener('click', function(){
    document.getElementById('lob').value = sketchbox.toDataURL();
	const  form = document.querySelector('.js-form');
	form.classList.toggle('formHidden');
	main.removeChild(sketchbox);
    defineInitialCanvas();
    redraw();
    
    
    const style3 = getComputedStyle(document.getElementById("collapsablebtns"));
    if(style3.display === "flex" && style3.flexDirection === "row"){
        document.getElementById('collapsablebtns').classList.toggle('closed');        
        document.getElementById("plusMinus").classList.toggle("fa-plus-circle");
		main.removeChild(sketchbox);
        defineInitialCanvas();
    	redraw();
    }
 
})






function collapse(){
  	const buttons = document.getElementById('collapsablebtns');
	buttons.classList.toggle('closed');
	document.getElementById("sidetools").classList.toggle("sideclosed");
	document.getElementById("plusMinus").classList.toggle("fa-plus-circle");
	main.removeChild(sketchbox);
	defineInitialCanvas();
	redraw();
	
	if(!document.querySelector('.js-form').classList.contains("formHidden")){
	  document.querySelector('.js-form').classList.toggle('formHidden');
	  main.removeChild(sketchbox);
	  defineInitialCanvas();
	  redraw();
	}

}


//Eraser Function

function eraser() {
	currentColor = ctx.fillStyle;
	currentSize = 20;
}

//Draw Function
function draw() {
	currentColor = "rgb(0,0,0)";
	currentSize = 5;
}


// GET MOUSE POSITION

function getMousePos(sketchbox, event) {
	
	var boundaryRect = sketchbox.getBoundingClientRect();
    
          
    if(event.touches != null){
		return {
		  x:event.touches[0].clientX - boundaryRect.left,
		  y:event.touches[0].clientY - boundaryRect.top
		};
	}else{
	    return{
	      x: event.clientX - boundaryRect.left,
		  y: event.clientY - boundaryRect.top
		};
	}
}


// Mouse Down Event Function

var currentPosition;

function mousedown(sketchbox, event) {
	var mousePos = getMousePos(sketchbox, event);
	isMouseDown = true;
	currentPosition = getMousePos(sketchbox, event);
	ctx.moveTo(currentPosition.x, currentPosition.y);
	ctx.beginPath();
	ctx.lineWidth = currentSize;
	ctx.lineCap = "round";
	ctx.strokeStyle = currentColor;
	ctx.stroke();
	store(currentPosition.x, currentPosition.y, currentSize, currentColor);
}
// ON MOUSE MOVE

function mousemove(sketchbox, event) {

	if (isMouseDown) {
	    ctx.moveTo(currentPosition.x,currentPosition.y);
		currentPosition = getMousePos(sketchbox, event);
		ctx.lineTo(currentPosition.x, currentPosition.y);
		ctx.stroke();
		store(currentPosition.x, currentPosition.y,  currentSize, currentColor);
	}
}

// ON MOUSE UP

function mouseup() {
	isMouseDown = false
	store();
}
// Download Sketchbox

function downloadCanvas(link, sketchbox, filename) {
	link.href = document.sketchbox.toDataURL();
	link.download = filename;
}



function store(x, y, s, c) {
		var line = {
			"x": x,
			"y": y,
			"size": s,
			"color": c
		}
		linesArray.push(line);
	}
	
function redraw() {
				for (var i = 1; i < linesArray.length; i++) {
					ctx.beginPath();
					ctx.moveTo(linesArray[i-1].x, linesArray[i-1].y);
					ctx.lineWidth  = linesArray[i].size;
					ctx.lineCap = "round";
					ctx.strokeStyle = linesArray[i].color;
					ctx.lineTo(linesArray[i].x, linesArray[i].y);
					ctx.stroke();
				}
		}
function toggleFullScreen() {

  var requestFullScreen = main.requestFullscreen || main.mozRequestFullScreen || main.webkitRequestFullScreen || main.msRequestFullscreen;
  var cancelFullScreen = document.exitFullscreen || document.mozCancelFullScreen || document.webkitExitFullscreen || document.msExitFullscreen;
  
  document.querySelector("#fullscreen i").classList.toggle("fa-window-restore");
  
if(!document.fullscreenElement && !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement) {
    requestFullScreen.call(main);
  }
  else {
    cancelFullScreen.call(document);
  }
}		
