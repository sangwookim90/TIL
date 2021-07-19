# Call by reference & Call by value
함수의 호출 방식에는 Call by reference 와 Call by value가 있다.

어떤 차이가 있는지 알아보겠다.

> Call by value

```java
class CallByValue{
  public static void swap(int x, int y) {
    int temp = x;
    x = y;
    y = temp;
  }
  
  public static void main(String[] args) {
    int a = 10;
    int b = 20;
    
    System.out.println("swap() 호출 전: a="+a+",b="+b);
    swap(a,b);
    System.out.println("swap() 호출 후: a="+a+",b="+b);
  }
}
```

```
swap() 호출 전: a=10, b=20
swap() 호출 후: a=10, b=20
```

swap() 메서드에 a,b를 넘겼지만, swap의 x,y와 a,b는 서로 다르기 때문에 동일한 값이 출력된다.

```
Call by value는 메소드 호출 시에 사용되는 인자의 메모리에 저장되어 있는 값(value)을 복사하여 보낸다.
```

> Call by reference

- Call by reference는 메소드 호출 시에 사용되는 인자가 값이 아닌 주소(Address)를 넘겨줌으로써, 주소를 참조(Reference)하여 데이터를 변경할 수 있다.

``` java
class CallByReference {
  int value;
  
  CallByReference(int value) {
    this value = value;
  }
  
  public static void swap(CallByReference x, CallByReference y) {
    int temp = x.value;
    x.value = y.value;
    y.value = temp;
  }
  
  public static void main(String[] args) {
	CallByReference a = new CallByReference(10);
    CallByReference b = new CallByReference(20);
    
    System.out.println("swap() 호출 전: a="+a.value+",b="+b.value);
    swap(a,b);
    System.out.println("swap() 호출 후: a="+a.value+",b="+b.value);
  }
}
```

```
swap() 호출 전: a=10, b=20
swap() 호출 후: a=20, b=10
```

```
Call by reference는 메소드 호출 시, 사용되는 인자 값의 메모리에 저장되어있는 주소를 복사하여 보낸다.
```

