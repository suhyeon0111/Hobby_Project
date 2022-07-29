import axios from 'axios';
import {
    // LOGIN_USER,
    // REGISTER_USER,
    // AUTH_USER,
    // LOGOUT_USER,
    GET_USER,
    // DELETE_USER,
} from './types';
// import { USER_SERVER } from '../components/Config.js';

// export function registerUser(dataToSubmit){
//     const request = axios.post(`${USER_SERVER}/register`,dataToSubmit)
//         .then(response => response.data);
    
//     return {
//         type: REGISTER_USER,
//         payload: request
//     }
// }

// export function loginUser(dataToSubmit){
//     const request = axios.post(`${USER_SERVER}/login`,dataToSubmit)
//                 .then(response => response.data);

//     return {
//         type: LOGIN_USER,
//         payload: request
//     }
// }

// export function auth(){
//     const request = axios.get(`${USER_SERVER}/auth`)
//     .then(response => response.data);
    
//     return {
//         type: AUTH_USER,
//         payload: request
//     }
// }

// export function logoutUser(){
//     const request = axios.get(`${USER_SERVER}/logout`)
//     .then(response => response.data);

//     return {
//         type: LOGOUT_USER,
//         payload: request
//     }
// }

///////////////////////

//withCredentials 전역 설정 -- CORS error
axios.defaults.withCredentials = false;

export function getUser() {
    const token = localStorage.getItem('Authorization')
    
    const request = axios.get(`https://hoppy.kro.kr/api/myprofile`, {
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

// export function deleteUser() {
//     const token = localStorage.getItem('Authorization')
    
//     const request = axios.get(`https://hoppy.kro.kr/api/delete`, {
//         headers: {
//             Authorization: token
//         }, withCredentials: false
//     })
//     .then(response => response.data);

//     return {
//         type: DELETE_USER,
//         payload: request
//     }
// }
