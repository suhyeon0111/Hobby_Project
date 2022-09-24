import React, { useEffect, useState } from "react";
import { Input } from "antd";
import Axios from "axios";
import InfiniteScroll from "react-infinite-scroll-component";
import TestImg from "./TestImg.jpeg";
import _default from "antd/lib/date-picker";

function ExerciseMeetingPage() {
  const { Search } = Input;

  const onSearch = (value) => console.log(value);

  // 무한스크롤
  const [MeetingList, setMeetingList] = useState([]);
  const [LastId, setLastId] = useState("");
  const [Fetching, setFetching] = useState(false);
  const [FetchData, setFetchData] = useState("");
  const [NextpageUrl, setNextpageUrl] = useState("");

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
        // setLastId(response.data.data.lastId);
        setNextpageUrl(response.data.nextPagingUrl);
      } else {
        alert("데이터 불러오기를 실패했습니다.");
      }
    });
  }

  useEffect(() => {
    getMeetingList();
  }, []);

  const meetingCard = MeetingList.map((meeting, index) => {
    console.log("meeting", meeting);
    return (
      <>
        <div key={index} style={{ width: "100%" }}>
          <img src={meeting.url} style={{ width: "30px" }} />
          {meeting.title}
        </div>
      </>
    );
  });

  const InfiniteScroll = () => {
    if (LastId !== undefined) {
      fetch(NextpageUrl, {
        method: "GET",
      }).then((response) => {
        console.log("response>>>", response);
        // setFetchData(response.data.meetingList);
      });
    }
  };

  const MoreLoad = () => {
    if (FetchData < 14) {
      setFetching(false);
    } else if (FetchData === 14) {
      setFetching(true);
    }
  };

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
        <InfiniteScroll
          dataLength={MeetingList.length} // 반복되는 컴포넌트의 개수
          next={InfiniteScroll} // 스크롤이 바닥에 닿으면 데이터를 더 불러오는 함수
          hasMore={MoreLoad} // 추가 데이터 유무
        >
          {meetingCard}
        </InfiniteScroll>
      </div>
    </div>
  );
}

export default ExerciseMeetingPage;
