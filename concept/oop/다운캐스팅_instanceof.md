## 다운캐스팅 (하위 클래스로 형변환)
 - 묵시적으로 상위 클래스 형변환된 인스턴스가 원래 자료형(하위클래스)으로 변환되어야 할 때, 다운캐스팅이라 함.
 - 하위 클래스로의 형변환은 명시적으로 되어야 함
 
 ```
Customer vc = new VIPCustomer();    // 묵시적
VIPCustomer vCustomer = (VIPCustomer)vc;    // 명시적
```

```
if( vc instanceof VIPCustomer ) {
    VIPCustomer vCustomer = (VIPCustomer)vc;
}
```

* instanceof: 해당 타입의 instance가 맞는지 확인