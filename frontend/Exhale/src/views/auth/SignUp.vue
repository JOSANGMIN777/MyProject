<template>
  <!-- <img src="@/assets/logo_white.png" class="intro-logo"> -->
  <transition name="fade">
    <EmailAuthentication 
    :email_id="email_id"
    :email_domain="email_domain"
    v-if="show" @close="handleCloseModal"
    @shut="onEmailVerified" />
  </transition>
  <div :style="{ fontSize: fontSize + 'px' }">
    <div  class="container" ref="container">
      <button class="enlarge" @click="enlarge">
        <img src="@/assets/plus.svg" class="plus">
        {{ msg }}
      </button> 
      <!-- FORM SECTION -->
      <form :class="{ 'form-enlarge': show }">
        <!-- SIGN UP -->
        <div class="col align-items-center flex-col sign-up">
          <img src="@/assets/logo_white.png" class="intro-logo">
          <div class="form-wrapper align-items-center">
            <div class="form sign-up" :style="formStyle">
              <div class="input-group">
                <div class="input-group-flex">
                  <input class="input" v-model.trim="userId" id="userId" placeholder="아이디" type="text" />
                  <button class="doubleCheck" @click.prevent="id_Duplicated">중복확인</button>
                </div>
              </div>
              <div>
                <p v-show="userIdErr" class="subtext" style="color: red; font-size: small;" >
                  아이디는 4자리 이상의 영문,숫자가 조합되어야합니다.
                </p>
                <p v-show="userIdDuplicated" class="subtext" style="color: red; font-size: small;" >
                  다른 유저와 중복되는 아이디 입니다.
                </p>
              </div>
              <div class="input-group">
                <div class="input-group-flex">
                  <input class="input" v-model.trim="email" id="email" placeholder="이메일" type="email" :disabled="isVerifying" value="{{ email }}"/> 
                  <button class="doubleCheck" @click.prevent="handleClick" :disabled="isVerifying">인증</button>
                </div >
              </div>
              <p v-show="emailErr" class="subtext" style="color: red; font-size: small;">
                유효한 이메일 주소를 입력하세요.
              </p>

              <div class="input-group">
                <input class="input" v-model.trim="fullname" id="fullname" placeholder="성명" type="text"/>
              </div>
              
              <div class="input-group">
                <input class="input-birth" v-model.trim="birthdate" id="birthdate" placeholder="생년월일" type="Date"/>
              </div>
              
              <div class="input-group">
                <input class="input" v-model="password" id="password" :type="passwordType" placeholder="비밀번호"/>
                <span @click.prevent="toggleVisibility1('password')" class="eye-icon">
                  <img src="@/assets/eye.png" alt="">
                </span>
              </div>
              <p v-show="passwordErr" class="subtext" style="color: red; font-size: small;">
                비밀번호는 10자리 이상의 영문,숫자,특수문자가 조합되어야 합니다.
              </p>
              

              <div class="input-group">
                <input v-model="passwordConfirm" id="password-confirm" :type="passwordConfirmType" placeholder="비밀번호 확인"/>
                <span @click.prevent="toggleVisibility2('passwordConfirm')" class="eye-icon">
                  <img src="@/assets/eye.png" alt="">
                </span>
              </div>
              <p v-show="passwordConfirmErr" class="subtext" style="color: red; font-size: small; margin: 0; padding-right: 20px 20px;">
                비밀번호와 비밀번호확인이 다릅니다.
              </p>
              
              <div class="input-group">
                <input v-model="nickName" id="nickname" placeholder="닉네임" type="text" value="{{ nickName }}"/>
              </div>
              
              <div class="submit1" >
                <button class="btn-bottom" type="submit" @click.prevent="sign_up">
                  회원가입
                </button>
                <div class="kakaoLogin">
                  <button @click="kakaoLogin('signup')" class="kakao">
                    <img src="@/assets/kakao-logo.png" alt="signup">&nbsp;
                    <label>카카오로 시작하기</label>
                  </button>
                </div>
              </div>
              
              <p class="undertext1">
                <span>
                  이미 계정이 있으신가요?
                </span>
                <b @click="toggle" class="pointer">로그인</b>
              </p>
            </div>
          </div>
        </div>
        <!-- END SIGN UP -->
      </form>
      <!-- END FORM SECTION -->
    
      
      <!-- CONTENT SECTION -->
      <div class="row content-row">
        <!-- SIGN UP CONTENT -->
        <div class="col align-items-center flex-col">
          <div class="text sign-up">
            <!-- <h2>Sign Up</h2> -->
            <!-- <p>희망을 잃지 마세요!
              환영합니다! 4종 1,000개가 넘는 언어 재활 코스와 함께 일상으로의 복귀를 도와드리고 있어요. 함께 노력해봐요.
            </p> -->
          </div>
        </div>
        <!-- END SIGN UP CONTENT -->
      </div>
    </div>

  </div>
</template>

<script setup>
import { reactive, ref, computed, onMounted, watch, defineProps } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { RouterLink } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { isIdDuplicated ,signUp, kakaoLogin, verifyNumberCreate, checkCode} from '@/api/outhApi.js';
import EmailAuthentication from '@/components/modals/EmailAuthentication.vue'
const store = useAuthStore()
const fontSize = ref(16);
const router = useRouter();
const userId = ref('');
const email = ref('');
const email_id = ref('');
const email_domain = ref('');
const fullname = ref('');
const birthdate = ref('');
const password = ref('');
const passwordConfirm = ref('');
const nickName = ref('');
const passwordType = ref("password");
const passwordConfirmType = ref("password");
const isVerifying = ref(false)

// 에러변수 Boolean
const userIdErr = ref(false);
const emailErr = ref(false);
const passwordErr = ref(false);
const passwordConfirmErr = ref(false);
const userIdDuplicated = ref(false)


// 에러메세지 보이면 form 크기 확장
const formStyle = reactive({
  transform: 'translateY(2%)',
})
watch([userIdErr, userIdDuplicated, emailErr, passwordErr, passwordConfirmErr], () => {
  const visibleSubtexts = [userIdErr, userIdDuplicated, emailErr, passwordErr, passwordConfirmErr].filter(ref => ref.value).length
  formStyle.transform = `translateY(${visibleSubtexts * 2}%)`
})


// 이메일 인증 모달 창
const show = ref(false)

const handleClick = () => {
  if (email.value && !emailErr.value)
  {show.value = !show.value
  verify_Number_Create()
  }
  else {
    emailErr.value = true
  }
}

// 이메일 인증 모달 창 닫기
const handleCloseModal = () => {
  show.value = false
}

const onEmailVerified = () => {
  isVerifying.value = true
  console.log(isVerifying.value)
}

// 이메일 도메인 분리
watch(email, () => {
    const emailArr = email.value.split("@");
    if (emailArr.length === 2) {
        email_id.value = emailArr[0]; // 사용자 이름 부분 할당
        email_domain.value = emailArr[1]; // 도메인 부분 할당
    }
});

//유저아이디 조건
function check_userId() {
  const userIdValid = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,}$/
  userIdErr.value = !userIdValid.test(userId.value)
}
watch(userId, ()=> {
  check_userId();
})

//유저이메일 조건
function check_email() {
  const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]{3,}$/;
  emailErr.value = !emailValid.test(email.value);
}
watch(email, () => {
  check_email();
})

//유저비밀번호 조건
function check_password() {
  const passwordValid = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{10,}$/;
  passwordErr.value = !passwordValid.test(password.value);
}
watch(password, () => {
    check_password()
})
const idDuplicated = ref(false)
//유저 비밀번호확인
function check_passwordConfirm() {
  if (password.value !== passwordConfirm.value){
    passwordConfirmErr.value = true
  }
  else {
    passwordConfirmErr.value = false
  }
}
watch(passwordConfirm, () => {
  check_passwordConfirm()
});

// Id 중복확인
const id_Duplicated = async () => {
  if (!userId.value || userIdErr.value) {  
    return;
  }
 const response =  await isIdDuplicated(
     userId.value
)
userIdDuplicated.value = response
};


// 회원가입 
const sign_up = async () => {
  if (!userId.value || !email.value || !password.value || !passwordConfirm.value || userIdErr.value || userIdDuplicated.value || emailErr.value || passwordErr.value || passwordConfirmErr.value) {
    alert('입력 값들을 다시 확인해주세요.');
    return;
  }
    try {
      const response = await signUp(
        userId.value, 
        email_id.value,
        email_domain.value, 
        fullname.value,
        birthdate.value, 
        password.value, 
        nickName.value
        );
        
        router.push('/login')
      } catch (error) {
        }};

  // 글자크기조절 버튼
const msg = computed(() => fontSize.value > 28 ? '원래대로' : '글자확대');
const enlarge = () => {
  fontSize.value += 4;
  if (fontSize.value > 32) {
    fontSize.value = 16
  }
}

// 암호화 on/off
const toggleVisibility1 = (field1) => {
  if (field1 === 'password') {
    passwordType.value = passwordType.value === 'password' ? 'text' : 'password';
  }}; 
const toggleVisibility2 = (field2) => {
  if (field2 === 'passwordConfirm') {
    passwordConfirmType.value = passwordConfirmType.value === 'password' ? 'text' : 'password';
  }
  };


// const queryParams = new URLSearchParams(window.location.search);
//     const code = queryParams.get('code');
//     if (code) {
//       localStorage.setItem('kakao_outh_code', code);
//     }

// const sendKakaoTokenToServer = async () => {
//   const kakaoToken = localStorage.getItem('kakao_outh_code');
//   if (!kakaoToken) {
//     console.error('카카오 인증 코드가 없습니다.');
//     return;
//   }
//   try {
//     const response = await axios.post('api/outh/kakao/login', {
//       code : kakaoToken
//     });

//     // 서버 응답 처리
//     console.log('서버 응답:', response.data);
//   } catch (error) {
//     console.error('서버 요청 에러:', error);
//   }
// };

// sendKakaoTokenToServer();

// const isIdDuplicated = async (userId) => {
//   try {
//     const response = await axios ({
//     method: 'post',
//     url: 'api/general/id',
//     headers: {
//       "Content-Type":"application/json"
//     },
//     data: {
//       login_id: userId.value,
//     }
//     });

//     if (response.data === 2) {
//       console.log('이미 존재하는 아이디입니다.')
//       return true;  // true이면 input 초기화
//     } else {
//       console.log('사용 가능한 아이디입니다.')
//       return response.data, false; // false일 경우 그대로 
//     }
//   } catch (error) {
//     console.error('아이디 중복 확인 요청 에러:', error)
//     throw error;
//   }};


// 이메일 인증요청
const verify_Number_Create = async () => {
if(isVerifying.value) return
try{
  const response = await verifyNumberCreate(
    email_id.value,
    email_domain.value
    );
  }catch(error) {
    console.log('에러발생',error)
}};



const toggle = () => {
    router.push({ name: 'Login'})
  };
const container = ref(null);

// container ref가 mount 되었을 때 sign-in class 추가
  onMounted(() => {
    setTimeout(() => {
      if (container.value) {
        container.value.classList.add('sign-up')
      }
    }, 300)
})
const appURL = import.meta.env.VITE_APP_API_URL;

const checkJoinCode = async() => {
  let url = new URL(window.location.href);
  const code = url.searchParams.get('code')  //코드 받아옴
  if(code !== null){
    const response = await checkCode(code, 'join');
    if(response === 2){
      alert("이미 존재하는 회원입니다.", window.location.href = `${appURL}/signup`)
    }
    const data = response.data.response;
    isVerifying.value=true;
    email.value = data.emailId + '@' + data.emailDomain
    nickName.value = data.nickname;
  }
}

checkJoinCode()

</script>

<style lang="scss" scoped>
  @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600&display=swap');
  @import "@/assets/scss/pages/_signup.scss";
  .input{
    width: 100%;
  }

  .input:disabled{
    background-color: rgb(207, 206, 206);
  }

  .doubleCheck:disabled{
    background-color: rgb(122, 122, 122);
    pointer-events: none;
  }
  /* .input-danger{
  border-bottom: 2px solid red !important;
  color: red;
}

.title-danger{
  color: red !important;
}

.input-error {
    line-height: 16px;
    font-size: 11px;
    color: red;
  } */


</style>
