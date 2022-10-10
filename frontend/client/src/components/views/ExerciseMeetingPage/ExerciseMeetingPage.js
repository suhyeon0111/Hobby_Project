import React, { useEffect, useState } from "react";
import { Input } from "antd";
import Axios from "axios";
import InfiniteScroll from "react-infinite-scroll-component";
import _default from "antd/lib/date-picker";

function ExerciseMeetingPage() {
  const { Search } = Input;

  const onSearch = (value) => console.log(value);

  // 무한스크롤
  const [MeetingList, setMeetingList] = useState([]);
  const [Fetching, setFetching] = useState(false);
  const [FetchData, setFetchData] = useState("");
  const [NextpagingUrl, setNextpagingUrl] = useState("");

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
        setNextpagingUrl(response.data.data.nextPagingUrl);
      } else {
        alert("데이터 불러오기를 실패했습니다.");
      }
    });
  }

  useEffect(() => {
    getMeetingList();
  }, []);

  const InfiniteScrollHandler = () => {
    // 추가 데이터 불러오는 함수
    if (NextpagingUrl == null) {
      console.log("더 이상 조회할 데이터가 없습니다.");
      setFetching(false);
    } else if (NextpagingUrl == "end") {
      console.log("더 이상 조회할 데이터가 없습니다.");
      setFetching(false);
    } else {
      // 데이터 기져오기
      Axios.get(NextpagingUrl, {
        headers,
        withCredentials: false,
      }).then((response) => {
        console.log("추가 데이터>>>>>>", response.data);
        const addData = response.data.data.meetingList;
        const mergeData = MeetingList.concat(addData);
        setMeetingList(mergeData); // 가져온 데이터
        setFetchData(addData.length); // 추가해줄 데이터 길이
        setNextpagingUrl(response.data.data.nextPagingUrl); // 데이터를 불러올 다음 url
      });
    }
  };

  const MoreLoad = () => {
    // 추가 데이터 유무 함수
    if (FetchData < 14) {
      setFetching(false);
    } else if (FetchData === 14) {
      setFetching(true);
    }
  };

  const MeetingCard = MeetingList.map((meeting, index) => {
    // 실제 보여지는 데이터
    console.log("meeting>>>>>>", meeting);
    return (
      <>
        <div
          style={{
            float: "left",
            width: "48.5%",
            height: "200px",
            border: "0.8px solid #A5A5A5",
            borderRadius: "8px",
            marginBottom: "20px",
          }}
        >
          {/* <img
              alt="example"
              src={TestImg}
              style={{ width: "90%", marginTop: "9px" }}
            /> */}
        </div>

        <div
          style={{
            float: "left",
            width: "48.5%",
            height: "200px",
            marginLeft: "3%",
            marginBottom: "20px",
            border: "0.8px solid #A5A5A5",
            borderRadius: "8px",
          }}
        >
          {/* <img
              alt="example"
              src={TestImg}
              style={{ width: "90%", marginTop: "9px" }}
            /> */}
        </div>
      </>
    );
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
            marginRight: "27px",
          }}
        >
          운동 모임 리스트
          <span role="img" aria-label="exercise">
            🏃‍♂️
          </span>
        </h3>
        {/* 모임 리스트 조회 */}
        <div style={{ width: "90%", margin: "70px auto" }}>
          <InfiniteScroll
            dataLength={MeetingList.length} // 반복되는 컴포넌트의 개수
            next={InfiniteScrollHandler} // 스크롤이 바닥에 닿으면 데이터를 더 불러오는 함수
            hasMore={MoreLoad} // 추가 데이터 유무
          >
            {MeetingCard}
          </InfiniteScroll>
        </div>
      </div>
    </div>
  );
}

export default ExerciseMeetingPage;
