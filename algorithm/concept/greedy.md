# 탐욕 알고리즘 (Greedy Algorithm)

## 정의

- 최적의 해에 가까운 값을 구하기 위해 사용
- 여러 경우 중 하나를 결정해야할 때마다, 매순간 최적이라고 생각되는 경우를 선택하는 방식

## 예시

### 문제 1 - 동전 문제

- 지불해야 하는 값이 4720원일 때, 1원, 50원, 100원, 500원 동전으로 동전의 수가 적게 지불되는 경우는?
  - 가장 큰 동전부터 최대한 지불해야 하는 값을 채우는 방식으로 구현 가능
  - 탐욕 알고리즘으로 매순간 최적이라고 생각되는 경우를 선택하면 됨

```java
public class Greedy {
    public void coin(int price, ArrayList<Integer> coinList) {
      int totalCoinCnt = 0;
      int coinNum = 0;
      ArrayList<Integer> details = new ArrayList<>();	      // 기록용
      
      for (int i=0; i<coinList.size(); i++) {
        coinNum = price / coinList.get(i);
        totalCoinCnt += coinNum;
        price -= coinNum * coinList.get(i);
        details.add(coinNum);
      }
    }
}
```

---

### 문제 2 - 부분 배낭 문제 (Fractional Knapsack Problem)

- 무게 제한이 k인 배낭에 최대 가치를 가지도록 문건을 넣는 문제
  - 각 물건은 무게(w)와 가치(v)로 표현
  - 물건은 쪼갤 수 있으므로, 물건의 일부분이 배낭에 넣어질 수 있음
    - Fractional Knapsack Problem 의 반대로 물건을 쪼개서 넣을 수 없는 배낭 문제도 존재 함

```java
Integer[][] objectList = {{10,10}, {15,12}, {20,10}, {25,8}, {30,5}};
```

#### 참고: 정렬 기준 정의하기

``` java
Integer[] iArray = new Integer[]{1,10,4,3,2};
Arrays.sort(iArray);

// 객체
public class Edge {
  public int distance;
  public String vertex;
  
  public Edge(int distance, String vertex) {
    this.distance = distance;
    this.vertex = vertex;
  }
  
  Edge edge1 = new Edge(10, "A");
  Edge edge2 = new Edge(12, "A");
  Edge edge3 = new Edge(13, "A");
  Edge[] edges = new Edge[](edge1, edge2, edge3);
	Arrays.sort(edges);  
  
}
```

> Comparable 과 Comparator 인터페이스

- Comparable 와 Comparator 는 둘 다 인터페이스로, 정렬 기준을 구현하기 위해 사용됨
  - Comparable 인터페이스는 compareTo() 메서드를 override 해서 구현
    - 일반적으로 정렬할 객체에 implements 로 Comparable 인터페이스를 추가하여 구현
  - Comparator 인터페이스는 compare() 메서드를 override 해서 구현
    - 일반적으로는 별도 클래스를 정의해서 구현한다. 따라서, 동일 객체에 다양한 정렬 기준을 가진 클래스를 작성 가능

```java
public class Edge implements Comparable<Edge> {
  public int distance;
  public String vertex;
  
  public Edge(int distance, String vertex) {
    this.distance = distance;
    this.vertex = vertex;
  }
  
  @Override
  public int compareTo(Edge e) {
    return this.distance - e.distance;			// 오름차순
    // return e.distance - this.distance		// 내림차순
  }
  
  Edge edge1 = new Edge(10, "A");
  Edge edge2 = new Edge(12, "A");
  Edge edge3 = new Edge(13, "A");
  Edge[] edges = new Edge[](edge1, edge2, edge3);
	Arrays.sort(edges);
}

```

> Arrays.sort() 와 Comparator

- 아래 예와 같이 Arrays.sort() 메서드는 인자를 2개 받아서 인자에 Comparator 클래스를 넣어줄 수도 있음
  - Edge 객체에 comparable 인터페이스가 정의되어 있더라도, Comparator 클래스의 정렬 기준으로 정렬이 됨

```java
public class Edge implements Comparable<Edge> {
  public int distance;
  public String vertex;
  
  public Edge(int distance, String vertex) {
    this.distance = distance;
    this.vertex = vertex;
  }
  
  @Override
  public int compareTo(Edge e) {
    return this.distance - e.distance;			// 오름차순
    // return e.distance - this.distance		// 내림차순
  }
  
  Edge edge1 = new Edge(10, "A");
  Edge edge2 = new Edge(12, "A");
  Edge edge3 = new Edge(13, "A");
  Edge[] edges = new Edge[](edge1, edge2, edge3);
	Arrays.sort(edges, new Comparator<Edge>() {
    @Override
    public int compare(Edge objectItem1, Edge objectItem2) {
      return objectItem2.distance - objectItem1.distance;
    }
  });
}
```

> 부분 배낭 문제 풀이

```java
public class Greedy {
  public void knapsack(Integer[][] objectList, double capacity) {
    double totalValue = 0.0;
    double fraction = 0.0;	// 하나의 물건을 다 못넣은 경우, 얼마나 들어갔는지?
    
    
    Arrays.sort(objectList, new Comparator<Integer[]>() {
      @Override
      public int compare(Integer[] objectItem1, Integer[] objectItem2) {
        return (objectItem2[1]/objectItem2[0]) - (objectItem1[1]/objectItem1[0]);
      }
    });
    
    for (int i=0; i<objectList.length; i++) {
      if ((capacity - (double)objectList[i][0]) > 0) {
        capacity -= (double)objectList[i][0];
        tatalValue += (double)objectList[i][1];
        
      } else {
        fraction = capacity / (double)objectList[i][0];
        totalValue += (double)objectList[i][1] * fraction;
      }
    }
  }
}
```



## 탐욕 알고리즘의 한계

- 근사치 추정에 활용
- 반드시 최적의 해를 구할 수 있지 않다.
- 최적의 해에 가까운 값을 구하는 방법 중에 하나임
