import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import elapsedTime from '../Common/ElapsedTime';
import StatusStyle from '../Common/StatusStyle';
const no_profile_img = `${process.env.PUBLIC_URL + '/images/no_profile.png'}`;
const modify_img = `${process.env.PUBLIC_URL + '/images/modify.png'}`;

function BasicInfo({ id, name, birth, phonenumber, address, lastMovedTime }) {
  const ElapsedTime = elapsedTime(lastMovedTime);
  const status = ElapsedTime[0];
  const [backColor, fontColor] = StatusStyle(status);
  const navigate = useNavigate();
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
        {lastMovedTime === undefined ? (
          '기록없음'
        ) : (
          <Status
            style={{
              backgroundColor: `${backColor}`,
              borderColor: `${fontColor}`,
              color: `${fontColor}`,
            }}
          >
            {lastMovedTime === undefined ? '기록없음' : status}
          </Status>
        )}
        <Modify
          src={modify_img}
          onClick={() => {
            navigate(`/Detail/Modify/${id}`);
          }}
        />
      </BasicInfoContainer>
    </>
  );
}

export default BasicInfo;
const BasicInfoContainer = styled.div`
  position: relative;
  box-sizing: border-box;
  padding: 2vmin 5vmin;
  min-height: 95%;
`;

const ProfileImage = styled.img`
  width: 28vmin;
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
  width: 28vmin;
  box-sizing: border-box;
  padding: 1vmin;
  border: 1px solid black;
  text-align: center;
  font-family: 'nanum_b';
  border-radius: 8px;
`;

const Modify = styled.img`
  position: absolute;
  right: 10px;
  bottom: -15px;
  cursor: pointer;
`;
