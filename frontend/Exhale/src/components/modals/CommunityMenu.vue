<template>
  <div class="nav scale-up-hor-right">
    <div class="navbar-logo-link">
        <RouterLink :to="{ name: 'MainPage' }">
          <img src="@/assets/logo_green.png" alt="logo" class="navbar-logo" >
        </RouterLink>
      </div>
    <img src="@/assets/Nav.png" alt="hamburgerMenu" class="hamburgerMenu">
    <img src="@/assets/close.png" alt="close-btn" class="close-btn" >
    <div class="background"></div>
  </div>

  <div class="link">
    <RouterLink class="movelink" :to="{name: 'MainPage'}">메인 홈</RouterLink>
    <hr style="border: 1.5px solid white; width: 85vw; margin-top: -25px; opacity: 90;">
    <RouterLink class="movelink" :to="{name: 'PostWholeListView'}">복습하기</RouterLink>
    <hr style="border: 1.5px solid white; width: 85vw; margin-top: -25px; opacity: 90;">
    <RouterLink class="movelink" :to="{name: 'ARCReport'}">내 정보</RouterLink>
    <hr style="border: 1.5px solid white; width: 85vw; margin-top: -25px; opacity: 90;">
    <RouterLink class="movelink" :to="{name: 'PostWholeListView'}">로그아웃</RouterLink>
    <hr style="border: 1.5px solid white; width: 85vw; margin-top: -25px; opacity: 90;">
  </div>


</template>

<script>
 
</script>

<style lang="scss" scoped>
/* .background {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(128, 128, 128, 0.1);
  z-index: 1;
  animation: scale-up-hor-right 2s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

.hamburgerMenu {
  z-index: 2;
  position: relative;
}

.nav {
  position: relative;
  width: 100vw;
  height: 100vh;
}


.scale-up-hor-right {
animation: scale-up-hor-right 2s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

@keyframes scale-up-hor-right {
0% {
  transform: scaleX(0);
  transform-origin: 100% 100%;
}
100% {
  transform: scaleX(5.2);
  transform-origin: 100% 100%;
}
} */
.link {
  z-index: 1002;
  animation: fadeIn 1s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
  position: fixed;
  top: 23vh;
  right: 5vw;
  flex-direction: column;
  display: flex;
}

.movelink {
  text-decoration: none;
  color: white;
  font-family: 'NotoSansKR';
  font-size: x-large;
  margin-top: 3vh;
  margin-bottom: 10vh;
  text-align: right;
}

.background {
  position: fixed;
  top: 0;
  right: 0;
  width: 100vw;
  height: 80vh;
  margin-top: 20vh;
  background-color: rgba(108, 159, 156, 0.8);
  z-index: 1000;
  animation: fadeIn 1s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

.hamburgerMenu {
  position: fixed;
  top: 59px;
  right: 180px;
  z-index: 1001;
  animation: scale-up-hor-right2 2s cubic-bezier(0.390, 0.575, 0.565, 1.000) both;
}

.navbar-logo {
  // position: fixed;
  top: 0;
  left: 0;
  margin-top: -1vh;
  z-index: 1001;
}

.close-btn {
  width: 80px;
  position: fixed;
  right: 65px;
  animation: jello-horizontal 0.9s both;
}
.close-btn:hover {
  animation: jello-horizontal2 0.9s both;
}

/* .rotate-center {
	animation: rotate-center 0.6s ease-in-out both;
} */

.nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 1000;
  background-color: rgba(255, 255, 255, 0.8);
  margin-bottom: 87vh;
  height: 20vh;
}

@keyframes rotate-center {
  0% {
    transform: rotate(0);
  }
  100% {
    transform: rotate(360deg);
  }
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

@keyframes jello-horizontal2 {
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


@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes scale-up-hor-right2 {
0% {
  transform: scaleX(0);
  transform-origin: 100% 100%;
}
100% {
  transform: scaleX(5.1);
  transform-origin: 100% 100%;
}
}

</style>