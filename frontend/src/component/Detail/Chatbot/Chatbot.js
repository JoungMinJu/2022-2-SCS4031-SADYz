import React, { useEffect } from 'react';
import Calendar from 'react-calendar';
import '../../../css/calendar.css';
import moment from 'moment';
import { useState } from 'react';
import styled from 'styled-components';
import Conversation from './Conversation';
function Chatbot({ conversations }) {
  const [value, onChange] = useState(new Date());
  const [date, setDate] = useState([]);
  useEffect(() => {
    // console.log(date);
  }, [date]);
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
      </CalendarPart>
      <ContentsPart>
        {conversations &&
          conversations
            .filter((conversation) => {
              return (
                conversation.modifiedDateTime.slice(0, 10) ===
                moment(value).format('YYYY-MM-DD')
              );
            })
            .map((conversation, index) => {
              return <Conversation conversation={conversation} index={index} />;
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
  min-height: 300px;
`;
