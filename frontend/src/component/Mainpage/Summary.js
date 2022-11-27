import React from 'react';
import styled from 'styled-components';
import filtermap from '../Common/FilterMap';
const senior_img = `${process.env.PUBLIC_URL + '/images/senior.png'}`;
const list_img = `${process.env.PUBLIC_URL + '/images/list.png'}`;

function Summary({ clients }) {
  const normal = clients.filter((client) => {
    const [lastMovedTime, ElapsedTime, status] = filtermap(client);
    return status === '정상';
  });

  const warn = clients.filter((client) => {
    const [lastMovedTime, ElapsedTime, status] = filtermap(client);
    return status === '주의';
  });

  const alarm = clients.filter((client) => {
    const [lastMovedTime, ElapsedTime, status] = filtermap(client);
    return status === '경보';
  });
  const danger = clients.filter((client) => {
    const [lastMovedTime, ElapsedTime, status] = filtermap(client);
    return status === '위험';
  });
  return (
    <SummaryContainer>
      <Category>
        <ImageDiv>
          <Image src={senior_img} style={{ width: '3.5vmin' }}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>노인 가구 수</CategoryTitle>
          <Number>{clients.length}</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>정상 가구 수</CategoryTitle>
          <Number>{normal.length}</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>주의 가구 수</CategoryTitle>
          <Number>{warn.length}</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>경보 가구 수</CategoryTitle>
          <Number>{alarm.length}</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>위험 가구 수</CategoryTitle>
          <Number>{danger.length}</Number>
        </Title>
      </Category>
    </SummaryContainer>
  );
}

export default Summary;

const SummaryContainer = styled.div`
  display: flex;
`;

const Category = styled.div`
  display: flex;
  width: 20%;
  height: 13vmin;
  border: 1px solid #dadada;
  border-radius: 10px;
  margin-right: 1vmin;
  background-color: white;
  box-sizing: border-box;
  padding: 3vmin;
`;

const ImageDiv = styled.div`
  display: flex;
  float: left;
  align-items: center;
  width: 25%;
  margin-right: 5%;
  height: 100%;
  background: #f4f4f4;
  border-radius: 8px;
`;

const Image = styled.img`
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  width: 2.5vmin;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
`;
const Title = styled.div`
  float: right;
  margin-top: auto;
  margin-bottom: auto;
`;

const CategoryTitle = styled.section`
  color: #828282;
`;

const Number = styled.section`
  font-family: 'nanum_b';
  font-size: 2.5vmin;
  margin-top: 1vmin;
`;
