import React from 'react';
import styled from 'styled-components';
import elapsedTime from '../Common/ElapsedTime';
import { useNavigate } from 'react-router-dom';
function Row({ id, index, name, address, birth, emergency }) {
  const navigate = useNavigate();
  const emergency_time = emergency.createdDateTime;
  const emergencyNow = emergency.emergencyNow;
  const emergencyContent = '응급상황 내용';
  return (
    <>
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
        <td style={{ width: '13%' }}>
          {emergency_time === undefined
            ? '기록없음'
            : emergency_time.split('T').join(' ').split('.')[0]}
        </td>
        <td style={{ width: '13%' }}>
          {emergency_time === undefined
            ? '기록없음'
            : elapsedTime(emergency_time)[1]}
        </td>
        <td style={{ width: '20%' }}>{emergencyContent}</td>
        <td>{String(emergencyNow)}</td>
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
