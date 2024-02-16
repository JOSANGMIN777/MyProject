<template>
  <div class="hero">
    <h1>Text To Speech <span>Converter</span></h1>
    <textarea placeholder="Write anything here..."></textarea>
    <div class = "row">
      <select></select>
      <button>Listen</button>
    </div>

  </div>
</template>

<script>

let speech = new SpeechSynthesisUtterance() //This line creates a new instance of the SpeechSynthesisUtterance object, which represents the text that will be spoken.
window.onload = function(){
  document.querySelector("button").addEventListener("click", () =>{    
    speech.text = document.querySelector("textarea").value;             
    window.speechSynthesis.speak(speech);                                
  })
}
;


// const voices = []; //This line initializes an empty array called voices where the available voices will be stored.
// const voiceSelect = document.querySelector("select"); //This line selects the <select> element from the HTML document, presumably used for selecting different voices.


// window.speechSynthesis.onvoiceschanged = () =>{   //is an event that fires when the list of available voices has been updated. The assigned arrow function is a callback that executes when this event occurs.
//     voices = window.speechSynthesis.getVoices(); //This line retrieves the available voices using the getVoices() method and assigns them to the voices array.
//     speech.voice = voices[0];  //This sets the voice property of the SpeechSynthesisUtterance object (speech) to the first voice in the voices array

//     voices.forEach((voice, i) => (voiceSelect.options[i] = new Option(voice.name, i))); //This loop iterates over the voices array and populates the <select> element (voiceSelect) with options representing different voices. It creates a new Option object for each voice and assigns the voice's name as the option text and the index i as the option value.
// };

// voiceSelect.addEventListener("change" , () =>{

//     speech.voice = voices[voiceSelect.value] //sets the voice property of the SpeechSynthesisUtterance object (speech) to the selected voice, allowing the user to choose a specific voice from the dropdown menu.

// });


</script>

<style>
*{
  margin: 0;
  padding: 0;
  font-family: 'Poppins', sans-serif;
  box-sizing: border-box;
}

.hero{
  width: 100%;
  min-height: 100vh;
  background: linear-gradient(45deg, #010758, #490d61);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  color : #fff;
}
.hero h1{
  font-size: 45px;
  font-weight: 500;
  margin-top: -50px;
  margin-bottom: 50px;
}
.hero h1 span {
  color: #ff2963; 

}
textarea{
  width: 600px;
  height: 250px;
  background: #403d84;
  color: #fff;
  font-size: 15px;
  border:0;
  padding: 20px;
  border-radius: 10px;
  resize: none;
  margin-bottom: 30px;
}
textarea ::placeholder{
   font-size: 16px;
   color: #ddd;
}
.row{
  width: 600px;
  display: flex;
  align-items: center;
  gap: 20px;
}
button{
  background: #ff2963;
  color: #fff;
  font-size: 16px;
  padding: 10px 30px;
  border-radius: 35px;
  border: 0;
  outline: 0;
  cursor: pointer;
}
select{
  flex: 1;
  color: #fff;
  background: #403d84;
  height: 50px;
  padding: 0 20px;
  outline: 0;
  border: 0;
  border-radius: 35px;
}
</style>