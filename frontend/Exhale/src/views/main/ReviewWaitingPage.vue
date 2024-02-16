<template>
  <div :style="{ fontSize: fontSize + 'px' }">
    <Headers />
    <div class="sub-nav1">
        <div id="breadcrum">
          <RouterLink class="breadlink" :to="{name: 'MainPage'}">메인 홈</RouterLink>
          >
          <RouterLink class="breadlink" :to="{name: 'MainPage'}">복습</RouterLink>
        </div>
        <button class="enlarge" @click="enlarge">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
        </button> 
      </div>
    
    <p class="title">복습</p>

    <div class="mainbox">
      <div v-for="course of courses" @click="toggleSubbox(course.course_id)" class="box" :key="course.course_id">
        {{course.course_name}}
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

const toggleSubbox = async (courseId) => {
  let target = '';
  switch(courseId){
    case 1:
      target = 'review-say-object';
      break;
    case 2:
      target = 'review-follow-up-speech';
      break;
    case 3:
      target = 'review-listening-comprehension';
      break;
  }
  window.open(`${appURL}/${target}`);

}


const courseList = async () => {
  try {
    const { data } = await getCourseList();
    if(data.dataStatus.code!=1){
      // todo api 응답 예외 처리
      return;
    }
  
    for(let course of data.response){
      if(course.course_id!=4) courses.value.push(course); 
    }
    console.log(courses.value)

  } catch (error) {
    console.error(error); 
  }
}

courseList();



</script>

<style lang="scss" scoped>
@import "@/assets/scss/pages/_mainpage.scss";

</style>