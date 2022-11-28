import React from 'react';
import Calendar from 'react-calendar';
import '../../css/calendar.css';
import moment from 'moment';
import { useState } from 'react';
import styled from 'styled-components';
function Chatbot(props) {
  const [value, onChange] = useState(new Date());

  return (
    <>
      <Calendar_part>
        <Calendar
          onChange={onChange}
          locale="en"
          value={value}
          format={(locale, date) => date.toLocaleString('en')}
          formatMonthYear={(locale, date) =>
            date.toLocaleString('en', {
              month: 'long',
              year: 'numeric',
            })
          }
          formatDay={(locale, date) =>
            date.toLocaleString('en', {
              day: 'numeric',
            })
          }
          next2Label={null}
          prev2Label={null}
          showNeighboringMonth={false}
        />
        {/* {console.log(moment(value).format('YYYY-MM-DD'))} */}
      </Calendar_part>
      <Contents_part>d</Contents_part>
    </>
  );
}

export default Chatbot;

const Calendar_part = styled.div`
  float: left;
  width: 40%;
  padding: 15px;
`;

const Contents_part = styled.div`
  padding: 15px;
`;
