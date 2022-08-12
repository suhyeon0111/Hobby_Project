import React from "react";
import {Input} from "antd";
import Axios from "axios";

function DailyMeetingPage() {
    const {Search} = Input;

    const onSearch = (value) => console.log(value);

    const Number1 = 2;

    const onClick = (event) => {
        Axios
            .get(`https:/hoppy.kro.kr/api/meeting?categoryNumber=${Number1}`)
            .then((Response) => {
                console.log(Response);
            })
            .catch((Error) => {
                console.log(Error);
            });
    };
    return (
        <div
            style={{
                textAlign: "center",
                width: "100%"
            }}>
            <div
                style={{
                    width: "100%",
                    margin: "3rem auto"
                }}>
                <Search placeholder="찾으시는 취미를 검색해보세요!" onSearch={onSearch} style={{
                        textAlign: "center",
                        width: "90%"
                    }}
                    // enterButton
                    size="large"/>
                <h3
                    style={{
                        float: "left",
                        paddingTop: "26px",
                        fontSize: "16px",
                        marginLeft: "27px"
                    }}>
                    일상 모임 리스트
                    <span role="img" aria-label="exercise">
                        🖼
                    </span>
                    <button onClick={onClick}>확인용</button>
                </h3>
            </div>
        </div>
    );
}

export default DailyMeetingPage;