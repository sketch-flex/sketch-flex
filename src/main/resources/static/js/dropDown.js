const endpoint = 'http://localhost:8080/allSketchDecks';
const sketchDecks = [];

const dropDown = document.getElementById("dropDown");
const sketchDeckNameInput = document.getElementById("sketchDeckNameInput");


// Get All sketchdecks and add to array
fetch(endpoint)
    .then(blob => blob.json())
    .then(data => sketchDecks.push(...data));
   
   
createDropDown();

//create dropdown menu
function createDropDown() {
    sketchDecks.forEach(function (deckName) {
        var option = document.createElement("option");
        option.innerHTML = deckName.name;
        dropDown.appendChild(option);
    })
};

//Update Sketch Deck input field based on drop down selection
dropDown.addEventListener('click',updateInput);

function updateInput(){
    sketchDeckNameInput.setAttribute("value", dropDown.value );
}

//displays list of sketchdecks that match the typing
// dropDownInput.addEventListener('keyup',displayMatches);

// function findMatches(wordToMatch, sketchDecks) {
//     return sketchDecks.filter(deck => {
//         const regex = new RegExp(wordToMatch, 'gi');
//         return deck.name.match(regex);
//     });
// }

// function displayMatches(){

// }