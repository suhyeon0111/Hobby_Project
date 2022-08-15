import React from 'react'
import Axios from 'axios';

function LogoutPage(props) {

    const token = localStorage.getItem('Authorization')
    
    if (token !== undefined) {
        localStorage.removeItem('Authorization', `Bearer ${token}`);
        delete Axios.defaults.headers.common['Authorization'];
        props.history.push('/')
    }

  return (
    <div>LogoutPage</div>
  )
}

export default LogoutPage