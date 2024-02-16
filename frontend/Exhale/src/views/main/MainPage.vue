<template>
  <div :style="{ fontSize: fontSize + 'px' }">
    <Headers />
    <div class="sub-nav1">
        <div id="breadcrum">
          <RouterLink class="breadlink" :to="{name: 'MainPage'}">메인 홈</RouterLink>
          >
          <RouterLink class="breadlink" :to="{name: 'MainPage'}">언어재활코스</RouterLink>
        </div>
        <button class="enlarge" @click="enlarge">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
        </button> 
      </div>
    
    <p class="title">언어재활코스</p>
    
    <div class="mainbox">
      <div v-for="course of courses" @click="toggleSubbox(course.course_id)" 
      :class="{ 
        'hide': activeBox !== null && activeBox !== course.course_id, 
        'show': activeBox === course.course_id,
        'array1': activeBox === courses[0].course_id,
        'array2': activeBox === courses[1].course_id,
        'array3': activeBox === courses[2].course_id,
        'array4': activeBox === courses[3].course_id
      }" class="box" :key="course.course_id">
        {{course.course_name}}
      </div>
    
      <div v-if="categories" class="subbox" :class="{ 'show': subboxStates.box1, 'hide': !subboxStates.box1 }">
        <div v-for="category of categories" :key="category.category_id">
          <div class="b" style="font-size: 90%;" @click="navigateToReview(category)" >
              {{category.category_name}}
          </div>

        </div>
      </div>
    </div>

</div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch, computed } from "vue";
import { useRouter } from "vue-router";
import Headers from "@/components/common/Headers.vue";
import { RouterView } from "vue-router";
import router from "@/router";
import { getCourseList, getCategoryList } from '@/api/course.js';
import SayObject from "../ARC/SayObject.vue";

const fontSize = ref(16);
const msg = computed(() => fontSize.value > 21 ? '원래대로' : '글자확대');
const enlarge = () => {
  fontSize.value ++;
  if (fontSize.value > 22) {
    fontSize.value = 16
  };
};


// 클릭하면 subbox 나옴
const showSubbox = ref(false);
const animationDirection = ref('');

const subboxStates = ref({
  box1: false,
  box2: false,
  box3: false,
  box4: false,
})
const courses = ref([]);
const categories = ref([]);
let currentCourseId = 0;

const appURL = import.meta.env.VITE_APP_API_URL;

const activeBox = ref(null);

// 화면의 다른 부분을 클릭했을 때 실행될 함수
const onBodyClick = (event) => {
  // 클릭된 요소가 box 클래스 영역 외부인지 확인
  if (!event.target.closest('.box')) {
    // activeBox를 null로 설정하여 애니메이션을 원상태로 복원
    activeBox.value = null;
    // 모든 subbox 상태를 초기화
    subboxStates.value.box1 = false;
    subboxStates.value.box2 = false;
    subboxStates.value.box3 = false;
    subboxStates.value.box4 = false;
  }
};

const toggleSubbox = async (courseId) => {
  if (currentCourseId === courseId) {
    activeBox.value = null;
    currentCourseId = 0;

    subboxStates.value.box1 = false;
    subboxStates.value.box2 = false;
    subboxStates.value.box3 = false;
    subboxStates.value.box4 = false;

    
    categories.value = [];
    return;
  }
  currentCourseId = courseId;
  activeBox.value = courseId;
  await categoryList(courseId);
  subboxStates.value.box1 = !subboxStates.value.box1;
  // subboxStates.value[boxKey] = !subboxStates.value[boxKey]
  // Object.keys(subboxStates.value).forEach(key => {
  //   if (key !== boxKey) subboxStates.value[key] = false
  // })
}

// 위 name에 각 게임 페이지 링크 이름 넣어야 함
// 해당 게임 유형으로 이동
const navigateToReview = (category) => {
  let target = '';
  switch(currentCourseId){
    case 1:
      target = 'say-object';
      break;
    case 2:
      target = 'follow-up-speech';
      break;
    case 3:
      if(category.category_id==6)target = 'matching-image';
      else if(category.category_id==7)target = 'matching-text';
      break;
    case 4:
      target = 'fluency';
      break;
  }
  window.open(`${appURL}/${target}/${category.category_id}/${category.over_time_sec}`);
  
}
// const navigateToReview2 = (category) => {
//   router.push({ name: ' FollowUpSpeech', params: { category } })
// }
// const navigateToReview3 = (category) => {
//   router.push({ name: 'ListeningComprehension', params: { category } })
// }
// const navigateToReview4 = () => {
//   router.push({ name: ' Fluency' })
// }
const courseList = async () => {
  try {
    const { data } = await getCourseList();
    
    if (data.response) {
      array1.value = data.response[0].course_id
      array2.value = data.response[1].course_id
      array3.value = data.response[2].course_id
      array4.value = data.response[3].course_id
      console.log('어레이1',array1.value)
    }
    else if(data.dataStatus.code!=1){
      // todo api 응답 예외 처리
      return;
    }
    courses.value = data.response;
    courses.value.forEach((data) => {
      console.log(data.course_name)
    })

  } catch (error) {
    console.error(error); 
  }
}
const array1 = ref([])
const array2 = ref([])
const array3 = ref([])
const array4 = ref([])
const categoryList = async (courseId) => {
  try {
    const { data } = await getCategoryList(courseId);
    if(data.dataStatus.code!=1){
      // todo api 응답 예외 처리
      return;
    }
    categories.value = data.response;
    console.log(categories.value)
  } catch (error) {
    console.error(error); 
  }
}

onMounted(()=> {
  courseList()
})

onUnmounted(() => {
  document.removeEventListener('click', onBodyClick);
});

</script>

<style lang="scss" scoped>
@import "@/assets/scss/pages/_mainpage.scss";

</style>