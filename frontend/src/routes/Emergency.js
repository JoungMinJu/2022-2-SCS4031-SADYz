import React, { useEffect, useState } from 'react';
import Navbar from '../component/Navbar';
import Summary from '../component/Emergency/Summary';
import Main from '../component/Emergency/Main';
import axios from 'axios';
function getToday() {
  var date = new Date();
  var year = date.getFullYear();
  var month = ('0' + (1 + date.getMonth())).slice(-2);
  var day = ('0' + date.getDate()).slice(-2);

  return year + '-' + month + '-' + day;
}
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
  }, [emergency]);

  const today_unsolved = emergency.filter((emerg) => {
    const today = getToday();
    return (
      emerg.createdDateTime.slice(0, 10) === today &&
      emerg.emergencyNow === true
    );
  });

  const unsolved = emergency.filter((emerg) => {
    return (emerg.emrgencyNow = true);
  });
  const today_emergency = emergency.filter((emerg) => {
    const today = getToday();
    return emerg.createdDateTime.slice(0, 10) === today;
  });
  return (
    <div>
      <Navbar />
      <style>
        {`#emergency {
                    filter: invert(11%) sepia(51%) saturate(890%) hue-rotate(333deg) brightness(100%) contrast(98%);`}
      </style>
      <div className="container">
        <h2>응급콜 명단</h2>
        <Summary
          key="summary"
          emergency={emergency}
          unsolved={unsolved}
          today_unsolved={today_unsolved}
          today_emergency={today_emergency}
        />
        <Main
          key={emergency}
          unsolved={unsolved}
          emergency={emergency}
          today_unsolved={today_unsolved}
          today_emergency={today_emergency}
        />
      </div>
    </div>
  );
}

export default Emergency;
