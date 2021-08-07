# 재귀 용법 (recursive call)
> 고급 정렬 알고리즘에서 재귀 용법을 사용하므로, 고급 정렬 알고리즘을 익히기 전에 재귀 용법을 익혀야 한다.
## 정의
- 함수 안에서 동일한 함수를 호출하는 형태
- 여러 알고리즘 작성 시 사용되므로, 익숙해져야 함

> 예제) 팩토리얼을 구하는 알고리즘을 Recursive Call을 활용해서 알고리즘 작성하기

1) 간단한 경우 생각해보기
   - 2! = 1 x 2
   - 3! = 1 x 2 x 3
   - 4! = 1 x 2 x 3 x 4
2) 규칙이 보임: n! = n x (n-1)!
   - 함수를 하나 만든다.
   - f(n) 은 n > 1 이면 return n x f(n-1)
   - f(n) 은 n = 1 이면 return n

```java
public class Factorial {
  public int factorialFunc(int n){
  	if (n>1) {
      return n*this.factorialFunc(n-1);
    } else {
      return 1;
    }
  }
}
```

- 시간 복잡도와 공간 복잡도
  - Factorial(n) 은 n-1번의 factorial() 함수를 호출하여 곱셉을 함.
    - 일종의 n-1번 반복문을 호출한 것과 동일
    - Factorical() 함수를 호출할 때마다, 지역변수 n이 생성됨
  - 시간 복잡도/공간 복잡도는 O(n-1) 이므로 결국 둘다 O(n) 이다

> 예제) 정수 n을 1, 2, 3의 합으로 나타낼 수 있는 방법의 수는?

 ```java
 public class Factorial {
   public int factorialFunc(int data) {
   	if (data == 1) {
       return 1;
     } else if ( data == 2) {
       return 2;
     } else if (data == 3) {
       return 4;
     }
     return this.factorialFunc(data-1) + this.factorialFunc(data-2) + this.factorialFunc(data-3);
   }
 }
 ```

