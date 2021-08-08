# 탐색 (Search)

## 순차 탐색 (Sequential Search)

### 정의

- 탐색은 여러 데이터 중에서 원하는 데이터를 찾아내는 것을 의미
- 데이터가 담겨있는 리스트를 앞에서부터 하나씩 비교해서 원하는 데이터를 찾는 방법

### 알고리즘 이해 

```java
public class SequencialSearch {
  public int Search(ArrayList<Intger> dataList, Integer searchItem) {
		// 순차적으로 데이터를 하나하나 비교하여 탐색
    for (int i=0; i<dataList.size(); i++) {
      if(dataList.get(i) == searchItem) {
        return i;
      }
    }
    // 찾는 데이터가 없으면 -1 리턴
    return -1;
  }
}
```

#### 최악의 경우 리스트 길이가 n 일 때, n번 비교해야 함.  => 시간복잡도 O(n)

## 이진 탐색 (Binary Search)

### 정의

- 탐색할 자료를 둘로 나누어 해당 데이터가 있을만한 곳을 탐색하는 방법
- 정렬이 선행되어야 한다
  - Divide: 리스트를 두 개의 서브 리스트로 나눈다
  - Conquer
    - 검색할 숫자 (search) > 중간값, 뒷 부분의 서브 리스트에서 재탐색
    - 검색할 숫자 (search) < 중간값, 앞 부분의 서브 리스트에서 재탐색

### 알고리즘 이해

```java
public class BinarySearch {
	public boolean search(ArrayList<Integer> data, int item) {
    if (data.size() == 1 && item == data.get(0)) {
      return true;
    }
    if (data.size() == 1 && item != data.get(0)) {
      return false;
    }
    if (data.size() == 0) {
      return false;
    }
    
    int medium = data.size()/2;
    if(item == data.get(medium)) {
      return true;
    } else if (item < data.get(medium)) {
      return this.search(new ArrayList<Integer>(data.subList(0,medium)), item);
    } else {
      return this.search(new ArrayList<Integer>(data.subList(medium, data.size())), item);
    }  
  }  
}

```

