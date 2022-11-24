import React, { useEffect } from 'react';
import styled from 'styled-components';
import { useState } from 'react';
import Row from './Row';
function Main({ clients }) {
  const selectList = ['전체', '정상', '주의', '경보', '위험'];
  const [Selected, setSelected] = useState('전체');
  const handleSelect = (e) => {
    setSelected(e.target.value);
  };
  useEffect(() => {
    console.log(Selected);
  }, [Selected]);

  return (
    <div>
      <br />
      <Select onChange={handleSelect} value={Selected}>
        {selectList.map((item) => (
          <option value={item} key={item}>
            {item}
          </option>
        ))}
      </Select>
      <Table style={{ width: '100%' }}>
        <Thead>
          <tr>
            <th style={{ width: '5%' }}>번호</th>
            <th style={{ width: '5%' }}>이름</th>
            <th style={{ width: '10%' }}>생년월일</th>
            <th style={{ width: '30%' }}>주소</th>
            <th style={{ width: '10%' }}>금일 응답 여부</th>
            <th style={{ width: '7%' }}>출입 여부</th>
            <th style={{ width: '13%' }}>
              마지막 움직임 시간
              <br />
              (YYYY-MM-DD HH:MM:SS)
            </th>
            <th style={{ width: '13%' }}>
              마지막 움직임 시간
              <br />
              (현재 시간과의 차이)
            </th>
            <th>Status</th>
          </tr>
        </Thead>
        <Tbody>
          {clients.map((client) => {
            const lastMovedTimeArray = client.lastMovedTime;
            const length = lastMovedTimeArray.length;
            let LastMovedTime = [];
            // eslint-disable-next-line no-unused-expressions
            length === 0
              ? null
              : (LastMovedTime = lastMovedTimeArray[length - 1]);

            return (
              <Row
                key={client.id}
                id={client.id}
                name={client.name}
                address={client.address}
                birth={client.birth}
                response={client.response}
                stay={client.stay}
                lastMovedTime={
                  LastMovedTime === null ? null : LastMovedTime.lastMovedTime
                }
              />
            );
          })}
        </Tbody>
      </Table>
    </div>
  );
}

export default Main;
const Select = styled.select`
  background: #ffffff;
  mix-blend-mode: normal;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  width: 138px;
  height: 44px;
  font-family: 'nanum_b';
  font-size: 1.6vmin;
  padding-left: 1vmin;
`;

const Table = styled.table`
  margin-top: 3vmin;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  padding: 0px;
  width: 100%;
  height: 100%;
  background: #ffffff;
  border: 1px solid #f2f2f2;
  border-radius: 8px;
  flex: none;
  order: 0;
  flex-grow: 0;
  table-layout: fixed;
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
