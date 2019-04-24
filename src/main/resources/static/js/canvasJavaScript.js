
var sketchbox = document.createElement("canvas");
var main = document.getElementById("main");
var ctx = sketchbox.getContext('2d');
var isMouseDown = false;
currentSize = 1;
var lastSize = currentSize;
var linesArray = [];
var linesArray2 = [];
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
    const style4 = getComputedStyle(document.getElementById("buttons"));
    if((style3.display === "flex" || style3.display === "none") && style4.flexDirection === "row"){
     width = parseInt(style.width) - parseInt(style.paddingLeft) -  parseInt(style.paddingRight);
     height = parseInt(style.height) - parseInt(style.paddingTop) - parseInt(style.paddingBottom) - parseInt(style4.height) - parseInt(style.rowGap);
     console.log(style3.height);
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


window.addEventListener("orientationchange", function() {


window.setTimeout(function() {
  if(window.innerWidth < window.innerHeight){
    main.removeChild(sketchbox);
    defineInitialCanvas();
    redrawvertical();
  }
  else if(window.innerWidth > window.innerHeight){
    main.removeChild(sketchbox);
    defineInitialCanvas();
    redrawhorizontal();
  }
 },400);
});

// PC Drawing Event Handlers

sketchbox.addEventListener('mousedown', function (event) { 
event.preventDefault();
mousedown(sketchbox, event); 
});

sketchbox.addEventListener('mousemove', function () { mousemove(sketchbox, event); });


document.addEventListener('mouseup', mouseup);

document.addEventListener("touchmove",function(event){
  event.preventDefault();
});

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
document.getElementById('eraser').addEventListener('click', function(){
     eraser();
    if(document.querySelector(".inUse") === null){
	  document.getElementById("eraser").classList.toggle("inUse");
	}
	document.querySelector(".inUse").classList.toggle("inUse");
	document.getElementById("eraser").classList.toggle("inUse");
}
);
document.getElementById('draw').addEventListener('click', draw);
// document.getElementById('download').addEventListener('click', function () {
// 	downloadCanvas(this, 'sketchbox', document.getElementById('textbox').value);
// }, false);

document.getElementById("clear").addEventListener('click', function(){
  defineInitialCanvas();
  linesArray = [];
  linesArray2 = [];
});

document.getElementById("color_value").addEventListener("change",function(){
   currentColor = "#" + this.value;
})

document.getElementById("pensizesmall").classList.toggle("inUse");


document.getElementById("pensizesmall").addEventListener('click',function(){
	currentSize = 1;
	lastSize = 1;
	currentColor = "#" + document.getElementById("color_value").value;
	if(document.querySelector(".inUse") === null){
	  document.getElementById("pensizesmall").classList.toggle("inUse");
	}
	document.querySelector(".inUse").classList.toggle("inUse");
	document.getElementById("pensizesmall").classList.toggle("inUse");
	
})

document.getElementById("pensizemedium").addEventListener('click',function(){
	currentSize = 5;
	lastSize = 5;
	currentColor = "#" + document.getElementById("color_value").value;
	if(document.querySelector(".inUse") === null){
	  document.getElementById("pensizemedium").classList.toggle("inUse");
	}
	document.querySelector(".inUse").classList.toggle("inUse");
	document.getElementById("pensizemedium").classList.toggle("inUse");
})

document.getElementById("pensizelarge").addEventListener('click',function(){
	currentSize = 10;
	lastSize = 10;
	currentColor = "#" + document.getElementById("color_value").value;
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
	document.getElementById("sidetools").classList.toggle("sideclosed");
	document.getElementById("buttons").classList.toggle("sideclosed");
	document.getElementById("plusMinus").classList.toggle("fa-plus-circle");
	
	if(!document.querySelector("#fullscreen i").classList.contains("fa-plus-circle")){
	  document.getElementById('lob').value = sketchbox.toDataURL();
	}
	
	if(!document.querySelector('.js-form').classList.contains("formHidden")){
	  if(buttons.classList.contains("closed")){
	    document.getElementById("sidetools").classList.toggle("sideclosed");
	    document.getElementById("buttons").classList.toggle("sideclosed");
	    main.removeChild(sketchbox);
	    defineInitialCanvas();
	    redraw();    
	  }
	  document.querySelector('.js-form').classList.toggle('formHidden');
	}
	
	buttons.classList.toggle('closed');
	main.removeChild(sketchbox);
	defineInitialCanvas();
	redraw();
}

 
function eraser() {
	currentColor = ctx.fillStyle;
	currentSize = 20;
}


function draw() {
	currentColor = "#" + document.getElementById("color_value").value;
	currentSize = lastSize;
	if(currentSize === 1){
	  document.getElementById("pensizesmall").click();
	}else if(currentSize === 5){
	  document.getElementById("pensizemedium").click();
	}else if(currentSize === 10){
	  document.getElementById("pensizelarge").click();
	}
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
		var line2 = {
		    "x": window.innerHeight - y,
		    "y": x,
		    "size": s,
		    "color": c
		}
		
		linesArray.push(line);
		
		if(window.innerHeight > window.innerWidth){
		  linesArray2.push(line);
		}else{
		  linesArray2.push(line2);
		}
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
		

function redrawvertical(){
               for (var i = 1; i < linesArray2.length; i++) {
					ctx.beginPath();
					ctx.moveTo(linesArray2[i-1].x, linesArray2[i-1].y);
					ctx.lineWidth  = linesArray2[i].size;
					ctx.lineCap = "round";
					ctx.strokeStyle = linesArray2[i].color;
					ctx.lineTo(linesArray2[i].x, linesArray2[i].y);
					ctx.stroke();
				}

}
		
function redrawhorizontal(){
                for (var i = 1; i < linesArray2.length; i++) {
					ctx.beginPath();
					ctx.moveTo(linesArray2[i-1].y, window.innerHeight - linesArray2[i-1].x);
					ctx.lineWidth  = linesArray2[i].size;
					ctx.lineCap = "round";
					ctx.strokeStyle = linesArray2[i].color;
					ctx.lineTo(linesArray2[i].y, window.innerHeight - linesArray2[i].x);
					ctx.stroke();
				}

}

		

document.getElementById("fullscreen").addEventListener("click", toggleFullScreen);		
		
function toggleFullScreen() {

  var requestFullScreen = main.requestFullscreen || main.mozRequestFullScreen || main.webkitRequestFullScreen || main.msRequestFullscreen;
  var cancelFullScreen = document.exitFullscreen || document.mozCancelFullScreen || document.webkitExitFullscreen || document.msExitFullscreen;
  
  document.querySelector(".navbar").classList.toggle("hidden");
  document.getElementById("footer").classList.toggle("hidden");
  main.parentElement.classList.toggle("fullcontainer");
  main.classList.toggle("full");
	 
  document.querySelector("#fullscreen i").classList.toggle("fa-window-restore");
  main.removeChild(sketchbox);
  defineInitialCanvas();
  redraw(); 	 

  
  if(!document.fullscreenElement && !document.mozFullScreenElement && !document.webkitFullscreenElement && !document.msFullscreenElement) {
    requestFullScreen.call(main);
  }
  else {
    cancelFullScreen.call(document);
  }
  
  main.removeChild(sketchbox);
  defineInitialCanvas();
  redraw();
}		
