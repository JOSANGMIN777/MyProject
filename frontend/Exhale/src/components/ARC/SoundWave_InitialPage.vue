<template>
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
        <label :style="waveStyle"></label>
        <label :style="waveStyle"></label>
  
      </div>
  </template>

<script setup>
import { computed, defineProps } from 'vue';

const props = defineProps({
  volume: Number
});
const waveStyle = computed(() => {
  const height = props.volume > 0 ? props.volume / 2 : 10;
  const scaleY = props.volume > 0 ? 'scaleY(1)' : 'scaleY(0.3)';
  return {
    height: `${height}px`,
    transform: scaleY,
  };
});
</script>

<style lang="scss" scoped>

// .active #box > label {
//   animation-play-state: running;
// }


#box {
    position: fixed;
    top: 72%;
    width: 30%;
    height: 10%;
    display: flex;
    justify-content: center;
    align-items: center;
    /* transform: scaleY(0.2); */
    /* position: relative; */
}

#box>label{
  /* position: absolute; */
  position: fixed;
  /* bottom: 50%; */
  /* top:50%; */
  width: 5px;
  background-color: rgb(108, 159, 156);
  opacity: 70%;
  border-radius: 20%;
  /* transform-origin: center; */
  /* transform: scaleY(0.1); */
  animation: wave-animation 1s ease-in-out infinite; 
  animation-play-state: paused;
  max-height: 40px;
}

// #box>label[style*="scaleY(1)"] {
//   animation-play-state: running;
// }

#box>label:nth-of-type(1){
    left:620px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(2){
    left:630px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(3){
    left:640px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(4){
    left:650px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(5){
    left:660px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(6){
    left:670px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(7){
    left:680px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(8){
    left:690px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(9){
    left:700px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(10){
    left:710px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(11){
    left:720px;
    animation:wave-animation 1s ease-in .2s infinite;
}
#box>label:nth-of-type(12){
    left:730px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(13){
    left:740px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(14){
    left:750px;
    animation:wave-animation 1s ease-in .2s infinite;
}
#box>label:nth-of-type(15){
    left:760px;
    animation:wave-animation 1s ease-in .4s infinite;
}
#box>label:nth-of-type(16){
    left:770px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(17){
    left:780px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(18){
    left:790px;
    animation:wave-animation 1s ease-in .2s infinite;
}
#box>label:nth-of-type(19){
    left:800px;
    animation:wave-animation 1s ease-in .5s infinite;
}
#box>label:nth-of-type(20){
    left:810px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(21){
    left:820px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(22){
    left:830px;
    animation:wave-animation 1s ease-in .5s infinite;
}
#box>label:nth-of-type(23){
    left:840px;
    animation:wave-animation 1s ease-in .2s infinite;
}
#box>label:nth-of-type(24){
    left:850px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(25){
    left:860px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(26){
    left:870px;
    animation:wave-animation 1s ease-in .3s infinite;
}
#box>label:nth-of-type(27){
    left:880px;
    animation:wave-animation 1s ease-in .2s infinite;
}
#box>label:nth-of-type(28){
    left:890px;
    animation:wave-animation 1s ease-in .6s infinite;
}
#box>label:nth-of-type(29){
    left:900px;
    animation:wave-animation 1s ease-in .1s infinite;
}
#box>label:nth-of-type(30){
    left:910px;
    animation:wave-animation 1s ease-in .4s infinite;
}
@keyframes wave-animation{
    0% {
        transform:scaleY(0.2);
    }
    50%{
        transform:scaleY(0.5);
    }
    100%{
        transform:scaleY(1);
    }
}

</style>
