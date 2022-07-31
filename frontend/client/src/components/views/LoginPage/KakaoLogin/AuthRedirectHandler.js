import Axios from 'axios'
import React from 'react'

Axios.defaults.withCredentials = true;

function AuthRedirectHandler(props) {

    // console.log('props', props)
    const logKey = props.location.search
    // console.log(logKey)
    const splitLogKey = logKey.split('=')
    // console.log(splitLogKey[1])
    let certificationCode = splitLogKey[1].slice(0, -6)
    console.log(certificationCode)

    const loginSuccess = () => {
      if (certificationCode === undefined) {
          console.log('로그인에 실패했습니다.')
          delete Axios.defaults.headers.common['Authorization'];
          return <React.Fragment>
            <p>로그인 실패 ㅠ</p>
            </React.Fragment>
      } else {
          console.log('로그인에 성공하였습니다.')
          // const jwtToken = "najdadaskdjapdwanalkcasljdaidwiCBAUAYabcaoCAYOAWKDQKsGOG"
          
          // fetch('/login/oauth2/code/kakao')
          //   .then(response => {
          //     console.log('res', response)
          //     if(response.status === 200) {
          //       localStorage.setItem('Authorization', `Bearer ${jwtToken}`);
          //     }
          //   })
          
          // Axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;

            props.history.push('/')
          return <React.Fragment>
            <p>로그인 성공! {certificationCode}</p>
            </React.Fragment>
      }
    }

  return (
    <div style={{width: "100%"}}>
      {loginSuccess()}
    </div>
  )
}

export default AuthRedirectHandler