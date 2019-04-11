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
	var style = getComputedStyle(main);
    var style2 = getComputedStyle(main.firstElementChild);
    var width = parseInt(style.width) - parseInt(style.paddingLeft) - parseInt(style.paddingRight) - parseInt(style.columnGap) - parseInt(style2.width);
    var height = parseInt(style.height) - parseInt(style.paddingTop) - parseInt(style.paddingBottom);
	sketchbox.setAttribute("width",width);
	sketchbox.setAttribute("height",height);
	ctx.fillStyle = "white";
	ctx.fillRect(0, 0, sketchbox.width, sketchbox.height);
	main.appendChild(sketchbox);
}


//dynamic-resize
const delay = 0;  // Your delay here

const originalResize = evt => {
  console.log(evt);  
  main.removeChild(sketchbox);
  defineInitialCanvas();
  redraw();
};


(() => {
  resizeTaskId = null;

  window.addEventListener('resize', evt => {
    if (resizeTaskId !== null) {
      clearTimeout(resizeTaskId);
    }

    resizeTaskId = setTimeout(() => {
      resizeTaskId = null;
      originalResize(evt);
    }, delay);
  });
})();

// DRAWING EVENT HANDLERS

sketchbox.addEventListener('mousedown', function () { mousedown(sketchbox, event); });
sketchbox.addEventListener('mousemove', function () { mousemove(sketchbox, event); });
sketchbox.addEventListener('mouseup', mouseup);

//Button Event Handler
document.getElementById('eraser').addEventListener('click', eraser);
document.getElementById('draw').addEventListener('click', draw);
document.getElementById('download').addEventListener('click', function () {
	downloadCanvas(this, 'sketchbox', document.getElementById('textbox').value);
}, false);
document.getElementById("clear").addEventListener('click', function(){
  defineInitialCanvas();
  linesArray = [];
});
document.getElementById("penColor").addEventListener('change', function () {
	currentColor = this.value;
});
document.getElementById("pensize").addEventListener('change', function () {
	currentSize = this.value; document.getElementById("dotSize").innerHTML = this.value;
})
document.getElementById("opacity").addEventListener('change', function () { sketchbox.style.opacity = this.value; document.getElementById("opacityValue").innerHTML = this.value * 100; })


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
    console.log(boundaryRect);
    
	return {
		x: event.clientX - boundaryRect.left,
		y: event.clientY - boundaryRect.top
	};
}


// Mouse Down Event Function

function mousedown(sketchbox, event) {
	var mousePos = getMousePos(sketchbox, event);
	isMouseDown = true
	var currentPosition = getMousePos(sketchbox, event);
	ctx.moveTo(currentPosition.x, currentPosition.y)
	ctx.beginPath();
	ctx.lineWidth = currentSize;
	ctx.lineCap = "round";
	ctx.strokeStyle = currentColor;
	store(currentPosition.x, currentPosition.y, currentSize, currentColor);
}
// ON MOUSE MOVE

function mousemove(sketchbox, event) {

	if (isMouseDown) {
		var currentPosition = getMousePos(sketchbox, event);
		ctx.lineTo(currentPosition.x, currentPosition.y);
		ctx.stroke();
		store(currentPosition.x, currentPosition.y, currentSize, currentColor);
	}
}

// ON MOUSE UP

function mouseup() {
	isMouseDown = false
	document.getElementById('lob').value = document.getElementById('sketchbox').toDataURL(); //changes value of 'lob' input when mouseup
	store();
}
// Download Sketchbox

function downloadCanvas(link, sketchbox, filename) {
	link.href = document.getElementById(sketchbox).toDataURL();
	link.download = filename;
}

const collapsablebtns = document.getElementById("collapsablebtns");
const sidetools = document.getElementById("sidetools");
const sidetooltoggle = document.getElementById("sidetooltoggle");

function sketchToolToggle() {
	collapsablebtns.classList.toggle("closed");
	sidetools.classList.toggle("sideclosed");
	sidetooltoggle.classList.toggle("btnclosed");
		
	}



document.getElementById("sidetooltoggle").addEventListener("click", sketchToolToggle);


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