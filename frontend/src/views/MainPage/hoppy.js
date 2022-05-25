import React from 'react'
import { Row, Col } from 'antd'
import Logo from './img/Logo.png'

function hoppy() {

  return (
    <div style={{textAlign: 'center'}}>
    <Row>
      <Col xs={20} sm={16} md={12} lg={8} xl={4}>
        <img src={Logo} alt='img'></img>
        <br />
          <button>
              <a href='./login'>
                로그인 페이지
              </a>
          </button>
      </Col> 
    </Row>
    </div>
  )
}

export default hoppy