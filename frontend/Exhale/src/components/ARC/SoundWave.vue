<template>
  <div class="sound-wave-container">
    <div id="box">

      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>
      <label :style="waveStyle"></label>

    </div>
  </div>
</template>

<script setup>
import { computed, defineProps } from 'vue';

const props = defineProps({
  volume: Number
});
// const waveStyle = computed(() => {

//   const height = props.volume / 2;
//   return {
//     height: `${height}px`,
//   };
// });

const waveStyle = computed(() => {
  const height = props.volume > 0 ? props.volume / 2 : 10;
  const scaleY = props.volume > 0 ? 'scaleY(1)' : 'scaleY(0.2)';
  return {
    height: `${height}px`,
    transform: scaleY,
  };
});

</script>

<style scoped>
.sound-wave-container {
  width: 20%;
  height: 100px;
  /* transform: scaleY(0.2); */
}

#box {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 500px;

  /* transform: scaleY(0.2); */
  /* position: relative; */
}

#box>label{
  /* position: absolute; */
  position: fixed;
  /* bottom: 50%; */
  /* top:50%; */
  width: 10px;
  background-color: rgb(255, 255, 255);
  border-radius: 10px;
  /* transform-origin: center; */
  /* transform: scaleY(0.2); */
  animation: wave-animation 1s ease-in-out infinite; 
  animation-play-state: paused;
  max-height: 40px;
}

#box>label:nth-of-type(1){
    left:50px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(2){
    left:70px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(3){
    left:90px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(4){
    left:110px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(5){
    left:130px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(6){
    left:150px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(7){
    left:170px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(8){
    left:190px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(9){
    left:210px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(10){
    left:230px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(11){
    left:250px;
    animation:wave-animation 1s ease-in .2s infinite;
}
#box>label:nth-of-type(12){
    left:270px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(13){
    left:290px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(14){
    left:310px;
    animation:wave-animation 1s ease-in .2s infinite;
}
@keyframes wave-animation{
    0% {
        transform:scaleY(0.2);
    }
    50%{
        transform:scaleY(0.7);
    }
    100%{
        transform:scaleY(1.5);
    }
}

</style>
