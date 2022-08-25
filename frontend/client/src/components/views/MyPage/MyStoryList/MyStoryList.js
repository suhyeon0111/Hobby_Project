/* eslint-disable */

import {Avatar, Icon} from 'antd'
import Axios from 'axios'
import moment from 'moment';
import React, { useState, useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { getUser } from '../../../../_actions/user_actions';

function MyStoryList() {

    const dispatch = useDispatch();
    const [StoryList, setStoryList] = useState([])
    const [UserInfo, setUserInfo] = useState([])

    const token = localStorage.getItem('Authorization')
    const headers = {
        Authorization: token
    }
    async function getStoryList() {
        Axios
        .get('https://hoppy.kro.kr/api/profile/story', {
            headers,
            withCredentials: false
        })
        .then(response => {
            if(response.data.status === 200 && response.data !== undefined) {
                setStoryList(response.data.data)
            } else {
                alert("데이터 불러오기를 실패했습니다.")
            }
        })
    }
    
    useEffect(() => {
      dispatch(getUser()).then(response => {
        setUserInfo(response.payload.data)
      })
    }, [dispatch, setUserInfo])

    useEffect(() => {
        getStoryList()
    }, [])
    

    const userName = UserInfo.username
    const userProfileUrl = UserInfo.profileUrl

    const storyCard = StoryList.map((story, index) => {

        console.log('story', story)
        // console.log('4', story.createdDate)

        let createDate = story.createdDate
        let datestr = createDate.substr(0, 10)
        let timestr = createDate.substr(11, 15)
        let datemoment = moment(datestr, 'YYYY-MM-DD').add(5, 'days').format('MM/DD')
        let timemoment = moment(timestr, 'h:mm::ss').format(' h:mm')
        let date = datemoment + timemoment

        const image = () => {
            if(story.filename === 'https://hoppyservice.s3.ap-northeast-2.amazonaws.com/') {
                return <>
                    <Avatar shape='square' size={340} style={{display: 'none'}} />
                </>
            } else {
                return <>
                    <Avatar shape='square' size={340} style={{marginBottom: '13px'}} src={story.filename} />
                </>
            }

        }

        return <div
        style={{
            width: '90%',
            marginLeft: '20px'
        }}
        key={index}>
        <div>
            <Avatar
                size={27}
                src={userProfileUrl}
                style={{
                    float: 'left',
                    marginRight: '8px'
                }}/>
            <p
                style={{
                    float: 'left',
                    marginTop: '2px'
                }}>{userName}</p>
            <p
                style={{
                    textAlign: 'right',
                    fontSize: '8px',
                    marginTop: '15px'
                }}>{date}</p>
        </div>
        <div
            style={{
                display: 'inline-block',
                width: '100%',
                whiteSpace: 'pre-wrap',
                wordBreak: 'break-all'
            }}>
            <p
                style={{
                    textAlign: 'left',
                    fontSize: '11px'
                }}>{story.title}</p>
            <p
                style={{
                    textAlign: 'left',
                    fontSize: '9px'
                }}>{story.content}</p>
                {image()}
        </div>
        <div
            style={{
                display: 'inline-flex',
                width: '100%',
                justifyContent: 'flex-end',
                alignItems: 'stretch',
                gap: '10px',
                padding: '0px 10px 0px 0px',
            }}>
            <Icon
                type='heart'
                style={{
                    fontSize: '20px'
                }}/>
            <p>1</p>
            <Icon
                type='message'
                style={{
                    fontSize: '20px'
                }}/>
            <p>1</p>
        </div>
        <div
            style={{
                width: '100%',
                height: '8px',
                margin: '5px 0px 10px 0px',
                background: '#F1E3D2'
            }}/>
    </div>
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
                <p
                    style={{
                        textAlign: 'left',
                        marginLeft: '27px',
                        fontSize: '16px'
                    }}>나의 스토리</p>
                <hr
                    style={{
                        width: '90%',
                        backgroundColor: '#D3BA9C'
                    }}/>
                <div>
                    {storyCard}
                </div>
            </div>
        </div>
    )
}

export default MyStoryList