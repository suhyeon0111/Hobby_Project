import React from 'react';
import { Menu } from 'antd';
import { withRouter } from 'react-router-dom';;

function RightMenu(props) {

    if (localStorage.Authorization === 'Bearer null') {
      return (
        <Menu mode={props.mode}>
          <Menu.Item key="login">
            <a href="/login">로그인 ▷</a>
          </Menu.Item>
        </Menu>
      )
    } else {
      return (
        <Menu mode={props.mode}>
          <Menu.Item key="logout">
            <a href="/login">로그아웃 ▷</a>
            {/* <a onClick={logoutHandler}>로그아웃 ▷</a> */}
          </Menu.Item>
        </Menu>
      )
    }
}

export default withRouter(RightMenu);

