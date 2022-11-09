/* eslint-disable react/jsx-pascal-case */
import React from 'react';
import styled from 'styled-components';
import Navbar from '../component/Navbar';
function Register(props) {
  return (
    <div>
      <Navbar />
      <style>{`#register {
                    filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}</style>

      <div className="container">
        <Register_Title>노인 가구 등록하기</Register_Title>
        <Register_Container>
          <Label>이름</Label>
          <Input type="text"></Input>

          <Label>나이</Label>
          <Input type="number"></Input>

          <Label>생년월일</Label>
          <Input type="date"></Input>

          <Label>핸드폰 번호</Label>
          <Input type="tel" maxlength="13" />

          <Label>주소</Label>
          <Input type="text" />

          <Button>등록하기</Button>
        </Register_Container>
      </div>
    </div>
  );
}

export default Register;

const Register_Title = styled.section`
  text-align: center;
  font-size: 1.2vmax;
  font-family: 'nanum_b';
  color: #298097;
`;

const Register_Container = styled.div`
  position: relative;
  box-sizing: border-box;
  padding: 10vmin;
  background-color: white;
  width: 50%;
  height: 80vmin;
  border-radius: 15px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Label = styled.label`
  font-family: 'nanum_l';
  margin-bottom: 1vmin;
  font-weight: 600;
`;

const Input = styled.input`
  height: 5vmin;
  margin-bottom: 3vmin;

  border: 1.3px solid rgba(196, 196, 196, 0.5);
  border-radius: 1vmin;
`;

const Button = styled.button`
  right: 0;
  width: 15vmin;
  height: 5vmin;
  background-color: #298097;
  color: white;
  border: none;
  border-radius: 0.5vmin;
  font-weight: 600;
  font-size: 0.8vmax;
  font-family: 'nanum_l';
  cursor: pointer;
  position: absolute;
  right: 10vmin;
  bottom: 5vmin;
`;
