import React from 'react'
import Axios from 'axios';

function AuthRedirectHandler(props) {

  console.log('location', props.location)
  const param = new URLSearchParams(props.location.search);
  console.log('jwtToken>>>>>', param.get("token"))
  const jwtToken = param.get("token");

  
  if (jwtToken !== undefined) {
    localStorage.setItem('Authorization', `Bearer ${jwtToken}`);
    Axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
    props.history.push('/')
  } else {
    localStorage.removeItem('Authorization', `Bearer ${jwtToken}`);
    delete Axios.defaults.headers.common['Authorization'];
  }

  return (
    <div>AuthRedirectHandler</div>
  )
}

export default AuthRedirectHandler