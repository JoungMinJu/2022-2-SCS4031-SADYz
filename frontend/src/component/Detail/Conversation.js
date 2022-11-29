import React from 'react';
import styled from 'styled-components';

function Conversation({ conversation }) {
  const conversation_list = [conversation.fullText.split(',')];
  const problem_bool =
    conversation.problem === null || conversation.problem === '' ? false : true;
  const emotion_bool =
    conversation.emotion === '긍정' || conversation.emotion === '부정'
      ? true
      : false;
  return (
    <OneConversation>
      <Title>대화 {conversation.id}</Title>

      {!problem_bool && !emotion_bool ? (
        <section>대화 진행중</section>
      ) : problem_bool ? (
        <section>문제점: {conversation.problem}</section>
      ) : emotion_bool ? (
        <section>감정 : {conversation.emotion}</section>
      ) : null}

      <Button>대화 보기</Button>
      {/* <section>{conversation_list}</section> */}
    </OneConversation>
  );
}

export default Conversation;

const OneConversation = styled.div`
  position: relative;
  box-sizing: border-box;
  background-color: white;
  border-radius: 15px;
  padding: 15px;
  margin: 15px;
  margin-top: 0;
  height: 100%;
`;

const Title = styled.section`
  font-family: 'nanum_b';
  font-size: larger;
`;

const Button = styled.button`
  position: absolute;
  background-color: '#754E4E';
  font-family: 'nanum';
  color: white;
  right: 10px;
  bottom: 10px;
  background: #754e4e;
  border-radius: 30px;
  border: none;
  padding: 5px;
  cursor: pointer;
`;
