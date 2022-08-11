console.log('----------------------------------------------')
console.log('chapter 3-2 정규식 생성')
console.log('----------------------------------------------')

// regexr.com

const str32 = `
010-1234-2342
theadsaf@naver.com
https://www.naver.com
The quick brown ad fox jumps over the lazy dog.
abbcccdddd
http://www.googl.com
가나_다라
`
// 옵션 g(검색 내용만 출력), i (대소문자 구분 X)
// const regexp = new RegExp('the','gi')

console.log('----------------------------------------------')
console.log('chapter 3-3 메소드')
console.log('----------------------------------------------')

console.log('예제문자\n' + str32)

const regexp = /the/gi
console.log(str32.match(regexp))

const regexp2 = /fox/gi
console.log('검색한 단어 fox가 존재하는가 : ' + regexp.test(str32))
console.log('문자열 대체 fox -> AAA \n' + str32.replace(regexp2, 'AAA'))



console.log('----------------------------------------------')
console.log('chapter 3-4 플래그(옵션)')
console.log('----------------------------------------------')

const regexp34 = /the/
console.log('옵션 X\n' + str32.match(regexp34))
const regexp34g = /the/g
console.log('옵션 g\n' + str32.match(regexp34g))
const regexp34i = /the/gi
console.log('옵션 gi 대소문자 구분 X\n' + str32.match(regexp34i))

// 문장 끝부분에 . 이 있는 문장 검색
console.log('옵션 m 여러줄 일치\n' + str32.match(/\.$/gim))


console.log('----------------------------------------------')
console.log('chapter 3-5 패턴(표현)')
console.log('----------------------------------------------')

console.log(str32.match(/d$/gm))
console.log(str32.match(/^t/gm))
console.log('대문자 포함\n' + str32.match(/^t/gmi))
console.log('----------------------------------------------')
console.log(str32.match(/h..p/g))
console.log(str32.match(/fox|dog/))
console.log(str32.match(/https?/g)) // s가 있을수도 없을수도 있는 경우
console.log('----------------------------------------------')
console.log(str32.match(/d{2}/))
console.log(str32.match(/d{2,}/))
console.log(str32.match(/d{2,3}/))
console.log(str32.match(/\b\w{2,3}\b/g))  // /b로 경계를 만들어 2~3음절 단어 검색

console.log('----------------------------------------------')
console.log('chapter 3-6 패턴(표현) (2)')
console.log('----------------------------------------------')
console.log(str32.match(/[fox]/g))
console.log(str32.match(/[0-9]{1,}/g))
console.log(str32.match(/[가-힇]{1,}/g))
console.log('----------------------------------------------')
console.log(str32.match(/\w/g))
console.log(str32.match(/\b/g))
console.log('---------------------------\n| f로 시작하는 2글자 이상의 단어 |\n---------------------------\n')
console.log(str32.match(/\bf\w{1,}/g))  // f로 시작하는 2개 이상의 단어
console.log('---------------------\n| 모든 공백 띄어쓰기 검색 |\n---------------------\n')
console.log(str32.match(/\s/g)) // 모든 공백, 띄어쓰기 검색

const h = ` the hello  world    !`
console.log('--------------\n| 모든 공백 삭제 |\n--------------\n')
console.log(h.replace(/\s/g, '')) // 모든 공백 삭제
console.log('--------------\n| 앞쪽 일치 패턴 |\n--------------\n')
console.log(str32.match(/theadsaf(?=@)/g))
console.log('--------------\n| 뒤쪽 일치 패턴 |\n--------------\n')
console.log(str32.match(/(?<=@).{1,}/g))
console.log()



console.log()
console.log()
console.log()
console.log()
console.log()
console.log()
