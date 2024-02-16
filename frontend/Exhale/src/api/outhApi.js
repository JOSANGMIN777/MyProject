import axios from "axios";

const baseURL = import.meta.env.VITE_APP_BASE_URL;
const kakaoApiKey = import.meta.env.VITE_APP_KAKAO_API_APP_KEY;
    // 2 - 이미 존재하는 값
    const isIdDuplicated = async (userId) => {
        
        try {
        const response = await axios.post('https://www.ssafyexhale.shop/api/general/id', {
            login_id: userId
        });
    
        alert("사용 가능한 아이디입니다.");
        return false; 
    } catch (error) {
        if (error.response && error.response.status === 400) {
            alert("아이디가 이미 사용 중입니다.");
            return true; 
        } else {
            console.error("아이디 중복 확인 중 오류가 발생했습니다:", error);
            
        }
    }
};

const verifyNumberCreate = async (email_id, email_domain) => {
    try {
        const response = await axios.post('https://www.ssafyexhale.shop/api/email/certification', {
            email_id: email_id,
            email_domain : email_domain
        })
        if (response) {
        console.log('인증번호 전송완료!', response)}
    } catch (error) {
        console.error(error)
        alert('이미 인증된 이메일입니다. 다른 이메일 계정으로 시도해주세요.')

        }}; 

const emailVerifyRequest = async (email_id,email_domain,code) => {
    return await axios.post('https://www.ssafyexhale.shop/api/email/check',  {
        email_id: email_id,
        email_domain:email_domain,
        code: code
    })
};

const signUp = async (userId, email_id, email_domain, fullname, birthdate, password, nickName) => {      
    try {
        const response = await axios.post('https://www.ssafyexhale.shop/api/general/join', {
            login_id: userId,
            password: password,
            email_id: email_id,
            email_domain: email_domain,
            name: fullname,
            birth: birthdate,
            nickname: nickName
        });
        // console.log('회원가입 성공:', response);
        alert('날숨의 가족이 되신것을 환영해요! 다시 로그인 해주세요');
        
        // return response.data; 
        } catch (error) {
        if (axios.isAxiosError(error)) {
            // 서버 응답 에러 처리
            console.error('서버 요청 에러:', error.response?.data || error.message);
            alert(`서버 에러 발생: ${error.response?.statusText || error.message}`);
        } else {
            // 그 외 에러 처리
            console.error('알 수 없는 에러:', error);
            alert('알 수 없는 에러가 발생했습니다.');
        }
        }
    };


const sendKakaoCode = async (kakaoCode) => {
    return await axios.post('/api/outh/kakao/join', {
        code: kakaoCode
    })
};

const logIn = async (userId, password) => {
    const response = await axios.post(`${baseURL}/api/general/login`, {
        login_id: userId,
        password: password,
    }, {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        }
    });
    return response.data;
    
};
// todo 구현
const tempPassword = async (userId, email_id, emailDomain) => {
    return await axios.post('https://www.ssafyexhale.shop/api/users/temp-password',  {
        login_id: userId,
        email_id: email_id,
        email_domain: emailDomain
    })
};
// todo 구현
const reFresh = async (refresh_token, member_id) => {
    return await axios.post('https://www.ssafyexhale.shop/api/users/refresh', {
        refresh_token : refresh_token,
        member_id : member_id
    }, {
        headers : {
            'Authorization': `Bearer ${accessToken}`
        }
    })
};

const logout = async (key, refreshToken) => {
    
    return await axios.post(`${baseURL}/api/users/logout`, {
        key : key
    },{
        headers : {
            "Authorization" : refreshToken
        }
    })
}

// todo 구현
const kakaoLogin = (type) => {
    const redirectUri = `https://www.ssafyexhale.shop/${type}`;
    const url = `https://kauth.kakao.com/oauth/authorize?client_id=${kakaoApiKey}&redirect_uri=${redirectUri}&response_type=code`
    // 사용자를 카카오 로그인 페이지로 리디렉션
    window.location.href = url;
  };

//   const logOut = async (key, refresh_token) => {
//     return await axios.get('https://www.ssafyexhale.shop/api/users/logout') ,{
//         key : key
//     }, {
//         headers : {
//             'Authorization': `${refresh_token}`
//         }
//     }
 
    // localStorage.removeItem('JWT_token')
    // localStorage.removeItem('refresh_token')
  
    // router.push('/')
//   };
const checkCode = async(code, type) => {
    try{
        if(code !== null){
        //code로 정보 요청
            // return await axios.get(baseURL + `auth/kakao/${type}?code=${code}`);
            return await axios.get("https://www.ssafyexhale.shop/api/" + `auth/kakao/${type}?code=${code}`);
        }; 
    } catch(error){
        console.log(error)
        if(error.response.data.dataStatus.code === 2) return 2;
    }
}

export {
    isIdDuplicated,
    checkCode,
    emailVerifyRequest,
    verifyNumberCreate,
    signUp,
    sendKakaoCode,
    logIn,
    tempPassword,
    reFresh,
    logout,
    kakaoLogin
    
};
