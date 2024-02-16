<template>
  <footer class="footer">
    <div class="footer-link">
      <a href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp" class="footer-item">FAQ</a> |
      <a href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp" class="footer-item">관련 링크</a> |
      <a href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp" class="footer-item">이용약관</a> |
      <a href="https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp" class="footer-item">개인정보 처리방침</a> |
      <a href="http://yuseong.samsungfire.com/servlet/controller.campus.out.CampusOutMainServlet?X-SFMI-INTLOG=N52012-001082^^Y^^^^^^^^^^^^^^^^^" class="footer-item">오시는 길</a>
    </div>
    <div class="footer-contact">
      <span>Telephone: 02-3429-5100 | 평일 상담시간 09:00 ~ 18:00 | ssafy@ssafy.com</span>
    </div>
  </footer>
</template>

<script setup>

</script>

<style lang="scss" scoped>
footer {
  display: flex;
  flex-direction: column;
  color: rgb(255, 255, 255);
  background-color: rgb(108, 159, 156);
  width: 120%;
  font-size: 90%;
  // padding: 20px 0;
  box-sizing: border-box;
  margin-top: auto;
  // margin-bottom: 10vh;
  & a {
    text-decoration: none;
    color: rgb(255, 255, 255);
    font-family: 'NotoSansKR';
  }
  & span {
    color: rgb(255, 255, 255);
    font-family: 'NotoSansKR';
  }
  & .footer-contact {
    margin-top: 1vh;
    margin-bottom: 10vh;
  }
}

</style>