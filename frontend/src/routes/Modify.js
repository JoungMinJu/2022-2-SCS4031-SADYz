import React from 'react';
import Navbar from '../component/Navbar';
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';

function Modify(props) {
  const { id } = useParams();
  const [inputs, setInputs] = useState({
    name: '',
    birth: '',
    phonenumber: '',
    address: '',
  });

  const navigate = useNavigate();
  const get_client = async () => {
    try {
      await axios
        .get(`http://localhost:8080/api/dashboard/clients/${id}`)
        .then((res) => {
          setInputs({
            ...inputs,
            name: res.data.name,
            birth: res.data.birth,
            phonenumber: res.data.phonenumber,
            address: res.data.address,
          });
          console.log(inputs);
        });
    } catch (err) {
      console.log(err);
    }
  };

  const put_client = async () => {
    try {
      await axios
        .put(`http://localhost:8080/api/dashboard/clients/${id}`, inputs)
        .then((res) => {});
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    get_client();
  }, []);
  useEffect(() => {
    // console.log(inputs);
  }, [inputs]);

  const { name, birth, phonenumber, address } = inputs;

  const onChangeInput = (e) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };
  return (
    <div>
      <Navbar />
      <style>
        {`#mainpage {
                filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>
      <div className="container">
        <br />
        <ModifyTitle>노인 가구 등록하기</ModifyTitle>
        <br />
        <ModifyContainer>
          <Label>이름</Label>
          <Input
            type="text"
            name="name"
            value={name}
            onChange={onChangeInput}
          ></Input>

          <Label>생년월일</Label>
          <Input
            type="date"
            name="birth"
            value={birth}
            onChange={onChangeInput}
          ></Input>

          <Label>핸드폰 번호</Label>
          <Input
            type="tel"
            maxlength="13"
            name="phonenumber"
            value={phonenumber}
            onChange={onChangeInput}
          />

          <Label>주소</Label>
          <Input
            type="text"
            name="address"
            value={address}
            onChange={onChangeInput}
          />

          <Button
            onClick={() => {
              put_client();
              navigate('/');
            }}
          >
            등록하기
          </Button>
        </ModifyContainer>
      </div>
    </div>
  );
}

export default Modify;

const ModifyTitle = styled.section`
  text-align: center;
  font-size: 1.2vmax;
  font-family: 'nanum_b';
  color: #298097;
`;

const ModifyContainer = styled.div`
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
  font-size: 0.8vmax;
  font-family: 'nanum';
  cursor: pointer;
  position: absolute;
  right: 10vmin;
  bottom: 5vmin;
`;
