import axios from "axios";

const baseURL = import.meta.env.VITE_APP_BASE_URL + "/api/";

const getLetterData = (token) => {
  return axios.get(baseURL + "statistics/letter", {
    headers: {
      Authorization: token,
    },
  });
};

const getSolvedData = (token) => {
  return axios.get(baseURL + "statistics/solved-data", {
    headers: {
      Authorization: token,
    },
  });
};

export { getLetterData, getSolvedData };
