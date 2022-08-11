
// 기본통로로 데이터가 나감, 함수명 없어도 괜찮음
// export default function (data) {
export default function getType(data) {
  return Object.prototype.toString.call(data).slice(8,-1);
}

