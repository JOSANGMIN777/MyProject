<script setup>
import { ref, onMounted, onBeforeUnmount, computed, nextTick  } from 'vue';
import { storeToRefs } from "pinia";
import { useAuthStore } from "@/stores/auth";
import { getProblem, postSolvedProblem, postReview  } from '@/api/course.js';
import TTS_MatchingImage from '@/components/ARC/TTS_MatchingImage.vue';
import SoundWave_MatchingImage from '@/components/ARC/SoundWave_MatchingImage.vue';
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

const isReading = ref(false);
let isFirst = true;

const problem = {
  problemId : ref(0),
  answer: ref(''),
  question: ref(''),
  options : ref([]),
  questionImage : ref('')
};

// 타이머
const elapsedTime = ref(overTime);
let timerId;
const timerWidth = computed(() => (elapsedTime.value / overTime) * 100);

// 힌트 토글 함수
const showHint = ref(false);
const toggleHint = () => {
  showHint.value = !showHint.value;
};

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
    problem.answer.value = problemSet[problemIdx].answer;
    problem.question.value = problemSet[problemIdx].question;
    problem.options.value = problemSet[problemIdx].options;
    problem.questionImage.value = problemSet[problemIdx].question_image;
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
  } catch (error) {
    if(error.response.data.dataStatus==4){
      console.log("이미 저장된 문제입니다.");
    }
    console.error(error);

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
    problem.answer.value = problemSet[problemIdx].answer;
    problem.question.value = problemSet[problemIdx].question;
    problem.options.value = problemSet[problemIdx].options;
    problem.questionImage.value = problemSet[problemIdx].question_image;
     // 초기화
    clearInterval(timerId);
    elapsedTime.value = overTime;
}

const resultProcessing = (text) =>{
  clearInterval(timerId);
  let _isRight = false;
  if(problem.answer.value == text){  // 정답
    _isRight = true;
  }else{  // 오답
    _isRight = false;
  }
  isRight.value = _isRight;
  resultDialog.value = true;

  
  saveSolvedProblem();

}

const handleIsReadingChange = (value) => {
    if(isFirst && !value){
        isFirst = false;
        startTimer();
    }
  isReading.value = value;
};

const handleDialogChange = (value) => {
  resultDialog.value = value;
  if(!value){
    isPause.value = false;
    isReturn.value=false;
  }

};
const handleNextTickChange = (value) => {
  isFirst = true;
  nextProblem();
  clickTTSQustion();

};
const handleReviewTickChange = (value) => {
  stopTimer();
  saveReviewProblem();
  reviewTick.value = value;
  
};
const handleAgainTickChange = (value) => {
  isFirst = true;
  elapsedTime.value = overTime;
  againTick.value = false;
  resultDialog.value = false;
  clickTTSQustion();
};
const handleIsCloseChange = (value) => {
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
  const ttsButton = document.querySelector('div > #tts-button');
  if (ttsButton) {
    ttsButton.click();
  }
};

const selectOption = (idx) => {
    resultProcessing(idx+1);
}

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
        메인 홈&nbsp; &nbsp;>&nbsp;&nbsp; 언어재활코스 &nbsp; &nbsp;>&nbsp; &nbsp;듣고 이해하기
        <p class="breadcrum2">(텍스트 고르기)</p>
      </div>
      <button class="enlarge" @click="enlarge" style="position: fixed; right: 0px; z-index: 10;">
      <img src="@/assets/plus.svg" class="plus">
      {{ msg }}
      </button> 
  </section>


  

  <div class="problem" v-if="problem">
    <div >
      <ResultDialog 
        :dialog = "resultDialog"
        :isRight = "isRight"  
        :reviewTick = "reviewTick"
        :againTick = "againTick"
        :isPause = "isPause"
        :isReturn = "isReturn"  
        :isComplete="isComplete"
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
      <div class="timer2">
        <div class="timer-bar" :style="{ width: timerWidth + '%' }">
          <img src="@/assets/clock1.svg" class="clock">
        </div>
      </div>

      <div class="content">
        <div>
            <TTS_MatchingImage 
                :text="problem.question.value"
                :isReading="isReading"
                @tts-start="isReading = true"
                @tts-end="isReading = false"
                @update:isReading="handleIsReadingChange"
                />
                <p class="click">Click!</p>
                <SoundWave_MatchingImage :isActive="isReading" class="soundwave_matchingimage" />
                <div class="process-number1" style="margin-top: 37px;" v-if="problemSet">진행 현황&nbsp;: &nbsp; {{problemIdx+1}} /{{ problemSet.length }}</div>

                <img src="@/assets/triangle_left.svg" class="triangle_left">
                <div class="options-grid">
                  <img :src="problem.questionImage.value" class="option-image"/>
                  <div v-for="(option, idx) of problem.options.value" :key="idx"> 
                    <label class="option" @click="selectOption(idx)">
                      &nbsp;{{ idx+1 }}. &nbsp;&nbsp;&nbsp;&nbsp; {{ option}}
                      </label>
                  </div>
                </div>
        </div>
        </div>
            <button class="hintBtn2" @click="toggleHint" v-if="!showHint">힌트</button>
            <div class="hint2" v-if="showHint">{{ problem.question.value }}</div>
      </div>

  </div>


</div>

</template>


<style lang="scss" scoped>
@import '@/assets/scss/layout/gamebackground.scss';
.timer2 {
  position: fixed;
  top: 8.5%;
  left: 33.2%;
  width: 35%;
  height: 40px;
  background-color: #ffffff;
  border-radius: 30px;
  overflow: hidden;
}

.soundwave_matchingimage {
  position: fixed;
  top: 22%;
  left: 53.8%;
  transform: translate(-50%, -50%);
  border-radius: 10px;
  padding: 15px 17px;
  // background-color: rgb(255, 255, 255);
  width: 25%;
  height: 60px;
}

.triangle_left {
  position: fixed;
  top: 20.5%;
  left: 40.5%;
  width: 20px;
}

.breadcrum2 {
  margin-left: 64.8%;
}

.options-grid {
  // display: grid;
  // grid-gap: 20px 20px; 
  justify-content: center; 
  align-items: center;
  margin: 20px auto;
  margin-left: 0px;
  width: 500px;
  height: 180px;
  object-fit: cover;
}

.option-image {
  object-fit: cover;
  border-radius: 10px;
  width: 100%;
  height: 100%;
  margin-bottom: 20px;
}

.option {
  cursor: pointer;
  background-color: white;
  width: 100%;
  margin: 7px 0px;
  padding: 10px;
  padding-left: 30px;
  border-radius: 10px;
  font-size: 80%;
  font-family: 'NotoSansKR';
}

.option:hover {
  cursor: pointer;
  opacity: 50%;
}


.hintBtn2 {
  font-size: 86%;
  position: fixed;
  top: 88%;
  left: 48%;
  color: white;
  border: 3px solid white;
  border-radius: 25px;
  padding-top: 4px;
  padding-bottom: 8px;
  padding-left: 15px;
  padding-right: 15px;
  text-shadow: 5px 2px 7px rgb(171, 171, 171);
  box-shadow: 1px 2px 5px rgb(211, 211, 211);
  &:hover {
    opacity: 50%;
}
}
.hint2 {
  position: fixed;
  text-align: center;
  width: 25%;
  top: 88%;
  left: 38%;
  color: rgb(121, 121, 121);  
  font-family: 'NotoSansKR';
  letter-spacing: 1px;
}

.click {
  position: fixed;
  top: 27%;
  left: 35.6%;
  color: rgb(108, 159, 156);
  font-size: 70%;
}

.process-number1 {
  display: flex;
  justify-content: flex-end;
  color: rgb(108, 159, 156);
  font-weight: 500;
  font-size: 16px;
  margin: 5px 5px;
  font-family: 'NotoSansKR';  
}

</style>