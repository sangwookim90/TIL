# 자료형 (data type)

## String (문자 데이터)

- 따옴표 사용

```js
let myName = "Sangwoo";
let email = "sangwookim90@gmail.com";
let hello = `Hello ${myName}?!`;
```

## Number (숫자 데이터)

- 정수 및 부동소수점 숫자를 나타냄

```js
let number = 123;
let opacity = 1.56;
```

## Boolean

```js
let checked = true;
let isShow = false;
```

## Undefined

- 값이 할당되지 않은 상태를 나타냄

```js
let undef;
let obj = { abc: 123 };

console.log(undef); //undefined
console.log(obj.abc); // 123
console.log(obj.xyz); // undefined
```

## Null

```js
let empty = null;
```

## Object(객체 데이터)

- 여러 데이터를 Key:Value 형태로 저장 {}

```js
let user = {
  name: "Sangwoo",
  age: 33,
  isValid: true,
};

console.log(user.name); // Sangwoo
console.log(user.age); // 33
console.log(user.isValid); // true
```

## Array(배열 데이터)

- 여러 데이터를 순차적으로 저장

```js
let fruits = ["Apple", "Banana", "Cherry"];

console.log(fruits[0]); // Apple
```
