import React from 'react'

function AuthRedirectHandler(props) {

    console.log('props', props)
    const logKey = props.location.search
    console.log(logKey)
    const splitLogKey = logKey.split('=')
    console.log(splitLogKey[1])

    const successLogin = () => {
      if (splitLogKey[1] === undefined) {
          console.log('로그인에 실패했습니다.')
          return <>카카오 로그인에 실패했습니다.</>
      } else {
          console.log('로그인에 성공하였습니다.')
          return <>카카오 로그인에 성공했습니다.</>
      }
    }


  return (
    <div>
        {successLogin()}
    </div>
  )
}

export default AuthRedirectHandler