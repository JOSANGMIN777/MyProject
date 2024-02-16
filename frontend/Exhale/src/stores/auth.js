  import { defineStore } from 'pinia';
  import { ref } from 'vue'


  export const useAuthStore = defineStore('auth', () => {
    const jwtToken = ref('');
    const refreshToken = ref('');
    const key = ref('');
    const loginId = ref('');
    const memberId = ref(0);
    const nickname = ref('');
    const isLogin = ref(false);

    const saveUserInfo = (jwtTokenValue, refreshTokenValue, keyValue, loginIdValue, memberIdValue, nicknameValue) => {
      jwtToken.value = jwtTokenValue;
      refreshToken.value = refreshTokenValue;
      key.value = keyValue;
      loginId.value = loginIdValue;
      memberId.value = memberIdValue;
      nickname.value = nicknameValue;
      isLogin.value = true;
    }
    const removeUserInfo = () => {
      jwtToken.value = '';
      refreshToken.value = '';
      key.value = '';
      loginId.value = '';
      memberId.value = 0;
      nickname.value = '';
      isLogin.value = false;
    }
    return { saveUserInfo,removeUserInfo, isLogin, jwtToken, refreshToken, key, loginId, memberId, nickname}
  } , {
    // persist 설정
    persist: {
      // persist 활성화
      enabled: true,
      // localStorage에 저장될 필드 선택 (기본적으로 모든 필드가 저장됨)
      fields: ['loginId', 'memberId', 'nickname'],
      // 필요한 경우 직렬화 및 역직렬화 함수 추가
      // serializer: (value) => JSON.stringify(value),
      // deserializer: (value) => JSON.parse(value)
    }
  })

 