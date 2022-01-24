# DOM(Document Object Model) API

```js
// HTML 요소 (Element) 1개 검색/찾기
const boxEl = document.querySelector(".box");

// HTML 요소에 적용할 수 있는 메소드
boxEl.addEventListener();

// 인수(Arguments) 추가 가능
boxEl.addEventListener(1, 2);

// 1 - 이벤트(event, 상황)
boxEl.addEventListener("click", 2);

// 2 - 핸들러(Handler, 실행할 함수)
boxEl.addEventListener("click", function () {
  console.log("click");
});

// 요소의 클래스 정보 객체 활용
boxEl.classList.add("active");
let isContains = boxEl.classList.contains("active");
console.log(isContains); // true

boxEl.classList.remove("active");
isContains = boxEl.classList.contains("active");
console.log(isContains); // false
```

```js
// HTML 요소(Element) 모두 검색/찾기
const boxEls = document.querySelectorAll('.box');
console.log(boxEls);


// 찾은 요소들 반복해서 함수 실행
// 익명 함수를 인수로 추가
boxEls.forEach(function () {});

// 첫번째 매개변수(boxEl) : 반복중인 요소
// 두번째 매개변수(index) : 반복중인 번호
boxEls.forEach(function (boxEl, index) {});

boxEls.forEach(function (boxEl, index) {
  boxEl.classList.add(`order-${index + 1}`);
  console.log(index, boxEl);
})
```
```js
const boxEl = document.querySelector('.box');

// Getter
console.log(boxEl.textContent); // Box!!

// Setter
boxEl.textContent = "Content"
console.log(boxEl.textContent); // Content
```