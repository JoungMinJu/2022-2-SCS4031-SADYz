import React from 'react';
import styled from 'styled-components';
import axios from 'axios';
function Row({ id, index, time, contents, solve }) {
  const put_solveEmergency = async (status) => {
    try {
      const data = {
        emergencyNow: status,
      };
      await axios
        .put(`http://localhost:8080/api/dashboard/emergency/${id}`, data)
        .then((res) => console.log(res));
    } catch (err) {
      // console.log(err);
    }
  };
  return (
    <div>
      <Border>
        <td style={{ width: '5%' }}>{index + 1}</td>
        <td style={{ width: '25%' }}>
          {time.slice(0, 10)}
          <br />
          {time.slice(11, 19)}
        </td>
        <td style={{ width: '50%' }}>{contents}</td>
        <td>
          {' '}
          <input
            type="checkbox"
            defaultChecked={!solve}
            onClick={() => {
              put_solveEmergency(!solve);
            }}
          />
        </td>
      </Border>
    </div>
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
