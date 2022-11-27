import React from 'react';
import Navbar from '../component/Navbar';
import Summary from '../component/Emergency/Summary';
function Emergency(props) {
  return (
    <div>
      <Navbar />
      <style>
        {`#emergency {
                    filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>
      <div className="container">
        <h2>응급콜 명단</h2>
        <Summary key="summary" />
      </div>
    </div>
  );
}

export default Emergency;
