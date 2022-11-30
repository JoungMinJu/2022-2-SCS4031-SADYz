import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import elapsedTime from '../Common/ElapsedTime';
import StatusStyle from '../Common/StatusStyle';
import axios from 'axios';
const no_profile_img = `${process.env.PUBLIC_URL + '/images/no_profile.png'}`;
const modify_img = `${process.env.PUBLIC_URL + '/images/modify.png'}`;
const trash_img = `${process.env.PUBLIC_URL + '/images/trash.png'}`;
const post_picture_img = `${
  process.env.PUBLIC_URL + '/images/post_picture.png'
}`;
function BasicInfo({
  id,
  name,
  birth,
  phonenumber,
  address,
  lastMovedTime,
  imageUrl,
}) {
  const ElapsedTime = elapsedTime(lastMovedTime);
  const status = ElapsedTime[0];
  const [backColor, fontColor] = StatusStyle(status);
  const navigate = useNavigate();
  const addImage = (e) => {
    const images = e.target.files;
    post_image(images);
  };
  const post_image = async (image) => {
    let formData = new FormData();

    formData.append('image', image[0]);

    await axios
      .post(`http://localhost:8080/api/dashboard/clients/s3/${id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then(function (res) {})
      .catch(function (err) {});
  };

  const delete_image = async () => {
    await axios
      .delete(`http://localhost:8080/api/dashboard/clients/s3/${id}`)
      .then(function (res) {})
      .catch(function (err) {});
  };
  return (
    <>
      <BasicInfoContainer>
        {imageUrl === null ? (
          <PostImage>
            <label htmlFor="input-file">
              <img
                src={post_picture_img}
                alt="사진 등록하기"
                style={{ cursor: 'pointer' }}
              />
            </label>
            <input
              type="file"
              id="input-file"
              style={{ display: 'none' }}
              accept="image/*"
              onChange={addImage}
            />
          </PostImage>
        ) : (
          <PostImage>
            <img
              src={trash_img}
              style={{ width: '25px' }}
              onClick={() => delete_image()}
            />
          </PostImage>
        )}

        <ProfileImage src={imageUrl === null ? no_profile_img : imageUrl} />
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
  height: 28vmin;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  margin-bottom: 0;
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
  bottom: 10px;
  cursor: pointer;
`;

const PostImage = styled.div`
  cursor: pointer;
  display: inline-block;
`;
