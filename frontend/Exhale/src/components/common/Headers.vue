<template>
<div class="navbar">
    <nav v-if="isLogin">
          <RouterLink  to="mainpage">
            <img src="@/assets/logo_green.png" alt="logo" class="navbar-logo" >
          </RouterLink>
          <nav class="navbar-links1">
            <RouterLink :to="{name: ' ReviewWaitingPage'}" class="nav-link1">복습하기</RouterLink>
            <RouterLink :to="{name: 'PostWholeListView'}" class="nav-link1">커뮤니티</RouterLink>
            <RouterLink :to="{name: 'ARCReport'}" class="nav-link1">내 정보</RouterLink> 
            <div class="nav-link1" @click="clickLogout">로그아웃</div>
          </nav>
    </nav>
    <nav v-else>
          <RouterLink to="/">
            <img src="@/assets/logo_green.png" alt="logo" class="navbar-logo" >
          </RouterLink>
          <nav class="navbar-links">
          <RouterLink  :to="{ name: 'AboutUs' }"  class="nav-link">날숨은 무엇인가요?</RouterLink>
          <RouterLink  :to="{ name: 'Login' }" class="nav-link">로그인</RouterLink>
          <RouterLink  :to="{ name: 'SignUp' }" class="nav-link">회원가입</RouterLink>
          </nav>
    </nav>
  </div> 

 
</template>

<script setup>
import { reactive, computed, watch, ref } from 'vue'
import { logout } from '@/api/outhApi';
import { storeToRefs } from "pinia";
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';

const authStore = useAuthStore();
// const state = reactive({
//   loggedIn: authStore.isLogIn,
// })

const { refreshToken, key, isLogin } = storeToRefs(authStore);

watch(() => isLogin, (token) => {

})

const router = useRouter();


const clickLogout = async() => {
  await logout(key.value,refreshToken.value);    
  authStore.removeUserInfo();
  alert('로그아웃 되셨습니다. 또 만나요!')
  router.push( "/")
}
</script>


<style scoped>
.navbar-links {
  position: fixed;
  top: 2%;
  right: -0.4%;
  border-bottom: none;
  width: 30%;
}

.navbar-links1 {
  position: fixed;
  top: 10%;
  right: 0.3%;
  border-bottom: none;
  width: 50%;
}

nav {
  margin-bottom: 3vh;
}

.navbar {
  display: flex;
  align-items: center;
  /* justify-content: space-around; */
  margin-left: 7vw;
  margin-top: 2vh;
}

.nav-link {
  text-decoration: none;
  color: #333;
  border-bottom: 2px solid transparent;
  transition: border-color 0.3s;
  display: inline-block;
  /* margin-top: -26vh; */
  /* height: -100px; */
}

.nav-link1 {
  text-decoration: none;
  color: #333;
  border-bottom: 2px solid transparent;
  transition: border-color 0.3s;
  display: inline-block;
  margin-right: 15%;
  /* margin-top: -26vh; */
  /* height: -100px; */
}


.nav-link:hover {
  border-bottom: 3px solid rgb(108, 159, 156);
  text-decoration: none;
  color: black;
  transition: 0.4s;
  padding-bottom: 10px;
}

.nav-link1:hover {
  border-bottom: 3px solid rgb(108, 159, 156);
  text-decoration: none;
  color: black;
  transition: 0.4s;
  padding-bottom: 10px;
}

</style>