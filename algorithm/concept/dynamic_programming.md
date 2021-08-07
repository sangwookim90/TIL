# 동적 계획법(Dynamic Programming)

## 정의

- 동적계획법
  - 입력 크기가 작은 부분 문제들을 해결한 후, 해당 부분 문제의 해를 활용해서 보다 큰 크기의 부분 문제를 해결하여, 전체 문제를 해결하는 알고리즘
  - 상향식 접근법으로, **가장 최하위 해답을 구한 후**, 이를 저장하고 해당 결과값을 이용해서 상위 문제를 풀어가는 방식
  - Memorization 기법 사용
    - Memorization: 프로그램 실행 시, 이전에 계산한 값을 저장하여 다시 계산하지 않도록하여 전체 실행 속도를 빠르게 하는 기술
  - 문제를 잘게 쪼갤 때, 부분 문제는 중복되어, 재활용 됨
    - 예) 피보나치 수열
- 분할 정복
  - 문제를 나눌 수 없을 때까지 나누어서 각각을 풀면서 다시 합병하여 문제의 답을 얻는 알고리즘
  - 하향식 접근법으로, **상위의 해답을 구하기 위해**, 아래로 내려가면서 하위의 해답을 구하는 방식
    - 일반적으로 재귀함수로 구현
  - 문제를 잘게 쪼갤 때, 부분 문제는 서로 중복되지 않음
    - 예) 병합 정렬, 퀵 정렬 등

## 공통점과 차이점

- 공통점
  - 문제를 잘게 쪼개서, 가장 작은 단위로 분할
- 차이점
  - 동적 계획법
    - 부분 문제는 중복되어, 상위 문제 해결 시 재활용 됨
    - Memorization 기법 사용 (부분 문제의 해답을 저장해서 재활용하는 최적화 기법으로 사용)
  - 분할 정복
    - 부분 문제는 서로 중복되지 않음
    - Memorization 기법 사용하지 않음

## 동적 계획법 알고리즘 이해

> 예) 피보나치 수열

### recursive call 활용

~~~java
public class Factorial {
  public int factorialFunc(int n){
   if (n>1) {
      return this.factorialFunc(n-1) + this.factorialFunc(n-2);
    } else {
      return n;
    }
  }
}
```
~~~

### 동적 계획법

```java
public class Dynamic {
  public int dynamicFunc(int n) {
    Integer[] cache = new Integer[n+1];
    cache[0] = 0;
    cache[1] = 1;
    for ( int i=2; i<n+1; i++) {
      cache[i] = cache[i-1] + cache[i-2];
    }
    return cache[data];
  }
}
```



