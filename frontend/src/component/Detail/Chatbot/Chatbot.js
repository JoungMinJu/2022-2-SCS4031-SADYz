import React from 'react';
import Calendar from 'react-calendar';
import '../../../css/calendar.css';
import moment from 'moment';
import { useState } from 'react';
import styled from 'styled-components';
import Conversation from './Conversation';
function Chatbot({ conversations }) {
  const [value, onChange] = useState(new Date());
  return (
    <div>
      <CalendarPart>
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
      </CalendarPart>
      <ContentsPart>
        {conversations &&
          conversations.map((conversation) => {
            return <Conversation conversation={conversation} />;
          })}
      </ContentsPart>
    </div>
  );
}

export default Chatbot;

const CalendarPart = styled.div`
  float: left;
  width: 40%;
  padding: 15px;
`;

const ContentsPart = styled.div`
  max-height: 300px;
  padding: 15px;
  margin-left: 40%;
  overflow: scroll;
`;
