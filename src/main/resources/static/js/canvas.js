var canvas,ctx;

var mouseX,mouseY,mouseDown=0;

function drawDot(ctx,x,y,size) {
	r=0; g=0; b=0; a=255;

	ctx.fillStyle = "rgba("+r+","+g+","+b+","+(a/255)+")";

	ctx.beginPath();
	ctx.arc(x, y, size, 0, Math.PI*2, true); 
	ctx.closePath();
	ctx.fill();
} 

function clearCanvas(canvas,ctx) {
	ctx.clearRect(0, 0, canvas.width, canvas.height);
}

function sketchpad_mouseDown() {
	mouseDown=1;
	drawDot(ctx,mouseX,mouseY,12);
}

function sketchpad_mouseUp() {
	mouseDown=0;
}

function sketchpad_mouseMove(e) { 
	getMousePos(e);

	if (mouseDown==1) {
			drawDot(ctx,mouseX,mouseY,12);
	}
}

function getMousePos(e) {
	if (!e)
			var e = event;

	if (e.offsetX) {
			mouseX = e.offsetX;
			mouseY = e.offsetY;
	}
	else if (e.layerX) {
			mouseX = e.layerX;
			mouseY = e.layerY;
	}
 }

function init() {
	canvas = document.getElementById('sketchpad');

	if (canvas.getContext)
			ctx = canvas.getContext('2d');

	if (ctx) {
			canvas.addEventListener('mousedown', sketchpad_mouseDown, false);
			canvas.addEventListener('mousemove', sketchpad_mouseMove, false);
			window.addEventListener('mouseup', sketchpad_mouseUp, false);
	}
}