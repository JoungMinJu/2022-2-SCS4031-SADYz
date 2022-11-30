import React from 'react';
import styled from 'styled-components';
import elapsedTime from '../Common/ElapsedTime';
import StatusStyle from '../Common/StatusStyle';
import { useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import Modal from 'react-modal';

function Row({
  id,
  index,
  name,
  address,
  birth,
  response,
  stay,
  lastMovedTime,
  ElapsedTime,
  status,
}) {
  const [backColor, fontColor] = StatusStyle(status);
  const navigate = useNavigate();
  const [status2, setStatus2] = useState(status);
  const [cnt, setCnt] = useState(0);
  useEffect(() => {
    setStatus2(elapsedTime(lastMovedTime)[0]);
  }, [ElapsedTime]);

  const [modalOpen, setModalOpen] = useState(false);
  useEffect(() => {
    if (cnt != 0 && status != '정상') setModalOpen(true);
    setCnt(cnt + 1);
  }, [status2]);

  const closeModal = () => {
    setModalOpen(false);
  };
  return (
    <>
      <Modal
        isOpen={modalOpen}
        style={ModalStyle}
        onRequestClose={() => closeModal()}
      >
        <section
          style={{
            color: '#FF6674',
            fontFamily: 'nanum_b',
            fontSize: '20px',
          }}
        >
          응급알림
        </section>
        <br />
        <section style={{ color: 'white' }}>{name}</section>
        <section style={{ color: 'white' }}>{address}</section>
        {status2 === '위험' ? (
          <section style={{ color: 'white' }}>
            24시간 이상 움직임이 없습니다.
          </section>
        ) : status2 === '경보' ? (
          <section style={{ color: 'white' }}>
            12시간 이상 움직임이 없습니다.
          </section>
        ) : status2 === '주의' ? (
          <section style={{ color: 'white' }}>
            8시간 이상 움직임이 없습니다.
          </section>
        ) : null}
        <img
          src="images/emergency3.png"
          style={{ position: 'absolute', right: '0', bottom: '0' }}
        />
      </Modal>
      <Border>
        <td
          style={{ width: '5%', cursor: 'pointer' }}
          onClick={() => {
            navigate(`/Detail/${id}`);
          }}
        >
          {index + 1}
        </td>
        <td
          style={{ width: '5%', cursor: 'pointer' }}
          onClick={() => {
            navigate(`/Detail/${id}`);
          }}
        >
          {name}
        </td>
        <td style={{ width: '10%' }}>{birth}</td>
        <td style={{ width: '30%' }}>{address}</td>
        <td style={{ width: '10%' }}>{response === false ? 'X' : 'O'}</td>
        <td style={{ width: '7%' }}>{stay === false ? 'X' : 'O'}</td>
        <td style={{ width: '13%' }}>
          {lastMovedTime === undefined
            ? '기록없음'
            : lastMovedTime.split('T').join(' ')}
        </td>
        <td style={{ width: '13%' }}>
          {lastMovedTime === undefined ? '기록없음' : ElapsedTime[1]}
        </td>
        <td>
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
        </td>
      </Border>
    </>
  );
}

export default Row;
const Border = styled.tr`
  box-sizing: border-box;
  border-top: 1px solid #f2f2f2;
  margin: 0;
  width: 100%;
  padding: 0.9vmin;
  display: table;
  color: #333333;
  font-size: 1.35vmin;
`;

const Status = styled.div`
  box-sizing: border-box;
  padding: 0.7vmin;
  border: 1px solid black;
  text-align: center;
  font-family: 'nanum_b';
  border-radius: 8px;
`;

const ModalStyle = {
  overlay: {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(255, 255, 255, 0.45)',
    zIndex: 10,
  },
  content: {
    background: '#312222',
    boxShadow: '0px 4px 4px rgba(0, 0, 0, 0.25)',
    borderRadius: '15px',
    overflow: 'auto',
    width: '500px',
    height: '150px',
  },
};
