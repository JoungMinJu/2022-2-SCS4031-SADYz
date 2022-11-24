import React from 'react';
import styled from 'styled-components';
const senior_img = `${process.env.PUBLIC_URL + '/images/senior.png'}`;
const list_img = `${process.env.PUBLIC_URL + '/images/list.png'}`;

function Summary(props) {
  return (
    <SummaryContainer>
      <Category>
        <ImageDiv>
          <Image src={senior_img} style={{ width: '3.5vmin' }}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>노인 가구 수</CategoryTitle>
          <Number>400</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>정상 가구 수</CategoryTitle>
          <Number>250</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>주의 가구 수</CategoryTitle>
          <Number>80</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>경보 가구 수</CategoryTitle>
          <Number>50</Number>
        </Title>
      </Category>
      <Category>
        <ImageDiv>
          <Image src={list_img}></Image>
        </ImageDiv>
        <Title>
          <CategoryTitle>위험 가구 수</CategoryTitle>
          <Number>20</Number>
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
`;

const CategoryTitle = styled.section`
  color: #828282;
`;

const Number = styled.section`
  font-family: 'nanum_b';
  font-size: 3vmin;
  margin-top: 1vmin;
`;
