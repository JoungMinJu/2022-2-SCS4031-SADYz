import elapsedTime from './ElapsedTime';
const filtermap = (client) => {
  const lastMovedTimeArray = client.lastMovedTime;
  const length = lastMovedTimeArray.length;
  let LastMovedTime = [];
  // eslint-disable-next-line no-unused-expressions
  length === 0 ? null : (LastMovedTime = lastMovedTimeArray[length - 1]);

  const lastMovedTime =
    LastMovedTime === null ? null : LastMovedTime.lastMovedTime;

  const ElapsedTime = elapsedTime(lastMovedTime);
  const status = ElapsedTime[0];
  return [lastMovedTime, ElapsedTime, status];
};

export default filtermap;
