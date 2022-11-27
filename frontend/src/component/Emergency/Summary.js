import React from 'react';
import styled from 'styled-components';
const emergency_img = `${process.env.PUBLIC_URL + '/images/emergency2.png'}`;

function Summary(props) {
  return (
    <>
      <SummaryContainer>
        <Category>
          <ImageDiv>
            <Image src={emergency_img}></Image>
          </ImageDiv>
          <Title>
            <CategoryTitle>최근 일주일 전체 응급콜 수</CategoryTitle>
            <Number>0</Number>
          </Title>
        </Category>
        <Category>
          <ImageDiv>
            <Image src={emergency_img}></Image>
          </ImageDiv>
          <Title>
            <CategoryTitle>금일 응급콜 수</CategoryTitle>
            <Number>0</Number>
          </Title>
        </Category>
        <Category>
          <ImageDiv>
            <Image src={emergency_img}></Image>
          </ImageDiv>
          <Title>
            <CategoryTitle>최근 일주일 해결 응급콜 수</CategoryTitle>
            <Number>0</Number>
          </Title>
        </Category>
        <Category>
          <ImageDiv>
            <Image src={emergency_img}></Image>
          </ImageDiv>
          <Title>
            <CategoryTitle>최근 일주일 미해결 응급콜 수</CategoryTitle>
            <Number>0</Number>
          </Title>
        </Category>
      </SummaryContainer>
    </>
  );
}

export default Summary;

const SummaryContainer = styled.div`
  display: flex;
`;

const Category = styled.div`
  display: flex;
  width: 25%;
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
  width: 20%;
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
