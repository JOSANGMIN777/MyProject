<script setup>
import {ref, onMounted} from 'vue'
const transcript = ref('')
const isRecording = ref(false)
const Recognition = window.SpeechRecognition || window.webkitSpeechRecognition
const sr = new Recognition()
sr.lang = "ko-KR"

onMounted(()=>{
  sr.countinuous = true
  sr.interimResults = true

  sr.onstart = () =>{
    console.log('SR Started')
    isRecording.value = true
  }

  sr.onend = () =>{
    console.log('SR Stopped')
    isRecording.value = false
  }

  sr.onresult = (evt) => {
    console.log(evt)
    const t = Array.from(evt.results)
              .map(result => result[0])
              .map(result => result.transcript)
              .join('')
    
    transcript.value = t
  }

})
const ToggleMic = () => {
	if (isRecording.value) {
		sr.stop()
	} else {
		sr.start()
	}
}
</script>

<template>
  <div class = "app">
    <h1>Hi</h1>
    <button :class="`mic`" @click = "ToggleMic">Record</button>
    <div class = "transcript" v-text="transcript"> </div>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Fira sans', sans-serif;
}
body {
  background-color: black;
  color: aliceblue;
}
</style>
