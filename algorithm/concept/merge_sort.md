# 병합 정렬(Merge sort)

## 정의

- 재귀용법을 활용한 정렬 알고리즘

  1) 리스트를 절반으로 잘라 비슷한 크기의 두 부분 리스트로 나눈다.

  2. 각 부분 리스트를 재귀적으로 병합 정렬을 이용해 정렬한다.
  3. 두 부분 리스트를 다시 하나의 정렬된 리스트로 병합한다.

## 알고리즘 이해

>  데이터가 4개일 때

- 두 단계로 분리해서 이해
  - 1단계: 정렬되지 않은 배열을 끝까지 분리하는 단계
  - 2단계: 분리한 데이터를 단계별로 합치는 단계
- 예) dataList = [1,9,3,2]
  - [1,9] , [3,2]
  - [1], [9] // 1단계
  - [1,9] // 2단계
  - [3], [2] // 1단계
  - [2,3] // 2단계
  - [1,9] , [2,3]
    - 두 배열의 맨 앞에 위치한 데이터부터 각각 비교하여 정렬된 합쳐진 배열을 작성한다.
      - 1 < 2 => [1]
      - 9 > 2 => [1,2]
      - 9 > 3 => [1,2,3]
      - 9밖에 없으니, [1,2,3,9]

## 알고리즘 구현

```java
public class MergeSort {
  public void split(ArrayList<Integer> dataList) {
    if (dataList.size() <= 1) {
      return;
    }
    
    int medium = dataList.size()/2;
    
    ArrayList<Integer> leftArr;
    ArrayList<Integer> rightArr;
    
    // 0부터 medium-1 까지 해당 배열 아이템을 서브 배열로 추출
    leftArr = new ArrayList<Integer>(dataList.subList(0, medium));
    rightArr = new ArrayList<Integer>(dataList.subList(medium, dataList.size()));
    
    System.out.println(leftArr);
    System.out.println(rightArr);
    
  }
  
}
```

### mergeSplit 메서드 만들기 (재귀용법 틀 만들기)

```java
public ArrayList<Integer> mergeSplit(ArrayList<Integer> arr) {
	// 만약 배열 갯수가 한개이면 해당 값 리턴
  if(arr.size() <= 1) {
    return arr;
  } 
  // 그렇지 않으면, 배열을 앞 뒤 2개로 나누기
  ArrayList<Integer> leftArr;
  ArrayList<Integer> rightArr;
	int medium = dataList.size()/2;
  
  leftArr = mergeSplit(new ArrayList<Integer>(arr.subList(0, medium)));
  rightArr = mergeSplit(new ArrayList<Integer>(arr.subList(medium, dataList.size())));
    
  // merge() 메서드가 leftArr 와 rightArr 를 합쳐서 정렬한 배열을 리턴한다고 가정한다면,
	// leftArr 와 righter 는 이미 정렬된 배열임을 알 수 있음
  return merge(leftArr, rightArr);
}
```



### mergeFunc 메서드 만들기

- 목표: leftList 와 rightList 의 배열 데이터를 정렬하며, 합쳐서 mergedList 라는 이름으로 return 하기
- leftList 와 rightList 는 이미 정렬된 상태 또는 데이터가 하나이다

```java
public ArrayList<Integer> merge(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
  // ArrayList 만들기
  ArrayList<Integer> mergedList = new ArrayList<>();

  int leftPoint=0;
  int rightPoint=0;

	// leftList, rightList 둘 다 있을 때
	while (leftList.size() > leftPoint && rightList.size() > rightPoint) {
    // leftPoint 나 rightPoint 가 이미 각 List 배열을 다 순회했다면, 그 반대쪽 데이터를 그대로 넣고, 해당 인덱스 1 증가  
    if (leftList.get(leftPoint) > rightList.get(rightPoint)) {
      mergedList.add(rightList.get(rightPoint));
      rightPoint += 1;
    } else {       
      mergedList.add(leftList.get(leftPoint));
      leftPoint += 1;
    }
  }
  // rightList 만 없을 때
	while (leftList.size() > leftPoint 0) {	
    mergedList.add(leftList.get(leftPoint));
    leftPoint += 1;
  }
  // leftList 만 없을 때
  while ( rightList.size() > rightPoint) {	
    mergedList.add(rightList.get(rightPoint));
    rightPoint += 1;
  }
  
  return mergedList;
}
```









