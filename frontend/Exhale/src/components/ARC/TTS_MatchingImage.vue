<template>
  <div>
    <button @click="speak">
      <img src="@/assets/headphone.svg" id="tts-button" alt="">
      <span v-if="!showButton" @click="skip">해설 넘어가기</span>
    </button>
      <img src="@/assets/headphone.svg" class="tts-button2" alt="">
    <!-- <img src="@/assets/mic.svg" class="backcircle" > -->
    </div>
</template>

<script setup>
import { onMounted, ref, defineProps, watch} from 'vue';
const props = defineProps({
  text: String,
  showButton: {
    type: Boolean,
    default: true 
  }
  });  

  const selectedLang = ref("ko-KR");
  const text = ref(props.text);
  const isReading = ref(props.isReading);
  
    watch(() => props.text, (newValue) => {
    text.value = newValue;
    speak(); 
  });

  onMounted(() => {
    speak();
  });
  
  // const emit = defineEmits(["update:isReading"]);
  // watch(isReading, () => {
  //   emit('update:isReading', isReading.value);
  // });
  const emit = defineEmits(["tts-start", "tts-end"]);

  const skip = () => {
    text.value=''; 
  }
  // function speak() {
  //   isReading.value = true;
  // if (typeof SpeechSynthesisUtterance === "undefined" || typeof window.speechSynthesis === "undefined") {
  //   alert("이 브라우저는 음성 합성을 지원하지 않습니다.")
  //   return
  // }
  function speak() {
  emit('tts-start');
  isReading.value = true;
  
  window.speechSynthesis.cancel(); // 현재 읽고있다면 초기화

  const speechMsg = new SpeechSynthesisUtterance();
  speechMsg.rate = 1; // 속도: 0.1 ~ 10      
  speechMsg.pitch = 1.2; // 음높이: 0 ~ 2
  speechMsg.lang = selectedLang.value;
  speechMsg.text = text.value;

  speechMsg.onend = function() {
    isReading.value = false;
    emit('tts-end');
  };

  window.speechSynthesis.speak(speechMsg);
}
</script>

<style lang="scss" scoped>


#tts-button {
  filter: brightness(110%);
  position: fixed;
  top: 18%;
  left: 35%;
  box-shadow: 1px 5px 4px 3px rgb(170, 169, 169);
  border-radius: 100%;
  width: 55px;
  z-index: 2;
  &:hover {
    opacity: 50%;
  }
}

.tts-button2 {
  position: fixed;
  top: 18%;
  left: 35%;
  width: 55px;
  box-shadow: 1px -2px 10px -1px rgb(255, 255, 255);
  border-radius: 100%;
  z-index: -1;
}

span {
font-family: 'NotoSansKR';
letter-spacing: 2px;
width: 30px;
height: 100px;
border: none;
background-color: #EAEAEA;
box-shadow: 1px 5px 4px 3px rgb(170, 169, 169);
border-radius: 10px;
padding-left: 8px;
padding-right: 8px;
padding-top: 20px;
padding-bottom: 20px;
}


</style>