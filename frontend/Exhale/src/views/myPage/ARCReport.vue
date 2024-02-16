<template>
  <div :style="{ fontSize: fontSize + 'px' }">

  <div id="page">
    <MyPageHeader />

    <section class="sub-nav1">
      <div id="breadcrum" style="color: white">
        <RouterLink
          class="breadlink"
          style="color: white"
          :to="{ name: 'MainPage' }"
          >메인 홈</RouterLink
        >
        >
        <RouterLink
          class="breadlink"
          style="color: white"
          :to="{ name: 'ARCReport' }"
          >마이페이지</RouterLink
        >
      </div>
     <button class="enlarge" @click="enlarge">
        <img src="@/assets/plus_white.png" class="plus">
        {{ msg }}
        </button> 
    </section>

    <div id="sidebar">
      <div id="profile">
        <div id="profile-image">
          <img id="profile-img-tag" v-bind:src="image" alt="프로필 사진" />
        </div>
        <p id="nickname-p-tag">{{ nickname }} 님의 <br />재활리포트</p>
      </div>
    </div>
    <div id="main">
      <div id="solved">
        <p class="data-title">문제 풀이 통계</p>
        <div id="problem-category-list">
          <button class="round-button" 
          :class="{ 'selected': selectedButtonId === 1 }"
          @click="selectButton(1)">
            이름 대기
          </button>
          <button class="round-button" 
          :class="{ 'selected': selectedButtonId === 2 }"
          @click="selectButton(2)">
            따라 말하기
          </button>
          <button class="round-button"
          :class="{ 'selected': selectedButtonId === 3 }"
          @click="selectButton(3)">
            듣기 이해력
          </button>
          <button class="round-button" 
          :class="{ 'selected': selectedButtonId === 4 }"
          @click="selectButton(4)">
          유창성
        </button>
        </div>
        <div id="problem-data-list">
          <div class="problem-data" v-for="item in filteredProblemDataList" :key="item.id">
            <!-- {{ item.course_id }} -->
            <p class="date">날짜 : {{ item.start_of_week }} ~ {{ item.end_of_week }}</p>
            <p class="problemdetile">
              푼 문제 : <span>{{ item.correct_count + item.wrong_count }}</span
              >, 맞춘 문제 : <span>{{ item.correct_count }}</span> , 틀린 문제 :
              <span>{{ item.wrong_count }}</span>
            </p>
          </div>
        </div>
      </div>
      <div id="letter">
        <p class="data-title">많이 틀린 다섯가지</p>
        <div id="letter-data-list">
          <div class="letter-data" v-for="(item, index) in letterDataList" :key="index">
            <div class="bar-container">
              <div class="bar-background"></div>
              <div class="bar" :style="{height: item.wrong_count / item.count * 100 + '%'}"></div>
            </div>
              <div class="label">
                <p>
                  {{ 
                    item.type === 'CONSONANT_LETTER' ? '초성' :
                    item.type === 'MIDDLE_VOWEL_LETTER' ? '중성' :
                    item.type === 'LAST_CONSONANT_LETTER' ? '종성' :
                    item.type
                  }}
                </p>
                <p class="sublabel">
                  {{ item.letter }}
                </p>
              </div>
          </div>
        </div>


      </div>
    </div>


    <Footers_mypage class="footer1" />

  </div>

</div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import MyPageHeader from "@/components/common/MyPageHeader.vue";
import Footers_mypage from "@/components/common/Footers_mypage.vue";

import * as mypage from "@/api/mypage";
import * as report from "@/api/report";
import { storeToRefs } from "pinia";
import { useAuthStore } from "@/stores/auth";

const authStore = useAuthStore();
const { jwtToken } = storeToRefs(authStore);
const token = jwtToken.value;

const nickname = ref("");
const image = ref("");
const letterDataList = ref([]);
const solvedDataList = ref([]);



const result1 = ref('')
const result2 = ref('')
const result3= ref('')
const result4= ref('')
const result5= ref('')


const selectedButtonId = ref(1);

const filteredProblemDataList = computed(() => {
  return solvedDataList.value.filter(item => item.course_id === selectedButtonId.value);
});

const selectButton = (id) => {
  selectedButtonId.value = id;
};




const select_course_id = ref(4);
const select_button = (course_id) => {
  select_course_id.value = course_id;
};

const select_problem_data_list = computed(() => {
  var alist = solvedDataList.value.filter(
    (item) => item.course_id === select_course_id.value
  );
  console.log(alist);
  return alist;
});

//API 호출
const getProfile = async () => {
  const response = await mypage.getProfile(token);
  nickname.value = response.data.response.nickname;
  image.value = response.data.response.image_url;
};

const getSolvedData = async () => {
  const response = await report.getSolvedData(token);
  solvedDataList.value = response.data.response;
  
};

const getLetterData = async () => {
  const response = await report.getLetterData(token);
  letterDataList.value = response.data.response;
  result1.value =(letterDataList.value[0].wrong_count / letterDataList.value[0].count *100)
  result2.value =(letterDataList.value[1].wrong_count / letterDataList.value[1].count *100)
  result3.value =(letterDataList.value[2].wrong_count / letterDataList.value[2].count *100)
  result4.value =(letterDataList.value[3].wrong_count / letterDataList.value[3].count *100)
  result5.value =(letterDataList.value[4].wrong_count / letterDataList.value[4].count *100)

};

onMounted(() => {
  getProfile();
  getLetterData();
  getSolvedData();
});

const fontSize = ref(16);
const msg = computed(() => fontSize.value > 21 ? '원래대로' : '글자확대');
const enlarge = () => {
  fontSize.value ++;
  if (fontSize.value > 22) {
    fontSize.value = 16
  };
};





</script>

<style lang="scss" scoped>
@import "@/assets/scss/pages/_mainpage.scss";



.sub-nav1 {
  margin-top: -2vh;
  margin-left: 2.7vw;
}

#problem-data-list {
  padding-left: 1vw;
}

#letter-data-list {
  display: flex;
  justify-content: center;
  flex-direction: row; 
  align-items: flex-end; 
  gap: 30px; 
  padding: 20px; 
  background-color: #f1f6f5; 
  border-radius: 10px; 
  width: 95%;
  height: 75%; 
  margin: auto; 
}

.bar-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  height: 180px;
  width: 100%;
  position: relative;
  background-color: rgb(226, 226, 226); 
  border-radius: 20px;
}

.bar-background {
  position: absolute;
  width: 80%; 
  height: 80%;
  background-color: rgb(226, 226, 226); 
  bottom: 0;
  border-radius: 20px;
}

.bar {
  width: 100%; 
  background-color: #334F4E; 
  border-bottom-right-radius: 5px; 
  border-bottom-left-radius: 5px; 
  position: absolute; 
  bottom: 0; 
}

.label {
  text-align: center; 
  margin-top: 50%; 
  font-family: 'NotoSansKR-Bold';
}

.sublabel {
  margin-top: -30%;
}


.footer1 {
  position: fixed;
  left: -8%;
  top: 125%;
  margin-top: -38vh;
  font-size: 80%;
  opacity: 70%;
}

.enlarge {
  color: white;
  border: 3px solid white;
  margin-right: 11vw;
}

.date {
  margin-top: 23px;
}


.problem-data {
  margin-left: 15px;
  margin-top: 5vh;
}

#page {
  height: 100vh;
  background-color: #6c9f9c;
  overflow: hidden;
  margin: 0;
}

//sidebar 관련
#sidebar {
  flex: 1;
  min-height: 80%;
  width: 30%;
  height: 80%;
  padding: 0% 1%;
  //background-color: rgb(169, 170, 170);
  position: absolute;
  left: 0;
  margin-bottom: -5vh;
  max-height: 100%;
  overflow: auto;
}

#profile-image {
  display: flex;
  justify-content: center;
  margin-top: 60px;
  margin-left: 50px;
  border-radius: 100%;
}

#profile-img-tag {
  width: 150px;
  height: 150px;
  border: 20px solid rgb(227, 227, 227);
  border-radius: 50%;  
}

#nickname-p-tag {
  margin: 30px 0px;
  font-size: 20px;
  text-align: center;
  color: white;
  margin-left: 50px;
}

//main 관련
#main {
  min-height: 100%;
  width: 80%;
  height: 80%;
  //background-color: aquamarine;
  margin-left: 30%;
  display: flex;
  gap: 5%;
  margin-top: -3vh;
  align-items: center;
  margin-bottom: -5vh;
  max-height: 100%;
  overflow: auto;
} 

#solved {
  width: 40%;
  height: 55%;
  margin-bottom: 30vh;
  background-color: #F1F6F5;
  color: #334F4E;
  border: none;
  border-radius: 30px;
  padding: 2%;
  
}


#letter {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 30%;
  height: 55%;
  padding-top: 3vh;
  margin-bottom: 30vh;
  background-color: #F1F6F5;
  border: none;
  border-radius: 30px;
}

.data-title {
  margin-top: 20px;
  margin-bottom: 35px;
  font-weight: 700;
  text-align: center;
  font-size: 140%;
  color: #334F4E;
}

//solved-data 관련
#problem-category-list {
  display: flex;

}

.round-button {
  border: none;
  background-color: transparent;
  border-radius: 15px;
  background-color: lightgray;
  color: #334F4E;
  font-weight: 600;
  font-size: 13px;
  padding: 10px 16.5px;
  margin: 0px 7px;
  &:hover {
    color: white;
    background-color: #334F4E;
  }
}

.selected {
  color: white;
  background-color: #334F4E !important;
}

//letter-data 관련
#pie-chart {
  display: inline-block;
  position: absolute;
  width: 200px;
  height: 200px;
  background: conic-gradient(#334F4E 0% 25%, #6C9F9C 25% 56%, #A6D4D1 56% 100%);
  border-radius: 50%;
}




</style>
