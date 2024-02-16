<template>
<div class="page-container">
    <header>
      <Headers />
    </header>
    <div class="contents">

    <div class="content-wrapper">
      <p class="title1" @click="toggleContent('title1')">날;숨이란 무엇인가요?</p>
      <transition name="slide">
        <div v-if="showContent.title1" key="content1">
          <p class="content">우리는 호흡을 할 때 자연스럽게 숨을 들이쉬고 내쉽니다.</p>
          <p class="content">여기서 착안한 날숨이라는 단어는 숨을 내쉬듯 자연스럽게 말을 하는 그날까지 함께 하겠다는 의미입니다.</p>
        </div>
    </transition>
</div>
<hr>

<div class="content-wrapper">
    <p class="title2" @click="toggleContent('title2')">왜 날;숨이어야 할까요?</p>
      <transition name="slide">
        <div v-if="showContent.title2" key="content2">
          <p class="content">인공지능을 이용한 학습 모델과 전문가의 자문으로 생성된 문제,</p>
          <p class="content">그리고 주요 사용자 분석을 통한 사용자 경험과 디자인까지 생각한 날;숨은 사용자분께서 완치되는 그날까지 함께하겠습니다.</p>
        </div>
    </transition>
</div>
<hr>

<div class="content-wrapper">
      <p class="title3" @click="toggleContent('title3')">날;숨은 어떻게 나오게 됐나요?</p>
      <transition name="slide">
        <div v-if="showContent.title3" key="content3">
          <p class="content">실어증 환자의 경우 지속적이고 일상적인 재활이 필요합니다.</p>
          <p class="content">하지만, 전문의료인에게 재활치료를 받게 될 경우 환자에게는 경제적 부담, 시간/공간적 제약 등이 큰 문제로 다가올 수 있습니다.</p>
          <p class="content">이러한 문제를 인식해 환자에게 좀 더 실질적인 도움을 줄 수 있는 사회공헌적 성격의 맞춤형 서비스가 탄생했습니다.</p>
        </div>
    </transition>
</div>
<hr>

</div>
    <footer> 
      <Footers />
    </footer>

</div>
  </template>
  
  <script setup>
  import Headers from '@/components/common/Headers.vue';
  import Footers from '@/components/common/Footers.vue';
  import { ref } from 'vue';
  
  const showContent = ref({
  title1: false,
  title2: false,
  title3: false
});

const toggleContent = (titleKey) => {
  showContent.value[titleKey] = !showContent.value[titleKey];
};
  </script>
  
  <style lang="scss" scoped>

.page-container {
    display: flex;
    flex-direction: column;
    min-height: 100vh;
}

  .contents {
    flex: 1; 
    margin-left: 11.5%;
    margin-right: 11.5%;
    margin-bottom: 10%;
    font-family: 'NotoSansKR';
  }
  
  .title1, .title2, .title3 {
    margin-top: 30px;
    // margin-bottom: 100px;
    color: gray;
    // color: #334F4E;
    // opacity: 70%;
    font-size: 170%;
    font-family: 'NotoSansKR-Bold';
    &:hover {
        // opacity: 50%;
        color: rgb(108, 159, 156);
        cursor: pointer;
    }
  }

  hr {
    margin-top: 60px;
  }


.content {
    color: #334F4E;
    font-size: 100%;
    margin-bottom: -20px;
    margin-top: 30px;
}

footer {
  position: fixed;
  top: 80%;
  bottom: 0; 
  left: 0; 
  width: 100%; 
  background-color: #fff; 
  color: rgb(188, 188, 188); 
  font-size: 90%;
  text-align: center; 
  padding: 10px 0; 
  box-sizing: border-box; 
}




</style>