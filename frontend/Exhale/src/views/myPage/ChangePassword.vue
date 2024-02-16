<template>
  <div class="parent-div">
    <header>
      <SimpleHdear />
      <div class="sub-nav1">
        <div id="breadcrum">
          <RouterLink class="breadlink" :to="{ name: 'MainPage' }"
            >메인 홈</RouterLink
          >
          >
          <RouterLink class="breadlink" :to="{ name: 'ARCReport' }"
            >내정보</RouterLink
          >
          >
          <!-- to를 자기 자신(ChangePassword)로  잡으면 에러남-->
          <RouterLink class="breadlink" :to="{ name: 'ARCReport' }"
            >회원정보수정</RouterLink
          >
        </div>
      </div>
    </header>

    <main>
      <h2>비밀번호 변경</h2>
      <p>비밀번호 인증 후 비밀번호 변경이 가능해요</p>
      <div class="input-form">
        <div class="old-password-textbox">
          <input
            class="old-password-input"
            type="password"
            v-model="oldPassword"
            placeholder="현재 비밀번호 입력"
            :disabled="isOldInputDisabled"
          />
          <button
            class="password-btn, btn"
            @click="checkPassword"
            :disabled="isOldInputDisabled"
          >
            비밀번호 인증
          </button>
        </div>
        <div class="new-password-textbox">
          <input
            type="password"
            v-model="newPassword"
            :disabled="isNewInputDisabled"
            placeholder="새 비밀번호 입력"
          />
          <input
            type="password"
            v-model="newPasswordCheck"
            :disabled="isNewInputDisabled"
            placeholder="새 비밀번호 재입력"
          />
          <p class="error">{{ showError }}</p>
          <button
            class="save-btn, btn"
            @click="changePassword"
            :disabled="isSaveButtonDisabled"
          >
            저장
          </button>
        </div>
      </div>
    </main>
  </div>

  <footer class="footer">
    <Footers />
  </footer>
</template>

<script setup>
import { ref, watch } from "vue";

import SimpleHdear from "@/components/common/SimpleHeader.vue";
import Footers from "@/components/common/Footers.vue";

import * as mypage from "@/api/mypage";

import { storeToRefs } from "pinia";
import { useAuthStore } from "@/stores/auth";const authStore = useAuthStore();

const { jwtToken } = storeToRefs(authStore);
const token = jwtToken.value;

const oldPassword = ref("");
const newPassword = ref("");
const newPasswordCheck = ref("");
const showError = ref("");
const isOldInputDisabled = ref(false);
const isNewInputDisabled = ref(true);
const isSaveButtonDisabled = ref(true);

const checkPassword = async () => {
  //여기서 try-catch를 해버리니까 console에 mypage 로직 처리중 발생한 에러 로그가 남음
  try {
    const response = await mypage.checkPassword(oldPassword.value, token);
    isOldInputDisabled.value = !isOldInputDisabled.value;
    isNewInputDisabled.value = !isNewInputDisabled.value;
  } catch (error) {
    //에러처리 로직 필요
    const errorType = error.response.data.dataStatus;
    if (error.response.status === 400) {
      if (errorType === null) {
        console.log();
        console.log("입력 타입 에러");
        return;
      }
      if (errorType.code === 6) {
        console.log("비밀번호 불일치");
        return;
      }
    }

    if (error.response.status === 401) {
      console.log("권한이 없습니다.");
      return;
    } else {
      console.log("처리중 오류 발생 : " + error.response.status);
    }
    //인가(토큰만료 등) 에러 잡을 코드도 있는게 좋아보임
  }
};

const changePassword = async () => {
  const response = await mypage.rePassword(
    oldPassword.value,
    newPassword.value,
    token
  );
};

// 입력 값이 변경될 때마다 실행되는 함수
watch(newPasswordCheck, (newValue, oldValue) => {
  handleInputChange(newValue);
});

// 입력 값이 변경될 때 실행되는 함수
function handleInputChange(value) {
  if (newPassword.value !== newPasswordCheck.value) {
    showError.value = "비밀번호가 일치하지 않습니다.";
    isSaveButtonDisabled.value = true;
  } else {
    showError.value = "";
    isSaveButtonDisabled.value = false;
  }
}
</script>

<style lang="scss" scoped>
input {
  border: none;
  outline: none;
  padding: 10px;
  border-radius: 10px; /* 모서리를 둥글게 조절합니다. */
  background-color: #f0f0f0; /* 입력 상자의 배경색을 지정합니다. */
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.3); /* 음푹 패인 효과를 만듭니다. */
}

input:disabled {
  border: none;
  outline: none;
  padding: 10px;
  border-radius: 10px; /* 모서리를 둥글게 조절합니다. */
  background-color: #aaaaaa; /* 입력 상자의 배경색을 지정합니다. */
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.3); /* 음푹 패인 효과를 만듭니다. */
}

.parent-div {
  display: flex;
  flex-direction: column;
  gap: 20px; /* 컴포넌트 간 간격 설정 */
}

header {
  display: flex;
  flex-direction: column;
}

main {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 60vh;
}

main > h2 {
  font-weight: bolder;
  font-size: 45px;
  margin-top: 20px;
}

main > p {
  margin: 20px;
  font-weight: bold;
  color: #00000085;
}

.input-form {
  font-size: 20px;
  margin: auto;
  display: flex;
  flex-direction: column;
}

.input-form > * > * {
  margin: 5px;
}

.old-password-textbox {
  display: flex;
  justify-content: space-between;
}
.old-password-input {
  flex: 1;
}

.new-password-textbox {
  display: flex;
  flex-direction: column;
  width: 500px; //크기 임시조절
}

.error {
  font-size: 15px;
  font-weight: bold;
  color: #cc0606;
}

.btn {
  font-size: 20px;
  font-weight: bold;
  padding: 8px 16px; /* 버튼 패딩 설정 */
  background-color: #6c9f9c; /* 버튼 배경색 설정 */
  color: white; /* 버튼 텍스트 색상 설정 */
  border: none; /* 버튼 테두리 없애기 */
  border-radius: 10px; /* 버튼 모서리를 둥글게 만들기 */
  cursor: pointer; /* 커서 스타일 변경 */
}

.btn:hover {
  background-color: #6c9f9c;
}

.btn:disabled {
  background-color: #476967;
}

.save-btn {
  width: 100px;
}

footer {
  position: fixed;
  bottom: 0;
  width: 100%;
  margin-bottom: 15px;
}
</style>
