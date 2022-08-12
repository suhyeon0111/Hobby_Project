import React, { useState } from 'react';
import LeftMenu from './Sections/LeftMenu';
import RightMenu from './Sections/RightMenu';
import { Drawer, Button, Icon } from 'antd';
import './Sections/Navbar.css';
import LogoImg from './img/logo.png'
import Bell from './img/Bell.png'
// import { Link } from 'react-router-dom';

function NavBar() {
  const [visible, setVisible] = useState(false);

  const showDrawer = () => {
    setVisible(true);
  };

  const onClose = () => {
    setVisible(false);
  };

  // const token = localStorage.getItem('Authorization')
  // console.log(token)

  return (
    <nav
      className="menu"
      style={{ position: "fixed", zIndex: 5, width: "100%" }}
    >
      <div className="menu__container">
        <div className="menu_left">
          <LeftMenu mode="horizontal" />
        </div>
        <div className="menu_rigth">
          <RightMenu mode="horizontal" />
        </div>
        <Button
          className="menu__mobile-button"
          type="text"
          onClick={showDrawer}
          style={{ width: "40px", height: "40px", marginTop: "20px" }}
        >
          {/* <img src={MenuIcon} style={{ width: "30px", marginRight: "2px" }} /> */}
          <Icon type="menu" style={{ fontSize: "25px", color: "#A5A5A5" }} />
        </Button>
        <Drawer
          title=""
          placement="left"
          className="menu_drawer"
          closable={false}
          onClose={onClose}
          visible={visible}
        >
          <div style={{ marginTop: '100px' }}>
            <img src={LogoImg} alt="로고이미지" style={{ width: '107px', height: '54px', marginLeft: '25%', marginBottom: '30px'}} />
            <RightMenu mode="inline" />
            <hr style={{ width: "80%", backgroundColor: "#D3BA9C" }} />
            <LeftMenu mode="inline" />
          </div>
          </Drawer>
      </div>
      <div style={{ float: 'right' }}>
        <a href="/notification" className="menu__bell">
          <img src={Bell} alt="알림" style={{ width: "45px" }} />
        </a>
      </div>
      <div className="menu__logo">
        <a href='/'>
          <img src={LogoImg} alt="로고" style={{ width: "147px" }} />
        </a>
      </div>
    </nav>
  );
}

export default NavBar;
