# Method Reference
- 람다 표현식이 단 하나의 메소드만을 호출하는 경우에 해당 람다 표현식에서 불필요한 매개변수를 제거하고 사용할 수 있도록 해준다.
- 메소드 참조를 사용하면 불필요한 매개변수를 제거하고 다음과 같이 '::' 기호를 사용하여 표현할 수 있다.

#### 문법
```
클래스이름::메소드이름
또는
참조변수이름::메소드이름
```

#### 예제
```
(base, exponent) -> Math.pow(base, exponent); // Lambda 표현식
Math::pow; // Method Reference
```

#### 예제
```
MyClass obj = new MyClass;
Function<String, Boolean> func = (a) -> obj.equals(a); // 람다 표현식
Function<String, Boolean> func = obj::equals(a); // 메소드 참조
```
