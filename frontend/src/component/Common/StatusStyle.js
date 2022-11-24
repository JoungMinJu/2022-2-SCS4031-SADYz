/* eslint-disable no-unused-expressions */
function StatusStyle(status) {
  let backColor;
  let fontColor;
  status === '정상'
    ? ((backColor = '#DBF7E6'), (fontColor = '#007D50'))
    : status === '주의'
    ? ((backColor = '#FFE600'), (fontColor = '#FFAE00'))
    : status === '경보'
    ? ((backColor = '#FFE092'), (fontColor = '#FF6F06'))
    : ((backColor = '#F9B6B6'), (fontColor = '#E13737'));
  return [backColor, fontColor];
}

export default StatusStyle;
