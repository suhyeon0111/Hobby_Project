import React from 'react'
import {Input, Button, Icon} from 'antd'
import ImageIcon from '../../LandingPage/img/trip.png'

function UploadTrip() {
    return (
        <div
            style={{
                textAlign: 'center',
                width: '100%'
            }}>
            <div
                style={{
                    width: '100%',
                    margin: '3rem auto'
                }}>
                <h3
                    style={{
                        textAlign: 'left',
                        marginLeft: '27px'
                    }}>취미 모임 만들기</h3>
                <div>
                    <img
                        src={ImageIcon}
                        style={{
                            width: '32px',
                            height: '32px',
                            float: 'left',
                            margin: '42px 0px 0px 27px'
                        }}/>
                    <Input
                        placeholder="모임의 이름을 입력해주세요."
                        style={{
                            width: '295px',
                            height: '46px',
                            background: '#F0F0F0',
                            borderRadius: '14px',
                            margin: '36px 20px 0px 11px',
                            fontSize: '12px'
                        }}/>
                    <Button
                        style={{
                            width: '349px',
                            height: '46px',
                            background: '#F0F0F0',
                            margin: '16px 20px 0px 20px',
                            borderRadius: '14px',
                            color: '#BABABA',
                            textAlign: 'left',
                            fontSize: '12px'
                        }}>모임의 대표 이미지를 첨부해주세요.
                        <Icon
                        type='paper-clip'
                        style={{
                          float: 'right',
                          fontSize: '20px'
                        }}/>
                    </Button>
                    <Input
                        placeholder='정원을 입력해주세요.'
                        // prefix={<Icon type = 'user' />}
                        style={{
                            width: '349px',
                            height: '46px',
                            background: '#F0F0F0',
                            borderRadius: '14px',
                            margin: '16px 20px 0px 20px',
                            fontSize: '12px'
                        }}/>
                    <Input
                        placeholder='모임의 멤버를 모집하는 글을 작성해주세요.'
                        style={{
                            width: '349px',
                            height: '254px',
                            background: '#F0F0F0',
                            borderRadius: '14px',
                            margin: '16px 20px 0px 20px',
                            fontSize: '12px'
                        }}/>
                    <Button style={{width: '128px', height: '38px', background: '#D3BA9C', borderRadius: '19px', color: '#464646', margin: '0 auto', marginTop: '30px'}}>글 올리기</Button>
                </div>
            </div>
        </div>
    )
}

export default UploadTrip