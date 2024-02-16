<template>
   <h1 style="text-align: center;">로그인 완료</h1>
  </template>
  
  <script setup>
  import { onMounted } from 'vue';
  import axios from 'axios';
  
      onMounted(() => {
        isMember();
      });
  
      const isMember = () => {
        let code = new URL(window.location.href).searchParams.get('code');
        const codeDataForm = new FormData();
        codeDataForm.append('code', code);
  
        axios.post(`YOUR-URL`, codeDataForm, {
          headers: {
            'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
          },
        })
        .then((response) => {
          let ACCESS_TOKEN = response.data.access_token;
          window.Kakao.Auth.setAccessToken(ACCESS_TOKEN);
          // 이후 로그인 처리
        })
        .catch((error) => {
          console.log(error.response.data);
        });
      };
    
  </script>
  