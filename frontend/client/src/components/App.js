import React, { Suspense } from "react";
import { Route, Switch } from "react-router-dom";
import { isBrowser, BrowserView, MoblieView } from "react-device-detect";

// import Auth from "../hoc/auth";
// pages for this product
import LandingPage from "./views/LandingPage/LandingPage.js";

import NavBar from "./views/NavBar/NavBar";
import Footer from "./views/Footer/Footer";
import NotificationPage from "./views/NotificationPage/NotificationPage";

// import LoginPage from "./views/LoginPage/LoginPage.js";
import RegisterPage from "./views/RegisterPage/RegisterPage.js";
import AuthRedirectHandler from "./views/LoginPage/KakaoLogin/AuthRedirectHandler";
import KakaoLoginPage from "./views/LoginPage/KakaoLogin/KakaoLoginPage";

import MyPage from "./views/MyPage/MyPage";
import EditMyPage from "./views/MyPage/EditMyPage.js";
import MyStoryList from "./views/MyPage/MyStoryList/MyStoryList";

import ArtMeetingPage from "./views/ArtMeetingPage/ArtMeetingPage";
import DailyMeetingPage from "./views/DailyMeetingPage/DailyMeetingPage";
import ExerciseMeetingPage from "./views/ExerciseMeetingPage/ExerciseMeetingPage";
import FoodMeetingPage from "./views/FoodMeetingPage/FoodMeetingPage";
import MusicMeetingPage from "./views/MusicMeetingPage/MusicMeetingPage";
import TripMeetingPage from "./views/TripMeetingPage/TripMeetingPage";

import HobbyStoryPage from "./views/HobbyStoryPage/HobbyStoryPage";
import MakeMeetingPage from "./views/MakeMeetingPage/MakeMeetingPage";

import UploadExercise from "./views/MakeMeetingPage/UploadMeetingPage/UploadExercise";
import UploadArt from "./views/MakeMeetingPage/UploadMeetingPage/UploadArt";
import UploadDaily from "./views/MakeMeetingPage/UploadMeetingPage/UploadDaily";
import UploadFood from "./views/MakeMeetingPage/UploadMeetingPage/UploadFood";
import UploadMusic from "./views/MakeMeetingPage/UploadMeetingPage/UploadMusic";
import UploadTrip from "./views/MakeMeetingPage/UploadMeetingPage/UploadTrip";

//null   Anyone Can go inside
//true   only logged in user can go inside
//false  logged in user can't go inside

function App() {
  if (isBrowser) {
    return (
      <div style={{ width: "100%" }}>
        <div style={{ margin: "3rem auto" }}>
          <h1 style={{ fontSize: "60px" }}>모바일 환경으로 접속해주세요</h1>
        </div>
      </div>
    );
  }
  return (
    <Suspense fallback={<div>Loading...</div>}>
      <NavBar />
      <div style={{ paddingTop: "69px", minHeight: "calc(100vh - 80px)" }}>
        <Switch>
          <Route exact path="/" component={(LandingPage)} />

          <Route exact path="/login" component={(KakaoLoginPage)} />
          <Route exact path="/login/oauth2/code/kakao" component={(AuthRedirectHandler)} />
          <Route exact path="/register" component={(RegisterPage)} />

          <Route exact path="/notification" component={(NotificationPage)} />

          {/* 마이페이지 */}
          {/* 나중에 null->true로 변경 + userId가 주소값에 할당되게 */}
          <Route exact path="/mypage" component={(MyPage)} />
          <Route exact path="/mypage/edit" component={(EditMyPage)} />
          <Route exact path="/mypage/mystory" component={(MyStoryList)} />

          {/* 취미 커뮤니티 */}
          <Route exact path="/artMeeting" component={(ArtMeetingPage)} />
          <Route exact path="/dailyMeeting" component={(DailyMeetingPage)} />
          <Route exact path="/exerciseMeeting" component={(ExerciseMeetingPage)} />
          <Route exact path="/foodMeeting" component={(FoodMeetingPage)} />
          <Route exact path="/musicMeeting" component={(MusicMeetingPage)} />
          <Route exact path="/tripMeeting" component={(TripMeetingPage)} />

          {/* 모임 초대*/}
          <Route exact path="/makeMeeting" component={(MakeMeetingPage)} />
          <Route exact path="/makeMeeting/exercise" component={(UploadExercise)} />
          <Route exact path="/makeMeeting/art" component={(UploadArt)} />
          <Route exact path="/makeMeeting/daily" component={(UploadDaily)} />
          <Route exact path="/makeMeeting/food" component={(UploadFood)} />
          <Route exact path="/makeMeeting/music" component={(UploadMusic)} />
          <Route exact path="/makeMeeting/trip" component={(UploadTrip)} />

          {/* 스토리 */}
          <Route exact path="/hobbyStory" component={(HobbyStoryPage)} />
        </Switch>
      </div>
      <Footer />
    </Suspense>
  );
}

export default App;