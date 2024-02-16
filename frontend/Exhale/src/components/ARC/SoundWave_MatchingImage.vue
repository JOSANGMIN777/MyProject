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
      <!-- <label :style="waveStyle"></label> -->

    </div>
  </div>
</template>

<script setup>
import { ref, computed, defineProps } from 'vue';

const props = defineProps({
  isActive: Boolean
});


// const waveStyle = computed(() => {

//   const height = props.volume / 2;
//   return {
//     height: `${height}px`,
//   };
// });

const waveStyle = computed(() => {
  return {
    height: '100%',
    transform: props.isActive ? 'scaleY(1)' : 'scaleY(0.2)',
  };
});

</script>

<style scoped>
.sound-wave-container {
  width: 20%;
  height: 100px;
  background-color: white;
  display: flex;
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

#box>label[style*="scaleY(1)"] {
  animation-play-state: running;
}

#box>label:nth-of-type(1){
    left:50px;
    animation-delay: .1s;
}
#box>label:nth-of-type(2){
    left:60px;
    animation-delay: .6s;
}
#box>label:nth-of-type(3){
    left:70px;
    animation-delay: .3s;
}
#box>label:nth-of-type(4){
    left:80px;
    animation-delay: .1s;
}
#box>label:nth-of-type(5){
    left:90px;
    animation-delay: .3s;
}
#box>label:nth-of-type(6){
    left:100px;
    animation-delay: .6s;
}
#box>label:nth-of-type(7){
    left:110px;
    animation-delay: .3s;
}
#box>label:nth-of-type(8){
    left:120px;
    animation-delay: .6s;
}
#box>label:nth-of-type(9){
    left:130px;
    animation-delay: .2s;
}
#box>label:nth-of-type(10){
    left:140px;
    animation-delay: .4s;
}
#box>label:nth-of-type(11){
    left:150px;
    animation-delay: .2s;
}
#box>label:nth-of-type(12){
    left:160px;
    animation-delay: .3s;
}
#box>label:nth-of-type(13){
    left:170px;
    animation-delay: .6s;
}
#box>label:nth-of-type(14){
    left:180px;
    animation-delay: .2s;
}
#box>label:nth-of-type(15){
    left:190px;
    animation-delay: .4s;
}
#box>label:nth-of-type(16){
    left:200px;
    animation-delay: .1s;
}
#box>label:nth-of-type(17){
    left:210px;
    animation-delay: .5s;
}
#box>label:nth-of-type(18){
    left:220px;
    animation-delay: .2s;
}
#box>label:nth-of-type(19){
    left:230px;
    animation-delay: .6s;
}
#box>label:nth-of-type(20){
    left:240px;
    animation-delay: .1s;
}
#box>label:nth-of-type(21){
    left:250px;
    animation-delay: .7s;
}
#box>label:nth-of-type(22){
    left:260px;
    animation-delay: .5s;
}
#box>label:nth-of-type(23){
    left:270px;
    animation-delay: .8s;
}
#box>label:nth-of-type(24){
    left:280px;
    animation-delay: .2s;
}
#box>label:nth-of-type(25){
    left:290px;
    animation-delay: .5s;
}
#box>label:nth-of-type(26){
    left:300px;
    animation-delay: .3s;
}
#box>label:nth-of-type(27){
    left:310px;
    animation-delay: .4s;
}
#box>label:nth-of-type(28){
    left:320px;
    animation-delay: .8s;
}
#box>label:nth-of-type(29){
    left:330px;
    animation-delay: .2s;
}
#box>label:nth-of-type(30){
    left:340px;
    animation-delay: .7s;
}
@keyframes wave-animation{
    0% {
        transform:scaleY(0.1);
    }
    50%{
        transform:scaleY(0.3);
    }
    100%{
        transform:scaleY(0.5);
    }
}

</style>
