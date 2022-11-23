import React from 'react';
import styled from 'styled-components';
const no_profile_img = `${process.env.PUBLIC_URL + '/images/no_profile.png'}`;
function elapsedTime(date) {
  const start = new Date(date);
  const end = new Date(); // 현재 날짜

  const diff = end - start; // 경과 시간

  const times = [
    { time: '분', milliSeconds: 1000 * 60 },
    { time: '시간', milliSeconds: 1000 * 60 * 60 },
    { time: '일', milliSeconds: 1000 * 60 * 60 * 24 },
    { time: '개월', milliSeconds: 1000 * 60 * 60 * 24 * 30 },
    { time: '년', milliSeconds: 1000 * 60 * 60 * 24 * 365 },
  ].reverse();

  // 년 단위부터 알맞는 단위 찾기
  for (const value of times) {
    const betweenTime = Math.floor(diff / value.milliSeconds);

    // 큰 단위는 0보다 작은 소수 단위 나옴
    if (betweenTime > 0) {
      return `${betweenTime}${value.time} 전`;
    }
  }

  // 모든 단위가 맞지 않을 시
  return '방금 전';
}
function BasicInfo({ id, name, birth, phonenumber, address, lastMovedTime }) {
  const today = new Date();
  const movedTime = new Date(lastMovedTime);

  const seconds = 1;
  const minute = seconds * 60;
  const hour = minute * 60;
  const day = hour * 24;

  //   const elapsedTime = Math.trunc(
  //     (today.getTime() - movedTime.getTime()) / 1000,
  //   );
  console.group(elapsedTime(movedTime));

  return (
    <>
      <BasicInfoContainer>
        <ProfileImage src={no_profile_img} />
        <Category>이름</Category>
        <Content>{name}</Content>
        <Category>생년월일</Category>
        <Content>{birth}</Content>
        <Category>핸드폰번호</Category>
        <Content>{phonenumber}</Content>
        <Category>주소</Category>
        <Content>{address}</Content>
        <Category>상태</Category>
        <Status
          style={{
            backgroundColor: '#F9B6B6',
            borderColor: '#E13737',
            color: '#E13737',
          }}
        >
          {lastMovedTime}
        </Status>
      </BasicInfoContainer>
    </>
  );
}

export default BasicInfo;
const BasicInfoContainer = styled.div`
  box-sizing: border-box;
  padding: 2vmin 5vmin;
`;

const ProfileImage = styled.img`
  width: 30vmin;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
`;

const Content = styled.section`
  font-size: 1.5vmin;
  font-family: 'nanum_l';
  font-weight: 600;
`;

const Category = styled.h4`
  margin-bottom: 1vmin;
`;

const Status = styled.div`
  box-sizing: border-box;
  padding: 1vmin;
  border: 1px solid black;
  text-align: center;
  font-family: 'nanum_b';
  border-radius: 8px;
`;
