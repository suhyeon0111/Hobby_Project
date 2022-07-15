import React, {useState} from 'react';
import {Menu, Modal, Button} from 'antd';

function LeftMenu(props) {

    const [IsModal, setIsModal] = useState(false)

    const showModal = () => {
        setIsModal(true);
    };

    const handleOk = () => {
        console.log('ok')
        setIsModal(false);
    };

    const handleCancel = () => {
        console.log('no')
        setIsModal(false);
    };

    return (
        <Menu mode={props.mode}>
            <Menu.Item key="mypage">
                <a href="/mypage">마이페이지</a>
            </Menu.Item>
            <Menu.Item key="exit" onClick={showModal}>
                회원 탈퇴
                <Modal
                    visible={IsModal}
                    onOk={handleOk}
                    onCancel={handleCancel}
                    centered
                    width={272}
                    footer={[
                      <Button key="back" onClick={handleCancel} style={{ width: '227px', height: '38px', borderRadius: '19px', background: '#D3BA9C', marginBottom: '8px'}}>
                        다시 생각해 볼게요
                      </Button>,
                      <Button key="exit" onClick={handleOk} style={{ width: '227px', height: '38px', borderRadius: '19px', background: '#F0F0F0', color: '#888888'}}>
                        계정 탈퇴
                      </Button>
                    ]}>
                      <div style={{ textAlign: 'center' }}>
                        <br />
                        <p>탈퇴하시겠습니까?</p>
                        <p style={{fontSize: '9px'}}>계정 탈퇴 시 모든 개인 정보가 삭제됩니다</p>
                      </div>
                </Modal>
            </Menu.Item>
        </Menu>
    )
}

export default LeftMenu