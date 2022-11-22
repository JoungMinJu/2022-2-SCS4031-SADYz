import React from 'react';
import styled from 'styled-components';
import Navbar from '../component/Navbar';
import '../css/grid.css';

function Detail(props) {
  return (
    <>
      <Navbar />
      <style>
        {`#mainpage {
                filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>

      <div className="container">
        <Container className="detail_container">
          <div className="item"></div>
          <div className="item"></div>
          <div className="item"></div>
          <div className="item"></div>
          <div className="item"></div>
          <div className="item"></div>
          <div className="item"></div>
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
  height: 100%;
`;
