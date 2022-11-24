import React from 'react';
import styled from 'styled-components';
import elapsedTime from '../ElapsedTime';
import StatusStyle from '../StatusStyle';
function Row({
  id,
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

  return (
    <>
      <Border>
        <td style={{ width: '5%' }}>{id + 1}</td>
        <td style={{ width: '5%' }}>{name}</td>
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
  padding: 1.2vmin;
  display: table;
  color: #333333;
  font-size: 1.35vmin;
`;

const Status = styled.div`
  box-sizing: border-box;
  padding: 1vmin;
  border: 1px solid black;
  text-align: center;
  font-family: 'nanum_b';
  border-radius: 8px;
`;
