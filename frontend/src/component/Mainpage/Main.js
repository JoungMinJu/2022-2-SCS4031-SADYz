import React, { useEffect } from 'react';
import styled from 'styled-components';
import { useState } from 'react';
import Row from './Row';
import elapsedTime from '../Common/ElapsedTime';
import filtermap from '../Common/FilterMap';
function Main({ clients }) {
  const selectList = ['전체', '정상', '주의', '경보', '위험'];
  const [Selected, setSelected] = useState('전체');
  const handleSelect = (e) => {
    setSelected(e.target.value);
  };
  useEffect(() => {
    // console.log(Selected);
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
            <th style={{ width: '7%' }}>외출 여부</th>
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
          {Selected === '전체'
            ? clients &&
              clients.map((client, index) => {
                const [lastMovedTime, ElapsedTime, status] = filtermap(client);

                return (
                  <Row
                    key={client.id}
                    id={client.id}
                    index={index}
                    name={client.name}
                    address={client.address}
                    birth={client.birth}
                    response={client.response}
                    stay={client.stay}
                    lastMovedTime={lastMovedTime}
                    ElapsedTime={ElapsedTime}
                    status={status}
                  />
                );
              })
            : clients &&
              clients
                .filter((client) => {
                  const [lastMovedTime, ElapsedTime, status] =
                    filtermap(client);

                  return status === Selected;
                })
                .map((client, index) => {
                  const [lastMovedTime, ElapsedTime, status] =
                    filtermap(client);

                  return (
                    <Row
                      key={client.id}
                      id={client.id}
                      index={index}
                      name={client.name}
                      address={client.address}
                      birth={client.birth}
                      response={client.response}
                      stay={client.stay}
                      lastMovedTime={lastMovedTime}
                      ElapsedTime={ElapsedTime}
                      status={status}
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
  margin-top: 2vmin;
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
  overflow-y: scroll;
  max-height: 585px;
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
