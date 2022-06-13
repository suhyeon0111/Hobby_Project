import { Avatar, Icon } from 'antd'
import React from 'react'

function MyPage() {

    const Nickname = () => {
        return <>해피 쿼카</>
    }

    const Infomation = () => {
        return <div style={{textAlign: 'left', margin: '20px 0px 20px 24px'}}>
        다양한 취미 가지는 걸 좋아해요!<br />
        하피를 통해 새로운 취미 친구를 만들고 싶어요 :) <br />
        MBTI는 ENFP!
        </div>
    }

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
                <p
                    style={{
                        textAlign: 'left',
                        marginLeft: '27px',
                        fontSize: '16px'
                    }}>마이페이지</p>
                <Avatar style={{width: '142px', height: '142px', marginTop: '48px', background: '#A5A5A5'}} icon={<Icon type='user' style={{fontSize: '100px', padding: '12px'}} />} />
                <p style={{marginTop: '16px'}}>반가워요 {Nickname()}님!</p>
                <p style={{textAlign: 'left', margin: '30px 0px 0px 27px', fontSize: '12px', color: '#858585'}}>다른 사람들에게 자신을 소개해주세요!</p>
                <div style={{ wordBreak: 'break-all', width: '350px', height: '100%', margin: '18px 0px 0px 20px', fontSize: '11px', color: '#464646', border: '0.8px solid #A5A5A5', borderRadius: '10px' }}>
                    {Infomation()}
                </div>
                <p style={{ margin: '36px 0px 0px 30px', fontSize: '14px', textAlign: 'left'}}>모임</p>
                <div style={{ color: '#858585', marginLeft: '30px', textAlign: 'left'}}>
                    <p style={{ margin: '18px 0px 0px 0px', fontSize: '12px', display: 'inline-block'}}>나의 모임</p>
                    <Icon type='right' style={{marginLeft: '230px'}} />
                    <hr style={{ width: '300px', float: 'left', display: 'inline-block'}} />
                </div>
                <div style={{ color: '#858585', marginLeft: '30px', textAlign: 'left'}}>
                    <p style={{ margin: '18px 0px 0px 0px', fontSize: '12px', display: 'inline-block'}}>관심 모임 리스트</p>
                    <Icon type='right' style={{marginLeft: '190px'}} />
                    <hr style={{ width: '300px', float: 'left', display: 'inline-block'}} />
                </div>
                <p style={{ margin: '36px 0px 0px 30px', fontSize: '14px', textAlign: 'left'}}>취미 스토리</p>
                <div style={{ color: '#858585', marginLeft: '30px', textAlign: 'left'}}>
                    <p style={{ margin: '18px 0px 0px 0px', fontSize: '12px', display: 'inline-block'}}>나의 스토리</p>
                    <Icon type='right' style={{marginLeft: '215px'}} />
                    <hr style={{ width: '300px', float: 'left', display: 'inline-block'}} />
                </div>
            </div>
        </div>
  )
}

export default MyPage