import React from 'react';
import Navbar from '../component/Navbar';
function Emergency(props) {
  return (
    <div>
      <Navbar />
      <style>
        {`#emergency {
                    filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>
    </div>
  );
}

export default Emergency;
