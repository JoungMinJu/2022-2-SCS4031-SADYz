import React, { useEffect } from 'react';
import styled from 'styled-components';
import Activity from '../component/Detail/Activity';
import BasicInfo from '../component/Detail/BasicInfo';
import Chatbot from '../component/Detail/Chatbot';
import DetailEmergency from '../component/Detail/Detail_Emergency';
import Navbar from '../component/Navbar';
import '../css/grid.css';
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import axios from 'axios';
function Detail(props) {
  const { id } = useParams();
  const [client, setClient] = useState([]);
  const get_client_detail = async () => {
    try {
      await axios
        .get(`http://localhost:8080/api/dashboard/clients/${id}`)
        .then((res) => {
          console.log(res);
          setClient(res.data);
        });
    } catch (err) {
      console.log(err);
    }
  };
  useEffect(() => {
    get_client_detail();
  }, []);

  return (
    <>
      <Navbar />
      <style>
        {`#mainpage {
                filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>
      <div className="container">
        <h2>관리 대상 노인 가구 정보</h2>
        <Container className="detail_container">
          <div className="item title">기본 인적사항</div>
          <div className="item">
            <BasicInfo />
          </div>
          <div className="item title">챗봇</div>
          <div className="item">
            <Chatbot />
          </div>
          <div className="item title">응급콜</div>
          <div className="item">
            <DetailEmergency />
          </div>
          <div className="item title">활동정보</div>
          <div className="item">
            <Activity />
          </div>
        </Container>
      </div>
    </>
  );
}

export default Detail;

const Container = styled.div`
  background: #ffffff;
  border-radius: 20px;
  width: 100%;
  height: 92%;
`;
