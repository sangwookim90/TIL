# 자바스크립트 기본

## 변수

```js
// 재사용 가능!
// 변수 선언
let a = 12;
console.log(a); // 12

a = 999;
console.log(a); // 999
```

```js
// 값(데이터)의 재할당 불가!
const a = 12;
console.log(a); // 12

a = 999;
console.log(a); // TypeError: Assignment to constant variable
```

## 예약어

```js
let this = 'Hello'; // SyntaxError
let if = 123; // SyntaxError
let break = true; // SyntaxError
```

## 함수 (function)

```js
function helloFunc() {
  // 실행 코드
  console.log(1234);
}

// 함수 호출
helloFunc(); // 1234
```

```js
function returnFunc() {
  return 123;
}

let a = returnFunc();
console.log(a); // 123
```

```js
function sum(a, b) {
  return a + b;
}

let a = sum(1, 2);
let b = sum(7, 12);

console.log(a, b); // 3, 19
```

```js
function hello() {
  console.log("Hello");
}

// 익명 함수
let world = function () {
  console.log("World");
};

hello(); // Hello
world(); // World
```

```js
// 객체 데이터
const sangwoo = {
  name: "SANGWOO",
  age: 33,
  getName: function () {
    return this.name;
  },
};

const hisName = sangwoo.getName();
console.log(hisName); // SANGWOO
```

## 조건문

```js
let isShow = true;

if (isShow) {
  console.log("Show");
} else {
  console.log("Hide");
}
```

## 메소드 체이닝 (Method Chaining)

```js
const a = "Hello";
// split: 문자를 인수 기준으로 쪼개서 배열로 반환
// reverse: 배열을 뒤집기
// join: 배열을 인수 기준으로 문자로 병합해 반환

const b = a.split("").reverse().join(""); // 메소드 체이닝

console.log(a); // Hello
sonsole.log(b); // olleH
```
