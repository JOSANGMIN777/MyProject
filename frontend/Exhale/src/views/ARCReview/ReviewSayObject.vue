<script setup>
import { ref, onMounted, onBeforeUnmount, computed  } from 'vue';
import { storeToRefs } from "pinia";
import { useAuthStore } from "@/stores/auth";
import { getReviewProblem, postSolvedProblem, deleteReview  } from '@/api/course.js';
import STT from '@/components/ARC/STT.vue';
import ReviewResultDialog from '@/components/ARCReview/ReviewResultDialog.vue'
import SoundWave from '@/components/ARC/SoundWave.vue';


const overTime = import.meta.env.VITE_APP_REVIEW_OVER_TIME;
let courseId = 1; 
const volume = ref(0);

const authStore = useAuthStore();
const { jwtToken } = storeToRefs(authStore);
const token = jwtToken.value;

let problemIdx=0;
let problemSet=null;
let no = ref(1);

// 다이어로그
const resultDialog = ref(false);
const isRight = ref(false);
const againTick = ref(false);
const reviewTick = ref(false);
const isPause = ref(false);
const isReturn = ref(false);
const isComplete = ref(false);

const sttText = ref("");
const sttRunning = ref(false);

const problem = {
  problemId : ref(0),
  answer: ref(''),
  hint: ref(''),
  imgUrl : ref(''),
  categoryId: ref(0),
  categoryName: ref('')
};


const handleVolumeUpdate = (newVolume) => {
  volume.value = newVolume; // STT.vue로부터 전달받은 볼륨 데이터 업데이트
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
    const { data } = await getReviewProblem(courseId, token);
    if(data.dataStatus.code!=1){
      // todo api 응답 예외 처리
      return;
    }

    problemSet = data.response;
    if(problemSet.length === 0){
      alert("복습할 문제가 없습니다!")
      window.close()
    }

    problem.problemId.value = problemSet[problemIdx].problem_id;
    problem.answer.value = problemSet[problemIdx].answer;
    problem.hint.value = problemSet[problemIdx].hint;
    problem.imgUrl.value = problemSet[problemIdx].img_url;
    problem.categoryId.value = problemSet[problemIdx].category_id;
    problem.categoryName.value = problemSet[problemIdx].category_name;
    

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

const deleteReviewProblem = async () => {
  try {
    const { data } = await deleteReview(problem.problemId.value, token);
    if(data.dataStatus.code==2){
      // todo api 응답 예외 처리
      return;
    }else if(data.dataStatus.code!=1){
    }
    alert("문제를 삭제 했습니다");
    resultDialog.value = false;
  } catch (error) {
    if(error.response.data.dataStatus.code==4){
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
  problem.hint.value = problemSet[problemIdx].hint;
  problem.imgUrl.value = problemSet[problemIdx].img_url;
  problem.categoryId.value = problemSet[problemIdx].category_id;
  problem.categoryName.value = problemSet[problemIdx].category_name;
    
  no.value++;

  // 초기화
  stopTimer();
  elapsedTime.value = overTime;
  startTimer();  
  toggleHint();
}

const resultProcessing = (text) =>{
  clearInterval(timerId);
  let _isRight = false;
  let answer = problem.answer.value.replace(/\s+/g, '').toLowerCase(); 
  if(text!="" &&text.replace(/\s+/g, '').toLowerCase() === answer){  // 정답
    _isRight = true;
  }else{  // 오답
    _isRight = false;
  }
  isRight.value = _isRight;
  resultDialog.value = true;

  
  saveSolvedProblem();

}

const handleSttTextChange = (text) => {
  // todo sttText 반영 안되는 오류
  sttText.value = text;
  resultProcessing(text);
};
const handleSttRunningChange = (value) => {
  sttRunning.value = value;
};
const handleDialogChange = (value) => {
  resultDialog.value = value;
  if(!value){
    isPause.value = false;
    isReturn.value=false;
    showHint.value = false;
  }

};
const handleNextTickChange = (value) => {
  nextProblem();
};
const handleReviewTickChange = (value) => {
  stopTimer();
  deleteReviewProblem();
  reviewTick.value = value;
  nextProblem();
  
};
const handleAgainTickChange = (value) => {
  stopTimer();
  elapsedTime.value = overTime;
  againTick.value = false;
  resultDialog.value = false;
  startTimer();  
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

onMounted(startTimer);
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
          메인 홈&nbsp; &nbsp;>&nbsp;&nbsp; 복습 &nbsp; &nbsp;>&nbsp; &nbsp;이름대기&nbsp; &nbsp;>&nbsp; &nbsp;{{ problem.categoryName.value }}
        </div>
        <button class="enlarge" @click="enlarge" style="position: fixed; right: 0px; z-index: 10;">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
        </button> 
    </section>


    

    <div class="problem" v-if="problem">
      <div >
        <ReviewResultDialog 
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

        <div class="content">
            <div class="problemtitle">
              <label class="numbering">
                {{ no }}.
              </label>
              &nbsp; &nbsp; 아래 이미지가 나타내는 적합한 단어를 말하세요. </div>
            <STT 
            v-model="sttText" 
            @update:sttText="handleSttTextChange" 
            @update:sttRunning="handleSttRunningChange" 
            @update:volume="handleVolumeUpdate" 
            class="sttcomponent1"
            />

              <div><img class="imgurl" :src="problem.imgUrl.value"/></div>
              <SoundWave :volume="volume" class="soundwave" />
              <button class="hintBtn" @click="toggleHint" >힌트</button>
              <div class="hint" v-if="showHint">{{ problem.hint.value }}</div>
        </div>

    </div>


  </div>

</div>
</template>


<style lang="scss" scoped>
@import '@/assets/scss/layout/gamebackground.scss';
</style>