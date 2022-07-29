import Axios from "axios";

export function fileUpload() {
  
  const token = localStorage.getItem('Authorization')
  
  const request = Axios.get(`https://hoppy.kro.kr/api/myprofile`, {
        headers: {
            Authorization: token
        }, withCredentials: false
    })
    .then(response => response.data);

    return {
        type: GET_USER,
        payload: request
    }
}