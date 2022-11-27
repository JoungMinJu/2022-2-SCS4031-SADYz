import React, { useEffect, useState } from 'react';
import Navbar from '../component/Navbar';
import Summary from '../component/Emergency/Summary';
import Main from '../component/Emergency/Main';
import axios from 'axios';
function Emergency(props) {
  const [emergency, setEmergency] = useState([]);

  const get_emergency = async () => {
    try {
      await axios
        .get('http://localhost:8080/api/dashboard/emergency/')
        .then((res) => {
          setEmergency(res.data);
        });
    } catch {}
  };
  useEffect(() => {
    get_emergency();
  }, []);

  useEffect(() => {
    // console.log(emergency);
  }, [emergency]);
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
        <Main key={emergency} emergency={emergency} />
      </div>
    </div>
  );
}

export default Emergency;
