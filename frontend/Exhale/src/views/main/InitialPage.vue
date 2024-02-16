<template>
    <Headers />
    <!-- <div class="hello">
      우리 반갑게 인사해볼까요?
    </div> -->
    <!-- <div class="maintext">
      EXHALE
    </div> -->
    <img class="background" src="@/assets/background_circle.png" alt="">
    <transition name="fade">
    <div class="maintext jello-horizontal" v-if="showMainText">반가워요,<br> 당신의 아름다운 목소리를 들려주세요 :)</div>
  </transition>
  <transition name="fade">
    <img class="cloud-character jello-horizontal" src="@/assets/character.png" alt="" v-if="showCloudCharacter"/>
  </transition>

    <STT_initialPage
      v-model="sttText" 
      @update:sttText="handleSttTextChange" 
      @update:sttRunning="handleSttRunningChange" 
      @update:volume="handleVolumeUpdate" 
      class="sttcomponent1"
    />
    <SoundWave_InitialPage :volume="volume" class="soundwave_initial" />

    <footer>
    <Footers/>
  </footer>
</template>

<script setup> 
import { ref, onMounted, onUnmounted, watch } from "vue";
import { useRouter } from "vue-router";
import Headers from "@/components/common/Headers.vue";
import Footers from '@/components/common/Footers.vue';
import STT_initialPage from '@/components/ARC/STT_initialPage.vue';
import SoundWave_InitialPage from '@/components/ARC/SoundWave_InitialPage.vue';

// const authStore = useAuthStore()
// const state = reactive({
//   loggedIn: authStore.isLogIn,
// })

const volume = ref(0);

const sttText = ref("");
const sttRunning = ref(false);

const handleVolumeUpdate = (newVolume) => {
  volume.value = newVolume; // STT.vue로부터 전달받은 볼륨 데이터 업데이트
};


const showMainText = ref(true);
const showCloudCharacter = ref(false);

onMounted(() => {
  const intervalId = setInterval(() => {
    showMainText.value = !showMainText.value;
    showCloudCharacter.value = !showCloudCharacter.value;
  }, 2000);

  onUnmounted(() => {
    clearInterval(intervalId);
  });
});

</script>

<style lang="scss" scoped>

.hello {
  font-size: 25px;
  position: fixed;
  left: 41.5%;
  top: 23%;
  font-family: 'NotoSansKR';
  font-weight: 900;
  color: rgb(93, 93, 93);
}
.maintext {
position: fixed;
top: 23%;
left: 37%;
text-align: center;
font-family: 'NotoSansKR-Bold';
font-size: 25px;
font-weight: 900;
// color: #334F4E;
// color: rgb(108, 159, 156);
color: gray;
// opacity: 50%;
}

body {
    text-align: center;
    margin-top: 200px;
}

.navbar-links {
  margin-left: 45vw;
  display: flex;
  align-items: center;
  border-bottom: none;
  margin-right: -30vw;
  width: 150%;
}

nav {
  margin-bottom: -2vh;
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
  margin-top: -22vh;
}


.nav-link:hover {
  border-bottom: 2px solid rgb(108, 159, 156);
  text-decoration: none;
  color: black;
  transition: 0.4s;
  padding-bottom: 10px;
}

.background {
  position: fixed;
  top: 36%;
  right: -4.5%;
  opacity: 50%;
  // box-shadow: 1px -30px 100px 15px rgb(108, 159, 156);
  border-radius: 100%;
  z-index: -50;
  // &:hover {
  //   animation: jello-horizontal 0.9s both;
  // }
}

.soundwave_initial {
  width: 100%;
  // background-color: black;
  position: fixed;
  top: 65%;
  left: 36%;
  z-index: 200;
}

.cloud-character {
  width: 9%;
  position: absolute;
  left: 46%;
  top: 21.4%;
  // transform: rotate(-14deg);
  opacity: 80%;
  &:hover {
    animation: jello-horizontal 0.9s both;
  }
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
  z-index: -100;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

@keyframes jello-horizontal {
  0% {
    transform: scale3d(1, 1, 1);
  }
  30% {
    transform: scale3d(1.25, 0.75, 1);
  }
  40% {
    transform: scale3d(0.75, 1.25, 1);
  }
  50% {
    transform: scale3d(1.15, 0.85, 1);
  }
  65% {
    transform: scale3d(0.95, 1.05, 1);
  }
  75% {
    transform: scale3d(1.05, 0.95, 1);
  }
  100% {
    transform: scale3d(1, 1, 1);
  }
}

</style>