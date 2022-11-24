import React from 'react';
import styled from 'styled-components';
import elapsedTime from '../ElapsedTime';
function Row({ id, name, address, birth, response, stay, lastMovedTime }) {
  const ElapsedTime = elapsedTime(lastMovedTime);
  const status = ElapsedTime[0];
  let backColor;
  let fontColor;
  // eslint-disable-next-line no-unused-expressions
  status === '정상'
    ? ((backColor = '#DBF7E6'), (fontColor = '#007D50'))
    : status === '주의'
    ? ((backColor = '#FFE600'), (fontColor = '#FFAE00'))
    : status === '경보'
    ? ((backColor = '#FFE092'), (fontColor = '#FF6F06'))
    : ((backColor = '#F9B6B6'), (fontColor = '#E13737'));

  return (
    <>
      <Border>
        <td style={{ width: '5%' }}></td>
        <td style={{ width: '5%' }}>{name}</td>
        <td style={{ width: '10%' }}>{birth}</td>
        <td style={{ width: '30%' }}>{address}</td>
        <td style={{ width: '10%' }}>{response === false ? 'X' : 'O'}</td>
        <td style={{ width: '7%' }}>{stay === false ? 'X' : 'O'}</td>
        <td style={{ width: '13%' }}>{lastMovedTime.split('T').join(' ')}</td>
        <td style={{ width: '13%' }}>{ElapsedTime[1]}</td>
        <td>
          <Status
            style={{
              backgroundColor: `${backColor}`,
              borderColor: `${fontColor}`,
              color: `${fontColor}`,
            }}
          >
            {lastMovedTime === undefined ? null : status}
          </Status>
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
