import _ from 'lodash'  // From `node_modules` !

let str = ''

const ulEl = document.querySelector('ul')


for (let i = 0; i < 10; i += 1) {
  const li = document.createElement('li')
  li.textContent = `list-${i + 1}`
  if ((i + 1) % 2 === 0) {
    li.addEventListener('click', function () {
      console.log(li.textContent)
    })
  }
  ulEl.appendChild(li)
}


// 4-3

// function User (first, last) {
//   this.firstName = first
//   this.lastName = last
// }

// User.prototype.getFullName = function () {
//   return `${this.firstName} ${this.lastName}` 
// }

class User {
  constructor(first, last) {
    this.firstName = first
    this.lastName = last
  }
  getFullName() {
    return `${this.firstName} ${this.lastName}` 
  }
}


const amy = new User('Amy','Clarke')

// console.log(amy.getFullName())


// 4-4
class Vehicle{
  constructor(name, wheel) {
    this.name = name
    this.wheel = wheel
  }
}
const myVehicle = new Vehicle('운송수단',2,3)
// console.log(myVehicle) 

class Bicycle extends Vehicle {
  constructor(name, wheel) {
    super(name, wheel)
  }
}

const myBicycle = new Bicycle('삼천리',2)
// console.log(myBicycle)

class Car extends Vehicle {
  constructor(name, wheel, license) {
    super(name, wheel)
    this.license = license
  }
}

const myCar = new Car('벤츠',4, true)
// console.log(myCar)

/*************** Part 4 ***************/
/*
  JS 데이터

  String: "", '', ``
  Number
  Boolean: true, false
  undefined
  null
  Array: []
  Object: {}

*/

/*
1-1 문자
https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/String
*/

str = 'Hello world!'
console.log(str.length) // 12
console.log(str.indexOf('HEROPY') !== -1) // false
console.log(str.slice(0,2))   // He
console.log(str.slice(2))     // llo world!
console.log(str.indexOf('world')) // 6
// console.log(str.slice(6,8))
console.log(str.slice(str.indexOf('world'), 8)) //wo
console.log(str.replace('world', 'SANGWOO'))  // Hello SANGWOO!

const str2 = 'sangwookim90@gmail.com'
console.log(str2.match(/.+(?=@)/)[0])   //  sangwookim90

const str3 = '    Hello world!   '
console.log(str.trim())   // 공백문자 제거


/*
1-2 숫자와 수학
https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Math
*/

const pi = 3.14159265358979 
const str4 = pi.toFixed(2)   // 소수점 n 번째 자리까지 출력
console.log(str4)   //  3.14
console.log(typeof str4)    //  string

const integer = parseInt(str4)
const float = parseFloat(str4)
console.log(integer)
console.log(float)

// Math 
let math = Math.abs(-12)   // 12
console.log('abs: ' + math)   //  절대값
math = Math.min(2,8)  //  2
console.log('min: ' + math) //  최소값
math = Math.max(2,8)  //  8
console.log('max: ' + math) // 최대값
math = Math.ceil(3.14)  //  4
console.log('ceil: ' + math)  //  올림
math = Math.floor(3.14) //  3
console.log('floor: ' + math) // 버림
math = Math.round(3.14) //  3
console.log('round: ' + math) // 반올림
math = Math.random()
console.log('random: ' + math)  // random

/*
1-3 배열
https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Array
*/

const numbers = [1,2,3,4]
const fruits = ['Apple', 'Banana', 'Cherry']

console.log('numbers length: ' + numbers.length); // 4
console.log('numbers concat fruits: ' + numbers.concat(fruits));  // 배열 병합

// forEach
fruits.forEach(function (fruit, i, array) {
  console.log(fruit, i, array)
})

// map
//const a = fruits.forEach(function (fruit, i) {
const a = fruits.forEach((fruit,i) => {    
  console.log(`${fruit}-${i}`)
})
console.log(a)  // forEach 는 반환되는 내용이 없으므로 undefined

// const b = fruits.map(function (fruit, i) {
const b = fruits.map((fruit, i) => {  
  return `${fruit}-${i}`
})
console.log(b);   // (3) ['Apple-0', 'Banana-1', 'Cherry-2']

/*
const c = fruits.map(function (fruit, i) {
  return {
    id: i,
    name: fruit
  }
})
*/

const c = fruits.map((fruit, i) => ({      
  id: i,
  name: fruit  
}))
console.log(c);


console.log();
console.log();

// 1-4 배열(2)

/*
const d = numbers.map(number => {
  return number < 3 
})
*/
const d = numbers.map(number => number < 3)
console.log('d: ' + d)  // true true false false

/*
const e = numbers.filter(number => {
  return number < 3
})
*/
const e = numbers.filter(number => number < 3)
console.log('e: ' + e)  // 1,2

// .find() .findIndex()

/*
const f = fruits.find (fruit => {
  return /^B/.test(fruit)   // B로 시작하는 문자 
})
*/
const f = fruits.find (fruit => /^B/.test(fruit))   // B로 시작하는 문자 
console.log(f)  //  Banana

/*
const g = fruits.findIndex (fruit => {
  return /^B/.test(fruit)   // B로 시작하는 문자 
})
*/
const g = fruits.findIndex (fruit => /^B/.test(fruit))   // B로 시작하는 문자 
console.log(g)  //  1

// .includes()
const h = numbers.includes(3)
console.log(h)  //  true

// .push()  .unshift()    원본이 수정되므로 주의!
numbers.push(5) 
console.log(numbers)  //  [1,2,3,4,5]

numbers.unshift(0)
console.log(numbers)  //  [0,1,2,3,4,5]

// .reverse() .splice()   원본 수정됨
console.log('reverse: ' + numbers.reverse())  // 5,4,3,2,1,0

// splice(i,n)  i에서 n개 추출
console.log('splice: ' + numbers.splice(2,2))   //  3,2
// console.log('splice: ' + numbers.splice(2, 4, 99))   // 지우고 끼워넣기, 왜 안돼?
// console.log('splice: ' + fruits.splice(2, 0, 'Orange'))

/*
1-5 Object
https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/Object
*/

const userAge = {
  name: 'Heropy',
  age: 85
}
const userEmail = {
  name: 'Heropy',
  email: 'sangwoo11@gmail.com'
}

// assign(a,b)  --> a에 b를 추가함 
let target = Object.assign(userAge, userEmail)
console.log(target)   // {name: 'Heropy', age: 85, email: 'sangwoo11@gmail.com'}
console.log(userAge)  // {name: 'Heropy', age: 85, email: 'sangwoo11@gmail.com'}
console.log(target === userAge)   // true

target = Object.assign({}, userAge, userEmail)    // 새로운 객체를 생성하여 데이터를 넣음, 기존 데이터 손상 X
console.log(target === userAge)   //  false


let aa = { k: 123 }
let bb = { k: 123 }
console.log( a === b)

let user = {
  name: 'sangwoo',
  age: 33,
  emails: ['sangwoo@gmail.com']
}

const keys = Object.keys(user)
console.log(keys)   // (3) ['name', 'age', 'email']

console.log(user['email'])  // sangwoo@gmail.com

let values = keys.map(key => user[key])
console.log(values)   // (3) ['sangwoo', 33, 'sangwoo@gmail.com']

/*
1-6 구조 분해 할당
*/

const { name, age, email, address } = user
console.log(`사용자의 이름은 ${name} 입니다.`)
console.log(`${name} 의 나이는 ${age} 입니다.`)

const [aaa,bbb,ccc,ddd] = fruits
console.log(aaa,bbb,ccc,ddd)    //  Apple Banana Cherry undefined

/*
1-7 전개 연산자
*/
console.log(...fruits)    //  Apple Banana Cherry

/*
function toObject(q,w, ...e) {
  return {
    q: q,
    w: w,
    e: e
  }
}
*/
const toObject = (q, w, ...e) => ({q, w, e})

console.log(toObject(...fruits))    //  {q: 'Apple', w: 'Banana', e: 'Cherry'}

/*
1-8 데이터 불변성

원시 데이터: String, Number, Boolean, undefined, null
참조형 데이터 : Object, Array, Function
*/

let p = 1
let q = 4
console.log(p, q, p === q)
q = p
console.log(p, q, p === q)
p = 7
console.log(p, q, p === q)
let r = 1
console.log(q, r, q === r)

/*
1-9 얕은 복사와 깊은 복사
깊은 복사가 새로운 메모리 주소로 생성
*/

const copyUser = user
console.log(copyUser === user)      // true

user.age = 22
console.log('user', user)           //  {name: 'sangwoo', age: 22, email: 'sangwoo@gmail.com'}
console.log('copyUser', copyUser)   //  {name: 'sangwoo', age: 22, email: 'sangwoo@gmail.com'}

const copyUser2 = Object.assign({}, user)
user.age = 85
console.log('user', user)           //  {name: 'sangwoo', age: 85, email: 'sangwoo@gmail.com'}
console.log('copyUser', copyUser2)   //  {name: 'sangwoo', age: 22, email: 'sangwoo@gmail.com'}

const copyUser3 = {...user}
user.age = 22
user.emails.push('abcdefu@gmail.com')
console.log('user', user)           //  {name: 'sangwoo', age: 22, emails: Array(2)}
console.log('copyUser', copyUser3)   //  {name: 'sangwoo', age: 85, emails: Array(2)}

const copyUser4 = _.cloneDeep(user)   // 깊은 복사
user.age = 22
user.emails.push('abcdefu@gmail.com')
console.log('user', user)           //  {name: 'sangwoo', age: 22, emails: Array(3)}
console.log('copyUser', copyUser4)   //  {name: 'sangwoo', age: 22, emails: Array(2)}


console.log('----------------------------------------------')
console.log('chapter 2-1 가져오기, 내보내기 ')
console.log('----------------------------------------------')


import checkType from './getType'
// import { random, user } from './getRandom'
import { random, user as swkim } from './getRandom'

// 객체로 모든 데이터 가져오기
// import * as R from './getRandom' 
// console.log(R.usr)

console.log(_.camelCase('the hello world'))
console.log(checkType([1,2,3]))
console.log(random(), random()) 
//console.log(user)
console.log(swkim)



console.log('----------------------------------------------')
console.log('chapter 2-2 Lodash 사용법 / 중복값 제거')
console.log('----------------------------------------------')


const usersA = [
  { userId: '1', name: 'HEROPY' },
  { userId: '2', name: 'Neo' }
]

const usersB = [
  { userId: '1', name: 'HEROPY' },
  { userId: '3', name: 'Amy' }
]

const usersC = usersA.concat(usersB)
console.log('concat', usersC) // 합치기
console.log('uniqBy', _.uniqBy(usersC, 'userId')) //  배열데이터가 하나일 때

const usersD = _.unionBy(usersA, usersB, 'userId')  // 배열데이터가 여러개일 때
console.log('unionBy', usersD)

console.log('----------------------------------------------')
console.log('데이터 값, 인덱스 찾기 / 데이터 삭제')

const users = [
  { userId: '1', name: 'A' },
  { userId: '2', name: 'B' },
  { userId: '3', name: 'C' },
  { userId: '4', name: 'D' },
  { userId: '5', name: 'E' },
]

const foundUser = _.find(users, { name: 'C' })
const foundUserIndex = _.findIndex(users, { name: 'C' })
console.log(foundUser)
console.log(foundUserIndex)

_.remove(users, { name: 'A' })
console.log(users)

console.log('----------------------------------------------')
console.log('chapter 2-3 JSON')
console.log('----------------------------------------------')

// JSON 자바스크립트의 객체 표기법

const user23 = {
  name: 'SANGWOO',
  age: 33,
  emails: [
    'asdf@gmail.com',
    'asdfg@naver.com'
  ],
  'company-name': 'tonair'
}
console.log('user', user23)

const str23 = JSON.stringify(user23) // JSON -> String
console.log('str', str23)
console.log(typeof str23)

const obj23 = JSON.parse(str23) // String -> JSON
console.log('obj', obj23)

import myData from './myData'
console.log(myData)

console.log('----------------------------------------------')
console.log('chapter 2-4 Storage')
console.log('----------------------------------------------')

/*
local storage - https://developer.mozilla.org/ko/docs/Web/API/Window/localStorage

localStorage.setItem(KEY, VALUE)
localStorage.getItem(KEY)
lcoalStorage.removeItem(KEY)
*/

localStorage.setItem('user', JSON.stringify(user23))
console.log(JSON.parse(localStorage.getItem('user')))
//localStorage.removeItem('user')

// 데이터 수정
const str24 = localStorage.getItem('user')
const obj24 = JSON.parse(str24)
obj24.age = 22
console.log(obj24)
localStorage.setItem('user', JSON.stringify(obj24))

console.log('----------------------------------------------')
console.log('chapter 2-5 OMDb API')
console.log('----------------------------------------------')

// http://www.omdbapi.com/?i=tt3896198&apikey=69916ffe&s=frozen
// https://github.com/axios/axios - http client

import axios from 'axios'

function fetchMovies() {
  axios
    .get('https://www.omdbapi.com/?apikey=54457417&s=frozen')
    .then((res) => {
      console.log(res.data.Search)
      const h1El = document.querySelector('h1')
      const imgEl = document.querySelector('img')
      h1El.textContent = res.data.Search[0].Title
      imgEl.src = res.data.Search[0].Poster
    })

}

fetchMovies()

console.log()
console.log()
console.log()




console.log()
console.log()
console.log()
console.log()
