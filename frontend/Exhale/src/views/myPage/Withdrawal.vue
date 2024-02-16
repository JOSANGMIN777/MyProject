<template>
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
        <RouterLink class="breadlink" :to="{ name: 'Withdrawal' }"
          >회원탈퇴</RouterLink
        >
      </div>
    </div>
  </header>
    <main>
        <div class="textbox">
            <h2>회원탈퇴</h2>
            <p> 서비스가 아쉬우셨나요?<br>
                비밀번호를 입력 후 회원탈퇴 버튼을 누르시면<br>
                회원정보가 영구적으로 삭제됩니다.
            </p>
        </div>

        <div class="withdraw-box">
            <p>Password</p>
            <input type="text" v-model="password" placeholder="비밀번호 입력"/>
            <h3 class="error-message">{{ errorMessage }}</h3>
            <button class="withdraw-btn" @click="withdrawCheck"
            :disabled="btnDisabled">회원탈퇴</button>
        </div>
    </main>

    <footer class="footer">
        <Footers />
    </footer>
</template>

<script setup>
    import { ref, watch } from "vue";
    import { withdraw, checkPassword } from "@/api/mypage"
    import { logout } from "@/api/outhApi"
    import { useRouter } from "vue-router"
    import { useAuthStore } from '@/stores/auth';

    import SimpleHdear from "@/components/common/SimpleHeader.vue";
    import Footers from "@/components/common/Footers.vue";

    const router = useRouter();

    const authStore = useAuthStore();
    const { jwtToken } = storeToRefs(authStore);
    const token = jwtToken.value;
    
    const errorMessage = ref("");
    const btnDisabled = ref(false);
    const password = ref("");

    const withdrawCheck = async() => {
        const isValidPassword = await checkInputPassword();
        if(!isValidPassword){
            return;
        }

        const result = confirm("회원 탈퇴를 진행하시겠습니까?")

        if(result){
            withdrawUser()
        }else{
            alert("회원탈퇴가 취소되었습니다.")
        }
    }

    const withdrawUser = async () => {
        const response = await withdraw(token);
        await logout();
        authStore.removeUserInfo();
        alert("회원탈퇴가 완료되었습니다.");
        router.push({ name: 'Login' })
    }


    watch(password, (newValue) => {
        handleInputChange(newValue);
    });

// 입력 값이 변경될 때 실행되는 함수
    function handleInputChange() {
        let result = true;
        const isLengthOk = password.value.length >= 10;

        // 숫자, 영문자, 특수문자 포함 여부 체크
        const hasNumber = /[0-9]/.test(password.value);
        const hasLetter = /[a-zA-Z]/.test(password.value);
        const hasSpecialChar = /[!@#$%^&*()_+\[\]{}|;:,.<>?]/.test(password.value);

        // 다른 문자(한글, 타 외국어 등) 사용 여부 체크
        const hasOtherChar = /[^\x00-\x7F]/.test(password.value);

        // 모든 조건 충족 여부 반환
        result = isLengthOk && hasNumber && hasLetter && hasSpecialChar && !hasOtherChar;

        if(!result){
            btnDisabled.value = true;
            errorMessage.value = "비밀번호는 10자 이상이며 영어, 숫자, 특수문자를 1개 이상 포함해야 합니다.";
        }else{
            btnDisabled.value = false;
            errorMessage.value = "   ";
        }
    }

    const checkInputPassword = async () => {
        try {
            const response = await checkPassword(password.value, token);
            return true;
        } catch (error) {
            //에러처리 로직 필요
            const errorType = error.response.data.dataStatus;
            if (error.response.status === 400) {
                if (errorType === null) {
                    alert("비밀번호 형식이 올바르지 않습니다.");
                }
                if (errorType.code === 6) {
                    alert("비밀번호가 일치하지 않습니다.");
                }
            }
            if (error.response.status === 401) {
                alert("권한이 없는 사용자입니다.");
            } else {
                console.log("처리중 오류 발생 : " + error.response.status);
            }
            return false;
            //인가(토큰만료 등) 에러 잡을 코드도 있는게 좋아보임
        }
    };

    
</script>

<style lang="scss" scoped>
    .widthdraw-btn{
        background-color: aquamarine;
    }

    main {
        display: flex;
        flex-direction: column;
        align-items: center;
        height: 70vh;
    }

    main > * {
        margin:10px;
    }

    .textbox{
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    .textbox > h2{
        font-weight: bold;
    }

    .textbox > p {
        font-size: 17px;
        margin-top: 40px;
        text-align: center;
    }
    
    .withdraw-box{
        height: 100%;
        margin-top: 20px;
        display: flex;
        flex-direction: column;
        width: 30%;
    }

    .withdraw-box > p{
        font-size: 23px;
        color: #6c9f9c;
        font-weight: bolder;
    }

    .withdraw-box > input{
        width: 100%;
        height: 60px;
        border: none;
        outline: none;
        padding: 10px;
        border-radius: 10px; /* 모서리를 둥글게 조절합니다. */
        background-color: #f0f0f0;
        box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.3); 
    }

    .error-message{
        height: 14px;
        margin-top: 5px;
        font-size: 14px;
        color: rgb(228, 0, 0);
    }

    .withdraw-btn{
        font-size: 20px;
        font-weight:bolder;
        color: white;
        height: 50px;
        border-radius: 20px;
        justify-self: flex-end;
        background-color: #6c9f9c;
        margin-top: auto;
        margin-bottom: auto;
    }

    .withdraw-btn:disabled {
        color: rgb(150, 150, 150);
        background-color: #36504f;
    }

    footer {
        position: fixed;
        bottom: 0;
        width: 100%;
        margin-bottom: 15px;
    }
</style>