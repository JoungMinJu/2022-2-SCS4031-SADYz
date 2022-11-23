import React from 'react';
import Navbar from '../component/Navbar';
function Mainpage(props) {
  return (
    <div>
      <Navbar />
      <style>
        {`#mainpage {
                    filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>
      <div className="container">
        <h2>노인 가구 명단</h2>
      </div>
    </div>
  );
}

export default Mainpage;
