import React from 'react';
import styled from 'styled-components';
import { useState } from 'react';
function Conversation({ conversation }) {
  const conversation_list = conversation.fullText.split(',');
  const problem_bool =
    conversation.problem === null || conversation.problem === '' ? false : true;
  const emotion_bool =
    conversation.emotion === '긍정' || conversation.emotion === '부정'
      ? true
      : false;
  const [modalOpen, setModalOpen] = useState(false);

  const closeModal = () => {
    setModalOpen(false);
  };
  return (
    <>
      <OneConversation>
        <Title>대화 {conversation.id}</Title>

        {!problem_bool && !emotion_bool ? (
          <section>대화 진행중</section>
        ) : problem_bool ? (
          <section>문제점: {conversation.problem}</section>
        ) : emotion_bool ? (
          <section>감정 : {conversation.emotion}</section>
        ) : null}
        <Button
          onClick={() => {
            setModalOpen(!modalOpen);
          }}
        >
          대화 보기
        </Button>
      </OneConversation>
      {modalOpen ? (
        <Modal>
          {conversation_list.map((conv, index) =>
            index % 2 === 0 ? (
              <>
                <Chat>{conv}</Chat>
                <br />
              </>
            ) : (
              <>
                <User>{conv}</User>
                <br />
              </>
            ),
          )}
        </Modal>
      ) : null}
    </>
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

const Modal = styled.div`
  position: relative;
  background: white;
  border-radius: 15px;
  width: 500px;
  padding: 15px;
  margin: 15px;
`;

const Chat = styled.div`
  padding: 10px;
  border-radius: 15px;
  background-color: #d9d9d9;
  max-width: 80%;
  margin: 10px;
  display: inline-block;
`;
const User = styled.div`
  padding: 10px;
  border-radius: 15px;
  background-color: #dfb6b6;
  max-width: 80%;
  text-align: right;
  display: inline-block;
  margin: 10px;
  float: right;
`;
