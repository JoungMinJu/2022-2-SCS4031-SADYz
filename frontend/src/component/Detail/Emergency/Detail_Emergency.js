import axios from 'axios';
import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import Row from './Row';
function DetailEmergency({ phonenumber }) {
  const [emergency, setEmergency] = useState([]);
  const get_emergency = async () => {
    try {
      axios
        .get(`http://localhost:8080/api/dashboard/emergency/${phonenumber}`)
        .then((res) => {
          setEmergency(res.data);
          // console.log(res);
        });
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    get_emergency();
  }, [emergency]);
  return (
    <div>
      <Table>
        <Thead>
          <tr>
            <th style={{ width: '5%' }}>번호</th>
            <th style={{ width: '25%' }}>발생 시간</th>
            <th style={{ width: '50%' }}>내용</th>
            <th>해결여부</th>
          </tr>
        </Thead>
        <Tbody>
          {emergency.map((emerg, index) => {
            return (
              <Row
                key={emerg.id}
                id={emerg.id}
                index={index}
                time={emerg.createdDateTime}
                contents={emerg.content}
                solve={emerg.emergencyNow}
              />
            );
          })}
        </Tbody>
      </Table>
    </div>
  );
}

export default DetailEmergency;
const Table = styled.table`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 0px;
  width: 96%;
  height: 100%;
  background: #ffffff;
  border: 1px solid #f2f2f2;
  border-radius: 8px;
  flex: none;
  order: 0;
  flex-grow: 0;
  table-layout: fixed;
  margin-left: auto;
  margin-right: auto;
  margin-top: 10px;
  overflow-y: scroll;
  max-height: 250px;
`;

const Thead = styled.thead`
  display: table;
  color: #828282;
  width: 100%;
  padding: 1vmin;
  font-size: 1.5vmin;
  box-sizing: border-box;
`;

const Tbody = styled.tbody`
  display: table;
  box-sizing: border-box;
  width: 100%;
  font-size: 1.5vmin;
  text-align: center;
`;
