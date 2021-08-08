# 퀵 정렬 (Quick sort)

## 정의

- 기준점(pivot) 을 정해서, 기준점보다 작은 데이터는 왼쪽, 큰 데이터는 오른쪽으로 모으는 함수를 작성
  - 맨 앞 데이터를 pivot 으로 지정하는 것이 로직 상 간단
- 각 좌, 우는 재귀용법을 사용해서 다시 동일 함수를 호출하여 위 작업을 반복함
- 함수는 좌(left) + 기준점(pivot) + 우(right) 을 리턴함

## 알고리즘 이해 및 구현

> int[] data = {4,1,2,5,7};

```java
public Class QuickSort() {
  public ArrayList<Intger> sort(Integer[] arr) {
    ArrayList<Integer> data = new ArrayList<>(Arrays.asList(array));
		ArrayList<Integer> result = new ArrayList<>();
    ArrayList<Integer> leftList = new ArrayList<>();
    ArrayList<Integer> righttList = new ArrayList<>();
    
    if(data.size() <= 1) {
      return data;
    }
    
    int point = 1;
    int pivot = data.get(0);
    while(data.size() > point) {
      if(data.get(point) < p) {
        leftList.add(data.get(point));
      } else {
        rightList.add(data.get(point));
      }
      point += 1;
    } 
    result.addAll(this.sort(leftList));
    result.add(pivot);
    result.addAll(this.sort(rightList));
    
    return result;
    
  }
}
```





