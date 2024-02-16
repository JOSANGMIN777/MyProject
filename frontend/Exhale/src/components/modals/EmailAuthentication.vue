<template>
  
<div class="authentication">  
  <img src="@/assets/logo_white.png">
  <hr class="hr">
  <button class="closeBtn" @click="closeModal">
    <img src="@/assets/backArrow2.svg" class="close" >
  </button>

  <div class="texts">
    <p>날숨 서비스 이용을 위해</p>
    <p class="secondTexts">이메일 인증이 한 번 필요해요.</p>
  </div>
  
  <div class="text2">
    <p>이메일로 전송된 인증코드를 입력해주세요.</p>
  </div>

  <div class="text-center">
    <v-otp-input
      ref="otpInputRef"
      focus-all
      v-model="otp"
      :loading="loading"
      min-width="600"
      length="6"
      variant="underlined"
      class="numberInput"
    ></v-otp-input>

    <v-btn
      :disabled="otp.length < 6 || loading"
      class="submitBtn"
      variant="tonal"
      @click="onClick"
    >전송</v-btn>
  </div>
  
  <p class="texts3">
    이메일을 받지 못하셨나요?
    <a class="emailSubmit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;이메일 재전송하기</a>
  </p>

</div>
</template>

<script setup>
import { ref, onMounted, defineEmits, defineProps } from 'vue';
import {emailVerifyRequest} from '@/api/outhApi'

const otpInputRef = ref(null)
const loading = ref(false)
const otp = ref('')
const emit = defineEmits(['close', 'shut'])

const props = defineProps({
  email_id:String,
  email_domain:String
})
// 모달창 마운트되면 인증코드 입력필드에 포커스 주는 함수(전송하면 해제)
onMounted(() => {
  otpInputRef.value.focus()
})
const onClick = () => {
    email_VerifyRequest()
  loading.value = true

  setTimeout(() => {
    otpInputRef.value
    loading.value = false
  }, 2000)
}

// 모달창 닫기 SignUp.vue에 전달
const closeModal = () => {
  emit('close');
  emit('shut');
}


const email_VerifyRequest = async () => {
  console.log(otp.value)
  
try{
  const response = await emailVerifyRequest(
    props.email_id,
    props.email_domain,
    otp.value
  )
  closeModal()
  console.log('인증성공!')
} catch(error){
  console.log('인증을 실패하였습니다', error)
}
}

</script>

<style lang="scss" scoped>
@import "@/assets/scss/components/_modal.scss";


</style>