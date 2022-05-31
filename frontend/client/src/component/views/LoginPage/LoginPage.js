import React from 'react'
import { Row, Col } from 'antd'
import { KAKAO_AUTH_URL } from '../../../../../server/config/key'
import Logo from './img/Logo.png'
import kakaoLoginButton from './img/kakaoLoginButton.png'

function LoginPage() {
    return (
        <div style={{textAlign: 'center'}}>
            <Row>
                <Col xs={20} sm={16} md={12} lg={8} xl={4}>
                    <img src={Logo} alt='img' />
                    <h5>지금 바로 Hoppy의 서비스를 시작해보세요!</h5>
                    <br />
                    <a href={KAKAO_AUTH_URL}>
                        <img src={kakaoLoginButton} alt='img' />
                    </a>
                </Col> 
            </Row>
        </div>
    )
}

export default LoginPage