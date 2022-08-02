import React, { useEffect } from 'react';
import { auth } from '../_actions/user_actions';
import { useSelector, useDispatch } from "react-redux";

export default function (SpecificComponent, option, adminRoute = null) {
    function AuthenticationCheck(props) {
        
        // let user = useSelector(state => state.user);
        // console.log('s', user)
        const dispatch = useDispatch();

        useEffect(() => {
            //To know my current status, send Auth request 
            dispatch(auth()).then(response => {
                console.log('res', response)
                //Not Loggined in Status 
                // if (!response.payload.isAuth) {
                //     if (option) {
                //         props.history.push('/login')
                //     }
                //     //Loggined in Status 
                // } else {
                //     //supposed to be Admin page, but not admin person wants to go inside
                //     if (adminRoute && !response.payload.isAdmin) {
                //         props.history.push('/')
                //     }
                //     //Logged in Status, but Try to go into log in page 
                //     else {
                //         if (option === false) {
                //             props.history.push('/')
                //         }
                //     }
                // }
            })

        }, [])

        return (
            <SpecificComponent {...props} />
        )
    }
    return AuthenticationCheck
}

// // let auth = (req, res, next) => {
// //   let token = req.cookies.w_auth;

// //   User.findByToken(token, (err, user) => {
// //     if (err) throw err;
// //     if (!user)
// //       return res.json({
// //         isAuth: false,
// //         error: true
// //       });

// //     req.token = token;
// //     req.user = user;
// //     next();
// //   });
// // };

// // module.exports = { auth };

// import React from 'react'

// function auth(props) {
//   let jwtToken = localStorage.Authorization
//   if (jwtToken === undefined) {
//     props.history.push('/login')
//   } else {
//     props.history.push('/')
//   }
// }

// export default auth
