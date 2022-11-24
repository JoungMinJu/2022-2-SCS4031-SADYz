import React from 'react';
import styled from 'styled-components';

function Main(props) {
  return (
    <div>
      <br />
      <Select>
        <option>전체</option>
        <option>정상</option>
        <option>주의</option>
        <option>경보</option>
        <option>위험</option>
      </Select>
      <Table>
        <Thead>
          <tr>
            <td></td>
            <th style={{ width: '5%' }}>번호</th>
            <th style={{ width: '5%' }}>이름</th>
            <th style={{ width: '5%' }}>나이</th>
            <th style={{ width: '40%' }}>주소</th>
            <th style={{ width: '8%' }}>금일 응답 여부</th>
            <th style={{ width: '8%' }}>출입 여부</th>
            <th style={{ width: '10%' }}>
              마지막 움직임 시간
              <br />
              (YYYY-MM-DD HH:MM:SS)
            </th>
            <th>
              마지막 움직임 시간
              <br />
              (현재 시간과의 차이)
            </th>
            <th>Status</th>
          </tr>
        </Thead>
        <tbody></tbody>
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
`;

const Thead = styled.thead`
  color: #828282;
  width: 100%;
  font-size: 1.5vmin;
`;
