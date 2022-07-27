import React from "react";
// import { FaCode } from "react-icons/fa";
import { Button, Input } from "antd";
import { BrowerView, MoblieView } from "react-device-detect";
import Axios from "axios";

import CharacterImg from "./img/character.png";
import ArtImg from "./img/art.png";
import DailyImg from "./img/daily.png";
import ExerciseImg from "./img/exercise.png";
import FoodImg from "./img/food.png";
import HobbyStoryImg from "./img/hobbyStory.png";
import MusicImg from "./img/music.png";
import TripImg from "./img/trip.png";

function LandingPage(props) {
  const { Search } = Input;

  const onSearch = (value) => console.log(value);
  console.log('location', props.location)
  const param = new URLSearchParams(props.location.search);
  console.log('jwtToken>>>>>', param.get("token"))
  const jwtToken = param.get("token");

  if (jwtToken !== undefined) {
    localStorage.setItem('Authorization', `Bearer ${jwtToken}`);
    Axios.defaults.headers.common['Authorization'] = `Bearer ${jwtToken}`;
  } else {
    localStorage.removeItem('Authorization', `Bearer ${jwtToken}`);
    delete Axios.defaults.headers.common['Authorization'];
  }

  const hobbyBoxStyle = {
    width: "108px",
    height: "108px",
    border: "solid 0.8px #A5A5A5",
    borderRadius: "15px",
    display: "inline-block",
    margin: "7px",
    padding: "10px",
  };

  const hobbyImageStyle = {
    width: "62px",
    height: "62px",
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

        <h4
          style={{
            textAlign: "left",
            color: "#787878",
            paddingTop: "30px",
            marginLeft: "27px",
          }}
        >
          취미 카테고리
        </h4>

        <a
          href="/exerciseMeeting"
          style={{ textDecoration: "none", color: "#000" }}
        >
          <div style={hobbyBoxStyle}>
            운동
            <img src={ExerciseImg} style={hobbyImageStyle} />
          </div>
        </a>
        <a href="/artMeeting" style={{ textDecoration: "none", color: "#000" }}>
          <div style={hobbyBoxStyle}>
            공예
            <img src={ArtImg} style={hobbyImageStyle} />
          </div>
        </a>
        <a
          href="/musicMeeting"
          style={{ textDecoration: "none", color: "#000" }}
        >
          <div style={hobbyBoxStyle}>
            음악
            <img src={MusicImg} style={hobbyImageStyle} />
          </div>
        </a>
        <a
          href="/dailyMeeting"
          style={{ textDecoration: "none", color: "#000" }}
        >
          <div style={hobbyBoxStyle}>
            일상
            <img src={DailyImg} style={hobbyImageStyle} />
          </div>
        </a>
        <a
          href="/foodMeeting"
          style={{ textDecoration: "none", color: "#000" }}
        >
          <div style={hobbyBoxStyle}>
            음식
            <img src={FoodImg} style={hobbyImageStyle} />
          </div>
        </a>
        <a
          href="/tripMeeting"
          style={{ textDecoration: "none", color: "#000" }}
        >
          <div style={hobbyBoxStyle}>
            여행
            <img src={TripImg} style={hobbyImageStyle} />
          </div>
        </a>

        <h4
          style={{
            padding: "35px 0px 10px 0px",
            marginLeft: "27px",
            textAlign: "left",
            color: "#787878",
          }}
        >
          취미를 함께 할 사람들을 찾아보세요
        </h4>
        <div
          style={{
            border: "0.8px solid #A5A5A5",
            width: "350px",
            height: "144px",
            borderRadius: "10px",
            margin: "0px auto",
          }}
        >
          <div
            style={{
              float: "left",
              display: "inline-block",
              paddingTop: "28px",
              paddingLeft: "36px",
            }}
          >
            <p
              style={{
                fontSize: "19px",
                color: "#988165",
              }}
            >
              취미 모임
            </p>
            <Button
              href="/makeMeeting"
              style={{
                width: "128px",
                height: "38px",
                background: "#D3BA9C",
                borderRadius: "19px",
                textDecoration: "none",
                color: "#000",
              }}
            >
              모임 만들기 ▷
            </Button>
          </div>
          <img
            src={CharacterImg}
            style={{
              width: "126px",
              height: "131px",
              display: "inline-block",
            }}
          />
        </div>

        <h4
          style={{
            padding: "35px 0px 10px 0px",
            marginLeft: "27px",
            textAlign: "left",
            color: "#787878",
          }}
        >
          취미를 기록하고 공유해보세요
        </h4>
        <div
          style={{
            border: "0.8px solid #A5A5A5",
            width: "350px",
            height: "144px",
            borderRadius: "10px",
            margin: "0px auto",
          }}
        >
          <div
            style={{
              float: "left",
              display: "inline-block",
              paddingTop: "28px",
              paddingLeft: "36px",
            }}
          >
            <p
              style={{
                fontSize: "19px",
                color: "#988165",
              }}
            >
              일상을 기록하는
            </p>
            <Button
              href="/hobbyStory"
              style={{
                width: "128px",
                height: "38px",
                background: "#D3BA9C",
                borderRadius: "19px",
                textDecoration: "none",
                color: "#000",
              }}
            >
              취미 스토리 ▷
            </Button>
          </div>
          <img
            src={HobbyStoryImg}
            style={{
              width: "126px",
              height: "131px",
              display: "inline-block",
            }}
          />
        </div>
      </div>
    </div>
  );
}

export default LandingPage;
