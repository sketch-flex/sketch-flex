var navbar = document.getElementById("navbar");
navbar.innerHTML = `
	<ul id = "myLinks">
		<li><a href="http://localhost:8080/home">Home</a></li>
		<li><a href="http://localhost:8080/sketchdecks">SketchDecks</a></li>
		<li><a href="http://localhost:8080/draw">New Sketch</a></li>
		<li><a href="http://localhost:8080/sketches">All Sketches</a></li>
		<li><a class="active" href="http://localhost:8080/upload">Upload Sketch</a></li>
	</ul>
	
	`;
	

var hamburger = document.getElementById("hamburger");

hamburger = hamburger.addEventListener("click", myFunction);
	
function myFunction() {
    var x = document.getElementById("myLinks");
    if (x.style.display === "block") {
      x.style.display = "none";
      x.style.height = "0px";
    } else {
      x.style.display = "block";
      x.style.height = "fit-content";
      
    }
  }