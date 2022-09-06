import React, { useEffect, useState } from 'react'
import { Avatar } from 'antd'
import Axios from 'axios';

function ViewUserPage(props) {

    console.log('props', props)

    const [Member, setMember] = useState([])
    
    const token = localStorage.getItem('Authorization')
    const headers = {
        Authorization: token
    }

    async function getMemberList() {
        await Axios
            .get(`https://hoppy.kro.kr/api/profile/member?id=${props.match.params.userId}`, {
                headers,
                withCredentials: false
            })
            .then(response => {
                if(response.data.status === 200) {
                    console.log('response =>', response)
                    setMember(response.data.data)
                } else {
                    alert("유저 정보를 불러오는 데 실패했습니다.")
                }
            })
    }

    useEffect(() => {
        getMemberList()
    }, [])

    console.log('member', Member)
    
    
  
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
                <Avatar
                    src={Member.profileUrl}
                    style={{
                        width: '142px',
                        height: '142px',
                        marginTop: '21px',
                        marginRight: '10px',
                        background: '#A5A5A5'
                    }}
                />
                <p style={{
                        marginTop: '16px',
                        textAlign: 'center',
                        marginLeft: '-10px'
                    }}>{Member.username}</p>
                <p
                    style={{
                        textAlign: 'left',
                        margin: '30px 0px 0px 27px',
                        fontSize: '12px',
                        color: '#858585'
                    }}>소개글</p>
                <div
                    style={{
                        width: '90%',
                        height: '117px',
                        margin: '18px 20px 0px 20px',
                        padding: '20px 24px 20px 24px',
                        fontSize: '11px',
                        color: '#464646',
                        border: '0.8px solid #A5A5A5',
                        borderRadius: '10px'
                    }}>
                    <div
                        style={{
                            display: 'block',
                            width: '100%',
                            height: '100%',
                            textAlign: 'left',
                            whiteSpace: 'pre-wrap',
                            wordBreak: 'break-all',
                            overflowY: 'auto'
                        }}>
                        {Member.intro}
                    </div>
                </div>
                <p
                    style={{
                        margin: '36px 0px 0px 30px',
                        fontSize: '12px',
                        textAlign: 'left'
                    }}>가입한 모임</p>
                <a href='/mypage/mystory'>
                    <div
                        style={{
                            color: '#858585',
                            marginLeft: '30px',
                            textAlign: 'left'
                        }}>
                        <p
                            style={{
                                margin: '18px 0px 0px 0px',
                                fontSize: '12px',
                                display: 'inline-block'
                            }}>우리 동네 풋살 모임</p>
                        <hr
                            style={{
                                width: '90%',
                                float: 'left',
                                display: 'inline-block'
                            }}/>
                    </div>
                </a>
            </div>
        </div>
    )
}

export default ViewUserPage