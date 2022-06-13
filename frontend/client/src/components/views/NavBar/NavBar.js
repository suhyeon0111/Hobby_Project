import React, { useState } from 'react';
import LeftMenu from './Sections/LeftMenu';
import RightMenu from './Sections/RightMenu';
import { Drawer, Button, Icon } from 'antd';
import './Sections/Navbar.css';
import LogoImg from './img/logo.png'

function NavBar() {
  const [visible, setVisible] = useState(false)

  const showDrawer = () => {
    setVisible(true)
  };

  const onClose = () => {
    setVisible(false)
  };

  return (
    <nav className="menu" style={{ position: 'fixed', zIndex: 5, width: '100%' }}>
      <div className="menu__logo">
        <a href="/"><img src={LogoImg} style={{width: '147px'}} /></a>
      </div>
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
          style={{width: '40px', height: '40px', marginTop: '20px'}}
        >
          <Icon type="menu" style={{fontSize: '25px'}} />
        </Button>
        <Drawer
          title="Hoppy"
          placement="left"
          className="menu_drawer"
          closable={false}
          onClose={onClose}
          visible={visible}
        >
          
          <RightMenu mode="inline"/>
          <hr style={{width: '80%', backgroundColor: '#D3BA9C'}}/>
          <LeftMenu mode="inline" />
          {/* <RightMenu mode="inline" /> */}
        </Drawer>
      </div>
      {/* <div style={{float: 'right', display: 'inline-block'}}>
        <Icon type='bell' />
      </div> */}
    </nav>
  )
}

export default NavBar