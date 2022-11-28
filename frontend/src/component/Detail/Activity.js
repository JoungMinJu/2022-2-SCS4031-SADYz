import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import elapsedTime from '../Common/ElapsedTime';

function Activity({ lastMovedTime, doorClosedTime, out }) {
  const elapsedDoor = elapsedTime(doorClosedTime)[1];
  const elapsedMove = elapsedTime(lastMovedTime)[1];
  return (
    <>
      <Door>
        <Title>외출 여부</Title>
        <Contents>{out}</Contents>
      </Door>
      <Door>
        <Title>마지막 출입 시간</Title>
        <Contents>
          {doorClosedTime === undefined ? (
            '기록없음'
          ) : (
            <>
              <section>{doorClosedTime}</section>
              <section>({elapsedDoor})</section>
            </>
          )}
        </Contents>
      </Door>
      <Movement>
        <Title>마지막 움직임 시간</Title>
        <Contents>
          {lastMovedTime === undefined ? (
            '기록없음'
          ) : (
            <>
              <section>{lastMovedTime}</section>
              <section>({elapsedMove})</section>
            </>
          )}
        </Contents>
      </Movement>
    </>
  );
}

export default Activity;
const Movement = styled.div`
  display: flex;
  flex-direction: row;
  padding: 15px;
  font-size: 15px;
`;
const Door = styled.div`
  display: flex;
  flex-direction: row;
  padding: 15px;
  font-size: 15px;
`;
const Title = styled.section`
  font-family: 'nanum_b';
  float: left;
  width: 45%;
`;

const Contents = styled.section`
  display: flex;
  flex-direction: column;
`;
