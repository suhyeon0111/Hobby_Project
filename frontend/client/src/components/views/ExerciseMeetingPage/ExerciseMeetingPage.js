import React from "react";
import { Input, Card } from "antd";
// import CardHandler from '../CardHandler/CardHandler';

function ExerciseMeetingPage() {
  const { Search } = Input;
  const { Meta } = Card;

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
          운동 모임 리스트
          <span role="img" aria-label="exercise">
            🏃‍♂️
          </span>
        </h3>
        <div style={{ width: "90%" }}>
          {/* <Card
                    hoverable="hoverable"
                    style={{
                        width: '170px',
                        border: '1px solid #000'
                    }}
                    cover={<img alt = "example" src= />}>
                    <Meta title="Europe Street beat" description="www.instagram.com"/>
                  </Card>  */}
        </div>
      </div>
    </div>
  );
}

export default ExerciseMeetingPage;
