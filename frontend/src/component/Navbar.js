/* eslint-disable jsx-a11y/alt-text */
/* eslint-disable react/jsx-pascal-case */
import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';
function Navbar() {
  return (
    <>
      <Navbar_style>
        <section style={{ ...Menu, color: '#034b5e' }}>Menu</section>
        <NavLink to="/" style={Menu} id="mainpage">
          <img src="images/mainpage.png" style={image_style}></img>
          Mainpage
        </NavLink>
        <NavLink to="/Emergency" style={Menu} id="emergency">
          <img src="images/emergency.png" style={image_style}></img>
          응급콜
        </NavLink>
        <NavLink to="/Register" style={Menu} id="register">
          <img src="images/register.png" style={image_style}></img>
          노인가구 등록
        </NavLink>
      </Navbar_style>
    </>
  );
}

export default Navbar;

const Navbar_style = styled.div`
  box-sizing: border-box;
  position: fixed;
  background: white;
  width: 12%;
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 3vmin;
`;

const Menu = {
  fontFamily: ['poppins_m', 'nanum'],
  display: 'flex',
  flexDirection: 'row',
  justifyContent: 'middle',
  alignItems: 'center',
  position: 'relative',
  textDecoration: 'none',
  fontSize: '1.8vmin',
  top: '20%',
  color: '#C4C4C4',
  marginBottom: '4.5vmin',
  fontWeight: 600,
};

const image_style = {
  width: '3vmin',
  marginRight: '3vmin',
};
