import React, { useEffect, useState } from "react";
import { Input } from "antd";
import Axios from "axios";
import InfiniteScroll from "react-infinite-scroll-component";
import TestImg from "./TestImg.jpeg";

function ExerciseMeetingPage() {
  const { Search } = Input;

  const onSearch = (value) => console.log(value);

  // 무한스크롤
  const [MeetingList, setMeetingList] = useState([]);
  const [LastId, setLastId] = useState("");
  const [Fetching, setFatching] = useState(false);
  const [FetchData, setFetchData] = useState("");

  const categoryNumber = 1; // 운동 카테고리
  const token = localStorage.getItem("Authorization");
  const headers = {
    Authorization: token,
  };

  if (token == null) {
    // 로그인 되어있는지 확인
    alert("로그인 후 이용 가능합니다.");
  }

  async function getMeetingList() {
    await Axios.get(
      `https://hoppy.kro.kr/api/meeting?categoryNumber=${categoryNumber}`,
      {
        headers,
        withCredentials: false,
      }
    ).then((response) => {
      if (response.data.status === 200 && response.data !== undefined) {
        console.log("response>>>>>", response.data.data);
        setMeetingList(response.data.data.meetingList);
        setLastId(response.data.data.lastId);
      } else {
        alert("데이터 불러오기를 실패했습니다.");
      }
    });
  }

  useEffect(() => {
    getMeetingList();
  }, []);

  const meetingCard = MeetingList.map(() => {
    console.log();
  });
  return (
    <div
      style={{
        textAlign: "center",
        width: "100%",
      }}
    >
      <div
        style={{
          width: "100%",
          margin: "3rem auto",
        }}
      >
        <Search
          placeholder="찾으시는 취미를 검색해보세요!"
          onSearch={onSearch}
          style={{
            textAlign: "center",
            width: "90%",
          }}
          // enterButton
          size="large"
        />
        <h3
          style={{
            float: "left",
            paddingTop: "26px",
            fontSize: "16px",
            marginLeft: "27px",
          }}
        >
          운동 모임 리스트
          <span role="img" aria-label="exercise">
            🏃‍♂️
          </span>
        </h3>
        {/* 모임 리스트 조회 */}
        <div
          style={{
            width: "100%",
          }}
        >
          <div
            style={{
              width: "90%",
              margin: "0 auto",
              // backgroundColor: "#A5A5A5",
              display: "flex",
            }}
          >
            <div
              style={{
                width: "48.5%",
                height: "200px",
                border: "0.8px solid #A5A5A5",
                borderRadius: "8px",
              }}
            >
              <img
                alt="example"
                src={TestImg}
                style={{ width: "90%", marginTop: "9px" }}
              />
            </div>

            <div
              style={{
                width: "48.5%",
                height: "200px",
                marginLeft: "3%",
                border: "0.8px solid #A5A5A5",
                borderRadius: "8px",
              }}
            >
              <img
                alt="example"
                src={TestImg}
                style={{ width: "90%", marginTop: "9px" }}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ExerciseMeetingPage;
