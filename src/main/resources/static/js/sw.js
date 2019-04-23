importScripts('canvasJavaScript.js','dropdownDecks.js','polyfill.js','reorderSketches.js',
"stickyNav.js","update-note-and-buttons.js","uploadButton.js");


self.addEventListener('install', function(e) {
 e.waitUntil(
   caches.open('sketchflex').then(function(cache) {
     return cache.addAll([
        "css/colorScheme.css",
        "css/fontawesome.css",
        "css/footer.css",
        "css/indexOnly.css",
        "css/sketchDrawOnly.css",
        "css/style.css",
        "css/uploadLayout.css",
        "images/pencil-dash.png",
        "images/sketchflex.PNG",
        "images/update-note-and-buttons.js",
        "manifest/manifest.webmanifest.js",
        "populator/ChoiceBulbs.png",
        "populator/coffee.png",
        "populator/Dig.png",
        "populator/flower.png",
        "populator/PlantBulb.png",
        "populator/raptor.png",
        "populator/t-rex.png",
        "populator/t-rex2.png",
        "populator/tea.jpg",
        "populator/triceratops.gif",
        "populator/TulipBloom.png",
        "populator/TulipBudding.png",
        "populator/TulipBuddingWithePetals.png",
        "populator/TulipBulb.png",
        "populator/TulipGrowing.png",
        "populator/Water.png",
        "video/howitworks.mp4",
        "webfonts/fa-solid-900.woff",
        "templates/partials/sketch-note-updated.html",
        "templates/partials/sketchDeck-order-changed.html",
        "templates/all-sketchdecks-template.html",
        "templates/all-sketches-template.html",
        "templates/index.html",
        "templates/single-sketch-template.html",
        "templates/single-sketchdeck-template.html",
        "templates/sketch-draw-template.html",
        "templates/sketch-upload-template.html",
        "application.properties"
     ]);
   })
 );
});

self.addEventListener('fetch', function(event) {
 console.log(event.request.url);

 event.respondWith(
   caches.match(event.request).then(function(response) {
     return response || fetch(event.request);
   })
 );
});