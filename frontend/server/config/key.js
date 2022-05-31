const CLIENT_ID = '146414e0f2cf5ef05dee863aae51615a';
const REDIRECT_URI = 'http://localhost:8888/oauth/login/kakao';

export const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?&client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;