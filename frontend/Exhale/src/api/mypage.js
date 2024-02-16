import axios from "axios";

const baseURL = import.meta.env.VITE_APP_BASE_URL + "/api/";

const getProfile = (token) => {
  
  return axios.get(baseURL + "users/profile", {
    headers: {
      Authorization: token,
    },
  });
};

const checkPassword = (password, token) => {
  const data = {
    password: password,
  };

  return axios.post(baseURL + "users/check-password", data, {
    headers: {
      Authorization: token,
    },
  });
};

const rePassword = (old_password, new_password, token) => {
  const data = {
    old_password: old_password,
    new_password: new_password,
  };

  return axios.post(baseURL + "users/repassword", data, {
    headers: {
      Authorization: token,
    },
  });
};

const withdraw = (token) => {
  return axios.get(baseURL + "users/withdraw", {
    headers: {
      Authorization: token,
    },
  });
};

export { getProfile, checkPassword, rePassword, withdraw };
