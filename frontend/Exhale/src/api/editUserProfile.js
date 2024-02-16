import axios from "axios";

const baseURL = import.meta.env.VITE_APP_BASE_URL + "/api/";

//닉네임 변경
const editNickname = (nickname, token) => {
  const datas = {
    nickname: nickname,
  };

  return axios.post(baseURL + "users/nickname", datas, {
    headers: {
      Authorization: token,
    },
  });
};

//이미지 변경
const editProfileImage = (formData, token) => {
  return axios.post(baseURL + "users/profile-image", formData, {
    headers: {
      Authorization: token,
    },
  });
};

//닉네임, 이미지 변경 요청 동시에 보내기
const editUserProfile = (nickname, formData, token) => {
  const datas = {
    nickname: nickname,
  };

  return axios.all([
    //닉네임 변경
    axios.post(baseURL + "users/nickname", datas, {
      headers: {
        Authorization: token,
      },
    }),

    //이미지 변경
    axios.post(baseURL + "users/profile-image", formData, {
      headers: {
        Authorization: token,
      },
    }),
  ]);
};

export { editNickname, editProfileImage, editUserProfile };
