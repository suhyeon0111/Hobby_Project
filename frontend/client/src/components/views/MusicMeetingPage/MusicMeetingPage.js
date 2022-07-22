import React from "react";
import { Input } from "antd";

function MusicMeetingPage() {
  const { Search } = Input;

  const onSearch = (value) => console.log(value);

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
          음악 모임 리스트
          <span role="img" aria-label="music">
            🎵
          </span>
        </h3>
      </div>
    </div>
  );
}

export default MusicMeetingPage;
