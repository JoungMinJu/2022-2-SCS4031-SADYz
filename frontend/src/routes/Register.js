import React from 'react';
import styled from 'styled-components';
import Navbar from '../component/Navbar';
function Register(props) {
  return (
    <div>
      <Navbar />
      <div className="container">
        <Register_Title>노인 가구 등록하기</Register_Title>
        <Register_Container>
          <Label_style>이름</Label_style>
          <Input_Style type="text"></Input_Style>

          <Label_style>나이</Label_style>
          <Input_Style type="number"></Input_Style>

          <Label_style>생년월일</Label_style>
          <Input_Style type="date"></Input_Style>

          <Label_style>핸드폰 번호</Label_style>
          <Input_Style type="tel" maxlength="13" />

          <Label_style>주소</Label_style>
          <Input_Style type="text" />

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

const Label_style = styled.label`
  font-family: 'nanum_l';
  margin-bottom: 1vmin;
  font-weight: 600;
`;

const Input_Style = styled.input`
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
