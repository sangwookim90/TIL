# Lambda Expression
- 메소드를 하나의 식으로 표현

#### Method
 ```
 int min(int x, int y) {
    return x<y ? x:y;
}
 ```
 #### Lambda
```
(x,y) -> x<y ? x:y;
```

#### 익명 클래스
```
new Object() {
    int min(int x, int y) {
        return x<y ? x:y;
    }
}
```

### 불필요한 코드를 줄여주고, 가독성을 높여주는 장점

#### 문법
```
(매개변수목록) -> { 함수 몸체 }
```
