var navbar = document.getElementById("navbar");
navbar.innerHTML = `
<a href="http://localhost:8080/home">Home</a>
<a href="http://localhost:8080/sketchdecks">SketchDecks</a>
<a href="http://localhost:8080/draw">New Sketch</a>
<a href="http://localhost:8080/sketches">All Sketches</a>
<a class="active" href="http://localhost:8080/upload">Upload Sketch</a>`;