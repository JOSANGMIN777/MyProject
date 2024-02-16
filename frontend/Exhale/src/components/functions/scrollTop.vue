<template>
  <button @click="scrollToTop" class="scroll-to-top">
  <img src="@/assets/uparrow.png" class="arrow" alt="">
  </button>
</template>

<script setup>
function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: 'smooth' // 부드러운 스크롤 효과
  });
}

</script>

<style scoped>
.scroll-to-top {
  width: 75px;
  position: fixed;
  bottom: 30px;
  right: 5%;
  color: white; /* 아이콘 색상 */
  border: 2px solid rgb(108, 159, 156); /* 테두리 없앰 */
  padding: 20px 20px; /* 패딩 */
  border-radius: 50%; /* 모서리 둥글게 */
  cursor: pointer; /* 마우스 오버시 커서 변경 */
}

.arrow {
  width: 80%;
}
</style>