import React from 'react'
import {Avatar, Button, Icon, Input, Tabs, Form} from 'antd'
import TextArea from 'antd/lib/input/TextArea'
import Axios from 'axios'

function EditMyPage() {

    const Information = "다양한 취미 가지는 걸 좋아해요! \n하피를 통해 새로운 취미 친구를 만들고 싶어요 :) \nMBTI는 ENFP!"

    const username = '해피 쿼카'

    Axios.post('https://hoppy.kro.kr/api/update', (req, res) => {
        
    })

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
                <div>
                    <p
                        style={{
                            textAlign: 'left',
                            marginLeft: '27px',
                            fontSize: '16px',
                            display: 'inline-block',
                            float: 'left'
                        }}>프로필 수정</p>
                    <Button
                        style={{
                            display: 'inline-block',
                            float: 'right',
                            marginRight: '20px',
                            width: '70px',
                            height: '24px',
                            fontSize: '7px',
                            textAlign: 'center',
                            padding: '0px',
                            borderRadius: '10px',
                            background: '#D3BA9C'
                        }}>
                        수정 완료
                    </Button>
                </div>
                <Form>
                {/* 사진 수정하기
                https://velog.io/@kbing14/React-%ED%94%84%EB%A1%9C%ED%95%84-%EC%82%AC%EC%A7%84-%EC%97%85%EB%A1%9C%EB%8D%94-%EB%A7%8C%EB%93%A4%EA%B8%B0 */}
                <Avatar
                    style={{
                        width: '142px',
                        height: '142px',
                        marginTop: '48px',
                        background: '#A5A5A5'
                    }}
                    icon={<Icon type = 'user' style = {{fontSize: '100px', padding: '12px'}}/>
                    }
                />
                <Input 
                    placeholder={username}
                    style={{
                        width: '138px',
                        height: '25px',
                        textAlign: 'center',
                        marginTop: '16px',
                        borderRadius: '7px'
                    }} />
                <p
                    style={{
                        textAlign: 'left',
                        margin: '30px 0px 0px 27px',
                        fontSize: '12px',
                        color: '#858585'
                    }}>다른 사람들에게 자신을 소개해주세요!</p>
                <TextArea
                    type='textarea'
                    placeholder={Information}
                    maxLength={100}
                    style={{
                        whiteSpace: 'pre-wrap',
                        wordBreak: 'break-all',
                        width: '90%',
                        height: '117px',
                        margin: '18px 20px 0px 20px',
                        fontSize: '11px',
                        color: '#464646',
                        border: '0.8px solid #A5A5A5',
                        borderRadius: '10px',
                    }}>
                </TextArea>
                </Form>
            </div>
        </div>
    )
}

export default EditMyPage