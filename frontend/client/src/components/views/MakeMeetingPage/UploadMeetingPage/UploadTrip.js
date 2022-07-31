import React, {useState} from 'react'
import {Input, Button, Icon, Form, Upload} from 'antd'
import ImageIcon from '../../LandingPage/img/trip.png'
import Axios from 'axios'

//antd 디자인 리스트 (더미db)
const fileList = [
    {
      uid: '-1',
      name: 'xxx.png',
      status: 'done',
      url: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
      thumbUrl: 'https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png',
    },
    {
      uid: '-2',
      name: 'yyy.png',
      status: 'error',
    },
  ];

function UploadTrip(props) {

    const [Title, setTitle] = useState("")
    const [FileName, setFileName] = useState("")
    const [MemberLimit, setMemberLimit] = useState("")
    const [Content, setContent] = useState("")
    const [Category, setCategory] = useState(1)

    const titleChangeHandler = (event) => {
        setTitle(event.currentTarget.value)
    }

    const fileNameChangeHandler = (event) => {
        setFileName(event.currentTarget.value)
    }

    const memberLimitChangeHandler = (event) => {
        setMemberLimit(event.currentTarget.value)
    }

    const contentChangeHandler = (event) => {
        setContent(event.currentTarget.value)
    }

    const submitHandler = (event) => {
        event.preventDefault();

        if (!Title || !FileName || !MemberLimit || !Content) {
            return alert("모든 값을 입력해주세요")
        } else if (Title.value.length > 26) {
            return alert("제목의 길이는 25자를 넘을 수 없습니다.")
        } else if (Content.value.length > 351) {
            return alert("내용의 길이는 350자를 넘을 수 없습니다.")
        }

        const body = {
            // memberId: props.user.userData.memberId
            category: Category,
            title: Title,
            content: Content,
            memberLimit: MemberLimit,
            filename: FileName,
        }

        Axios
            .post("/api/meeting", body)
            .then(response => {
                if (response.data.success) {
                    alert("모임이 생성되었습니다.")
                } else {
                    alert("모임 생성이 실패했습니다. 다시 작성해주세요.")
                }
            })
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
                <h3
                    style={{
                        textAlign: 'left',
                        marginLeft: '27px'
                    }}>취미 모임 만들기</h3>
                <Form onSubmitCapture={submitHandler}>
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
                            onChange={titleChangeHandler}
                            value={Title}
                            style={{
                                width: '295px',
                                height: '46px',
                                background: '#F0F0F0',
                                borderRadius: '14px',
                                margin: '36px 10px 0px 11px',
                                fontSize: '12px'
                            }}/>
                        {/* antd upload 라이브러리 */}
                        <Upload
                            action="https://www.mocky.io/v2/5cc8019d300000980a055e76"
                            listType="picture"
                            defaultFileList={[...fileList]}
                            refreshFunction={fileNameChangeHandler}
                            maxCount={1}
                        >
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
                        </Upload>
                        <Input
                            placeholder='정원을 입력해주세요.'
                            type="number"
                            onChange={memberLimitChangeHandler}
                            value={MemberLimit}
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
                            onChange={contentChangeHandler}
                            value={Content}
                            style={{
                                width: '349px',
                                height: '254px',
                                background: '#F0F0F0',
                                borderRadius: '14px',
                                margin: '16px 20px 0px 20px',
                                fontSize: '12px'
                            }}/>
                        <Button
                            htmlType='submit'
                            style={{
                                width: '128px',
                                height: '38px',
                                background: '#D3BA9C',
                                borderRadius: '19px',
                                color: '#464646',
                                margin: '0 auto',
                                marginTop: '30px'
                            }}>글 올리기</Button>
                    </div>
                </Form>
            </div>
        </div>
    )
}

export default UploadTrip