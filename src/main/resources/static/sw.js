

importScripts('polyfill.js');


self.addEventListener('install', function(event) {
 event.waitUntil(
   caches.open('sketchflex').then(function(cache) {
     return cache.addAll([
        "/draw",
        "/sketches",
        "/sketchdecks",
        "/",
        "/index.html",
        "../css/colorScheme.css",
        "../css/fontawesome.css",
        "../css/footer.css",
        "../css/indexOnly.css",
        "../css/sketchDrawOnly.css",
        "../css/style.css",
        "../css/uploadLayout.css",
        "../images/pencil-dash.png",
        "../images/sketchflex.PNG",
        "../images/sketchflex192.PNG",
        "../images/sketchflex512.PNG",
        "../images/storyboard-template.jpg",
        "/js/canvasJavaScript.js",
        "/js/dropdownDecks.js",
        "/js/reorderSketches.js",
        "/js/update-note-and-buttons.js",
        "/js/uploadButton.js",
        "/js/jscolor.js",
        "../manifest/manifest.webmanifest",
        "../populator/ChoiceBulbs.png",
        "../populator/coffee.jpg",
        "../populator/Dig.png",
        "../populator/flower.png",
        "../populator/PlantBulb.png",
        "../populator/raptor.jpg",
        "../populator/t-rex.jpg",
        "../populator/t-rex2.png",
        "../populator/tea.jpg",
        "../populator/triceratops.gif",
        "../populator/TulipBloom.png",
        "../populator/TulipBudding.png",
        "../populator/TulipBuddingWithePetals.png",
        "../populator/TulipBulb.png",
        "../populator/TulipGrowing.png",
        "../populator/Water.png",
        "../video/howitworks.mp4",
        "../webfonts/fa-solid-900.woff2",
        "../webfonts/Raleway.woff2",
        "../webfonts/Raleway2.woff2"
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

self.addEventListener('activate', function(event) {

  var cacheWhitelist = ['pages-cache-v1', 'blog-posts-cache-v1'];

  event.waitUntil(
    caches.keys().then(function(cacheNames) {
      return Promise.all(
        cacheNames.map(function(cacheName) {
          if (cacheWhitelist.indexOf(cacheName) === -1) {
            return caches.delete(cacheName);
          }
        })
      );
    })
  );
});
