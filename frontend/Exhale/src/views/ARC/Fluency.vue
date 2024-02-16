<script setup>
import { ref, onMounted, onBeforeUnmount, computed, nextTick  } from 'vue';
import { storeToRefs } from "pinia";
import { useAuthStore } from "@/stores/auth";
import { getProblem, postSolvedProblem, postReview, checkfluencyAnswer  } from '@/api/course.js';
import STT_Fluency from '@/components/ARC/STT_Fluency.vue';
import TTS from '@/components/ARC/TTS.vue';
import ResultDialog from '@/components/ARC/ResultDialog.vue'
import { useRoute } from 'vue-router';


const route = useRoute()
const overTime = route.params.time;
const categoryId = route.params.id; 

const authStore = useAuthStore();
const { jwtToken } = storeToRefs(authStore);
const token = jwtToken.value;

let problemIdx=0;
let problemSet=null;

// 다이어로그
const resultDialog = ref(false);
const isRight = ref(false);
const againTick = ref(false);
const reviewTick = ref(false);
const isPause = ref(false);
const isReturn = ref(false);
const isComplete = ref(false);

const sttText = ref("");
const question = ref("");
const isReading = ref(false);
let isFirst = true;
let isExplain = false;

const problem = {
  problemId : ref(0),
  question: ref(''),
  explain: ref(''),
};


//타이머
const elapsedTime = ref(overTime);
let timerId;
const timerWidth = computed(() => (elapsedTime.value / overTime) * 100);


// 컴포넌트가 마운트될 때 시작하는 타이머 설정
const startTimer = () => {

  timerId = setInterval(() => {
    elapsedTime.value--;
    if (elapsedTime.value < 0) {
      elapsedTime.value = 0;
      resultProcessing("");
    }
  }, 1000);
};

// 컴포넌트가 언마운트될 때 타이머 정리
const stopTimer = () => {
  clearInterval(timerId);
};


const getProblems = async () => {
  try {
    const { data } = await getProblem(categoryId, token);
    if(data.dataStatus.code!=1){
      // todo api 응답 예외 처리
      return;
    }
    problemIdx = data.response.first_problem_index;
    problemSet = data.response.problemResponseList;

    problem.problemId.value = problemSet[problemIdx].problem_id;
    problem.question.value = problemSet[problemIdx].question;
    question.value = problem.question.value;
    clickTTSQustion();

} catch (error) {
    console.error(error); 
  }
};
const saveSolvedProblem = async() => {

  try{
    const params = {
      problemId: problem.problemId.value,
      isRight: isRight.value,
      time : overTime - elapsedTime.value
    }
    const {data} = await postSolvedProblem(params, token);
    if(data.dataStatus.code!=1){
      // todo api 응답 예외 처리
      return;
    }
  } catch(error){
    console.error(error); 
  }
}

const saveReviewProblem = async () => {
  try {
    const { data } = await postReview(problem.problemId.value, token);
    if(data.dataStatus.code==2){
      // todo api 응답 예외 처리
      return;
    }else if(data.dataStatus.code!=1){
    }
    stopTimer();
    isComplete.value = true;
  } catch (error) {
    if(error.response.data.dataStatus.code==4){
      console.log("이미 저장된 문제입니다.");
    }
    // console.error(error);

  }
};


const nextProblem = () => {

  if(problemIdx>=problemSet.length-1){
    // todo 게임 종료
    isComplete.value = true;
    return;
  }
  resultDialog.value = false;
  problemIdx++;
  problem.problemId.value = problemSet[problemIdx].problem_id;
  problem.question.value = problemSet[problemIdx].question;
  question.value = problem.question.value;

  // 초기화
  clearInterval(timerId);
  elapsedTime.value = overTime;

  clickTTSQustion();

}

const resultProcessing = async (text) =>{
  clearInterval(timerId);
  let _isRight = false;

  if(text != ""){
    const params = {
        question : question.value,
        answer : text
    };
    const {data} = await checkfluencyAnswer(params, token);
    if(data.dataStatus.code!=1){
        // todo api 응답 예외 처리
        return;
    }
    _isRight = data.response.result;
    problem.explain.value = data.response.explain;
    clickTTSAnswer();
  }else{
    _isRight = false;
    resultDialog.value = true;
  }
  isRight.value = _isRight;
  isExplain = true;

  
  saveSolvedProblem();
  

}

const handleSttTextChange = (text) => {
  // todo sttText 반영 안되는 오류
  sttText.value = text;
  resultProcessing(text);
};
const handleIsReadingChange = (value) => {
    if(isFirst && !value){
        isFirst = false;
        startTimer();
        isReading.value = value;
    }else if(isExplain && !value){
        resultDialog.value = true;
        isExplain = false;
    }
  isReading.value = value;
};


const handleDialogChange = (value) => {
  resultDialog.value = value;
  if(!value){
    isPause.value = false;
    isReturn.value=false;
    sttText.value = " ";
  }

};
const handleNextTickChange = (value) => {
  isFirst = true;
  problem.explain.value = '';
  sttText.value = ' ';

  nextProblem();

};
const handleReviewTickChange = (value) => {
  stopTimer();
  saveReviewProblem();
  reviewTick.value = value;
  
};
const handleAgainTickChange = (value) => {
  isFirst = true;
  problem.explain.value = '';
  elapsedTime.value = overTime;
  againTick.value = false;
  resultDialog.value = false;
  
  clickTTSQustion();
};
const handleIsCloseChange = (value) => {
  stopTimer();
  window.close();
};
const handleIsPauseChange = (value) => {
  
  stopTimer();
  resultDialog.value = true;
  isPause.value = value;
  isReturn.value = false;
};
const handleIsExitChange = (value) => {
  stopTimer();  
};

const handleIsReturnChange = (value) => {
  startTimer();  
  
  resultDialog.value = false;
  isPause.value = false;
  isReturn.value = false;

};

const clickTTSQustion = async () => {
  await nextTick(); 
  const ttsButton = document.querySelector('#question > div > button > #tts-button');
  if (ttsButton) {
    ttsButton.click();
  }
};
const clickTTSAnswer = async () => {
  await nextTick(); 
  const ttsButton = document.querySelector('#answer > div > button > #tts-button');
  if (ttsButton) {
    ttsButton.click();
  }
};

onBeforeUnmount(stopTimer);


getProblems();


const fontSize = ref(16);
const msg = computed(() => fontSize.value > 21 ? '원래대로' : '글자확대');
const enlarge = () => {
  fontSize.value ++;
  if (fontSize.value > 22) {
    fontSize.value = 16
  };
};


</script>

<template>
      
<div :style="{ fontSize: fontSize + 'px' }">

  <div class="background">


    <img src="@/assets/logo_green.png" alt="logo" class="navbar-logo" >

    <section class="sub-nav1">
        <div id="breadcrum">
          메인 홈&nbsp; &nbsp;>&nbsp;&nbsp; 언어재활코스 &nbsp; &nbsp;>&nbsp; &nbsp;유창성
        </div>
        <button class="enlarge" @click="enlarge" style="position: fixed; right: 0px; z-index: 10;">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
        </button> 
    </section>

    <div class="problem2" v-if="problem">
      <div >
        <ResultDialog 
          :dialog = "resultDialog"
          :isRight = "isRight"  
          :reviewTick = "reviewTick"
          :againTick = "againTick"
          :isPause = "isPause"
          :isReturn = "isReturn"  
          :isComplete="isComplete"
          :sttText="sttText"
          @update:dialog="handleDialogChange"
          @update:nextTick="handleNextTickChange"
          @update:reviewTick="handleReviewTickChange"
          @update:againTick="handleAgainTickChange"
          @update:isClose="handleIsCloseChange"
          @update:isPause="handleIsPauseChange"
          @update:isExit="handleIsExitChange"
          @update:isReturn="handleIsReturnChange"
          
          />
      </div>


      <div class="timer">
          <div class="timer-bar" :style="{ width: timerWidth + '%' }">
            <img src="@/assets/clock1.svg" class="clock">
          </div>

        </div>

        <div class="question">
          {{ question }}
        </div>
        <div class="process-number" style="position:absolute; top:115px;right:490px" v-if="problemSet">진행 현황&nbsp;: &nbsp; {{problemIdx+1}} /{{ problemSet.length }}</div>

        <img src="@/assets/triangle_left.svg" class="triangle_left">

        <!-- <div class="sttText">
          {{ sttText }}
        </div> -->

        <img src="@/assets/triangle.svg" class="triangle">
      
            <div :class="isReading ? 'stt-able' :  'stt-disable'">
                <STT_Fluency 
                :is-reading="isReading"
                :sttText="sttText"
                @update:sttText="handleSttTextChange"
                class="sttcomponent"
                />
            </div>

        <div id="question" >
        <TTS
            :text="question"
            :isReading="isReading"
            :showButton="true"
            @update:isReading="handleIsReadingChange"
            @tts-start="handleIsReadingChange(true)"
            @tts-end="handleIsReadingChange(false)"
            />
          <!-- <img src="@/assets/headphone.svg" id="tts-button" alt=""> -->
        </div>
        <div id="answer">
            <TTS
            :text="problem.explain.value"
            :isReading="isReading"
            :showButton="false"
            :showExplanationButton="problem.explain.value !== ''"
            @update:isReading="handleIsReadingChange"
            />
        </div>
        <div class="answerText">{{ problem.explain.value }}</div>
        <!-- <button @click="click">클릭</button> -->
    </div>


    <img class="cloud-character" src="@/assets/character.png" alt="">

  </div>

</div>
</template>


<style lang="scss" scoped>
@import '@/assets/scss/layout/_gamebackground.scss';

.stt-able{
    pointer-events: none;
    // background-color : rgba(128, 128, 128, 0.8);
}

.stt-disable{
    pointer-events: cursor;
    // background-color: '';
}

.cloud-character {
  position: fixed;
  top: 39%;
  left: 64%;
  width: 90px;
}

.question {
  position: fixed;
  top: 23%;
  left: 41%;
  border-radius: 10px;
  text-align: center;
  padding: 15px 17px;
  background-color: rgb(255, 255, 255);
  color: rgb(45, 45, 45);
  width: 27.7%;
  font-family: 'NotoSansKR';
  letter-spacing: 1px;
}

.triangle {
  position: fixed;
  top: 42%;
  left: 61.5%;
  width: 20px;
}

.triangle_left {
  position: fixed;
  top: 25%;
  left: 40%;
  width: 20px;
}

#answer {
  position: fixed;
  top: 83%;
  left: 22%;
}

.answerText {
  // display: flex;
  // justify-content: center;
  // align-items: center;
  position: absolute;
  top: 68%;
  left: 34%;
  width: 35%;
  text-align: center;
  font-family: 'NotoSansKR';
}

 .sttcomponent {
 position: fixed;
 top: 53%;
 left: 50%;
}


</style>