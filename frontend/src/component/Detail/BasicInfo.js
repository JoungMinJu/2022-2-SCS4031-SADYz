import React, { useEffect } from 'react';
import styled from 'styled-components';
import elapsedTime from '../ElapsedTime';
import StatusStyle from '../StatusStyle';
const no_profile_img = `${process.env.PUBLIC_URL + '/images/no_profile.png'}`;

function BasicInfo({ id, name, birth, phonenumber, address, lastMovedTime }) {
  const ElapsedTime = elapsedTime(lastMovedTime);
  const status = ElapsedTime[0];
  const [backColor, fontColor] = StatusStyle(status);

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
            backgroundColor: `${backColor}`,
            borderColor: `${fontColor}`,
            color: `${fontColor}`,
          }}
        >
          {lastMovedTime === undefined ? null : status}
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
