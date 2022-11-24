function elapsedTime(date) {
  const start = new Date(date);
  const end = new Date(); // 현재 날짜
  const diff = end - start; // 경과 시간
  const diffDay = Math.floor(diff / (1000 * 60 * 60 * 24));
  const diffHour = Math.floor((diff / (1000 * 60 * 60)) % 24);
  const diffMin = Math.floor((diff / (1000 * 60)) % 60);
  const diffSec = Math.floor((diff / 1000) % 60);
  // console.log(diffDay, diffHour, diffMin, diffSec);
  const level_category = ['정상', '주의', '경보', '위험'];
  let client_level;
  let message = '';
  if (date === undefined) client_level = null;
  else if (diffDay >= 1) {
    client_level = level_category[3];
    message = `${diffDay}일 ${diffHour}시간 ${diffMin}분 전`;
  } else if (diffHour >= 12) {
    client_level = level_category[2];
    message = `${diffHour}시간 ${diffMin}분 전`;
  } else if (diffHour >= 8) {
    client_level = level_category[1];
    message = `${diffHour}시간 ${diffMin}분 전`;
  } else {
    client_level = level_category[0];
    if (diffHour === 0) message = `${diffMin}분 전`;
    else if (diffMin === 0) message = `${diffSec}초 전`;
    else message = `${diffHour}시간 ${diffMin}분 전`;
  }
  return [client_level, message];
}

export default elapsedTime;
