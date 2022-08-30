/* eslint-disable */

import { Button, Icon, Avatar } from 'antd'
import React, { useState, useEffect } from 'react'
import Axios from 'axios'
import moment from 'moment'

function HobbyStoryPage() {

    const [StoryList, setStoryList] = useState([])
    const [Pagination, setPagination] = useState("")

    const token = localStorage.getItem('Authorization')
    const headers = {
        Authorization: token
    }
    async function getStoryList() {
        Axios
        .get('https://hoppy.kro.kr/api/story', {
            headers,
            withCredentials: false
        })
        .then(response => {
            if(response.data.status === 200 && response.data !== undefined) {
                console.log('response.data.data', response.data.data.nextPagingUrl)
                setStoryList(response.data.data.storyList)
                setPagination(response.data.data.nextPagingUrl)
            } else {
                alert("데이터 불러오기를 실패했습니다.")
            }
        })
    }

    useEffect(() => {
        getStoryList()
    }, [])

    const storyCard = StoryList.map((story, index) => {

        console.log('story', story)

        let createDate = story.createdDate
        let datestr = createDate.substr(0, 10)
        let timestr = createDate.substr(11, 15)
        let datemoment = moment(datestr, 'YYYY-MM-DD').add(5, 'days').format('MM/DD')
        let timemoment = moment(timestr, 'h:mm::ss').format(' h:mm')
        let date = datemoment + timemoment

        const image = () => {
            if(story.filename === '') {
                return <>
                    <Avatar shape='square' size={340} style={{display: 'none'}} />
                </>
            } else {
                return <>
                    <Avatar shape='square' size={340} style={{marginBottom: '13px'}} src={story.filename} />
                </>
            }
        }

        //무한 스크롤
        //https://piaflu.tistory.com/125
        //lastId : https://hianna.tistory.com/465

        console.log(window.scrollY, document.documentElement.clientHeight, document.documentElement.scrollHeight);

        return <div
        style={{
            width: '90%',
            marginLeft: '20px'
        }}
        key={index}>
        <div>
            <Avatar
                size={27}
                src={story.profileUrl}
                style={{
                    float: 'left',
                    marginRight: '8px'
                }}/>
            <p
                style={{
                    float: 'left',
                    marginTop: '2px'
                }}>{story.username}</p>
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
                gap: '5px',
                padding: '0px 5px 0px 0px',
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
                    }}>취미 스토리</p>
                    <hr style={{width: '90%', backgroundColor: '#D3BA9C'}} />
                    <div>
                        {storyCard}
                    </div>
                    <a href='/hobbystory/upload'>
                    <Button shape='circle' style={{background: '#D3BA9C', width: '40px', height: '40px', position: 'fixed', right: 0, bottom: 0, margin: '0px 15px 50px 0px'}}>
                        <Icon type='plus' style={{color: '#fff', fontSize: '20px'}} />
                    </Button>
                    </a>
            </div>
        </div>
    )
}

export default HobbyStoryPage