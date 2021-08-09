# 최단 경로 알고리즘
## 정의
- 가중치 그래프(Weighted Graph)에서 간선(Edge)의 가중치 합이 최소가 되도록 하는 경로를 찾는 것이 목적
> 문제 종류
- 단일 출발(single-source) 최단 경로 문제
    - 그래프 내의 특정 노드 u에서 출발하여, 그래프 내의 모든 다른 노드에 도착하는 가장 짧은 경로를 찾는 문제
- 단일 도착(single-destination) 최단 경로 문제
    - 모든 노드들로부터 출발해서, 그래프 내의 특정 노드 u로 도착하는 가장 짧은 경로를 찾는 문제
- 단일 쌍(single-pair) 최단 경로 문제
    - 주어진 노드 u 와 v 간의 최단 경로를 찾는 문제
- 전체 쌍(all-pair) 최단 경로 문제
    - 그래프 내의 모든 노드 쌍 (u, v) 사이에 대한 최단 경로를 찾는 문제


## 다익스트라 알고리즘
- 방향이 있는 가중치 그래프
- 하나의 정점에서 다른 모든 정점에 도착하는 가장 짧은 거리를 구하는 문제
### 알고리즘 로직
- 첫 정점을 기준으로 연결되어 있는 정점들을 추가해가며, 최단 거리를 갱신하는 기법
- 너비우선탐색(BFS)와 유사
    - 첫 정점부터 각 노드간의 거리를 저장하는 배열을 만든 후, 첫 정점의 인접 노드 간의 거리부터 먼저 계산하면서, 첫 정점부터 해당 노드간의 가장 짧은 거리를 해당 배열에 업데이트
- 우선순위 큐를 활용한 다익스트라 알고리즘
    - 우선순위 큐는 MinHeap 방식을 활용해서, 현재 가장 짧은 거리를 가진 노드 정보를 먼저 꺼내면 됨
 1. 첫 정점을 기준으로 배열을 선언하여 첫 정점에서 각 정점까지의 거리를 저장
    - 초기 첫 정점의 거리는 0, 나머지는 무한대로 저장 (inf 라고 표현)
    - 우선순위 큐에 (첫 정점, 거리 0)만 먼저 넣음
2. 우선순위 큐에서 노드를 꺼냄
    - 처음에는 첫 정점만 저장되어 있으므로, 첫 정점이 꺼내짐
    - 첫 정점에 인접한 노드를 각각에 대해, 첫 정점에서 각 노드로 가는 거리와 현재 배열에 저장되어 있는 첫 정점에서 각 정점까지의 거리를 비교
    - 배열에 저장되어 있는 거리보다, 첫 정점에서 해당 노드로 가는 거리가 더 짧은 경우, 배열에 해당 노드의 거리를 업데이트
    - 배열에 해당 노드의 거리가 업데이트된 경우, 우선순위 큐에 넣는다.
        - 결과적으로 너비 우선 탐색 방식과 유사하게, 첫 정점에 인접한 노드들을 순차적으로 방문
        - 만약 배열에 기록된 현재까지 발견된 가장 짧은 거리보다, 더 긴 거리를 가진 (노드, 거리)의 경우에는 해당 노드와 인접한 노드간의 거리 계산을 하지 않음

> 우선순위 큐 (PriorityQueue)
### 장점
 - 지금까지 발견된 가장 짧은 거리의 노드에 대해서 먼저 계산
 - 더 긴거리로 계산된 루트에 대해서는 계산을 스킵할 수 있음
 ### 정렬 방법
 - 객체간의 정렬을 위한 기준 정의
 - 객체의 Comparable 인터페이스의 compareTo() 메서드 정의
 ```java
 public class Edge implements Comparable<Edge> {
	public int distance;
  public String vertex;
  
  public Edge(int distance, String vertex) {
    this.distance = distance;
    this.vertex = vertex;
  }

  @Override    
  public int compareTo(Edge edge) {
    return this.distance - edge.distance;
  }
}
 ```
> 우선순위큐 인터페이스
```java
PriorityQueue<Edge> pq = new PriorityQueue<Edge>();

// insert는 add 또는 offer 사용
pq.add(new Edge(2,"A"));
pq.add(new Edge(5,"B"));
pq.offer(new Edge(1,"C"));
pq.offer(new Edge(7,"D"));

// 최상단의 데이터 확인
pq.peek();

// 최상단의 데이터 꺼내기
pq.poll();

// 데이터 사이즈 확인
pq.size();
```
> Hashmap 에 들어 있는 모든 Key 가져오기
```java
for (String key : graph.keySet()) {
	System.out.println(key);
}
```
> Hashmap 에서 각 키에 해당하는 값, 즉 ArrayList 의 모든 Edge 객체 추출하기
```java
ArrayList<Edge> nodeList = graph.get("A");
for (int i=0; i<nodeList.size(); i++) {
	nodeList.get(i);
}
```
## 다익스트라 알고리즘 구현
```java
// <vertex, 인접한 정점>
HashMap<String, ArrayList<Edge>> graph = new HashMap<>();
graph.put("A", new ArrayList<Edge>(Arrays.asList(new Edge(8,"B"), new Edge(1,"C"), new Edge(2,"D"))));
graph.put("B", new ArrayList<Edge>());
graph.put("C", new ArrayList<Edge>(Arrays.asList(new Edge(5,"B"), new Edge(2, "D"))));
graph.put("D", new ArrayList<Edge>(Arrays.asList(new Edge(3,"E"), new Edge(5, "F"))));
graph.put("E", new ArrayList<Edge>(Arrays.asList(new Edge(1,"F"))));
graph.put("F", new ArrayList<Edge>(Arrays.asList(new Edge(5,"A"))));

public class DijkstraPath {
	public HashMap<String, Integer> dijkstar(HashMap<String, ArrayList<Edge>> graph, String start) {
    Edge edgeNode, adjacentNode;
    int currentDistance, weight, distance;
    String currentNode, adjacent;
    HashMap<String, Integer> distances = new HashMap<>();
    for (String key : graph.keySet()) {
      distances.put(key, Integer.MAX_VALUE);     // inf 설정
    }
    distances.put(start, 0);
      
    // 초기화 끝
    PriorityQueue<Edge> pq = new PriorityQueue<>();
    pq.add(new Edge(distances.get(start), start));
                
    // 알고리즘 작성
    while (pq.size() > 0) {
      edgeNode = pq.poll();
      currentDistance = edgeNode.distance;
      currentNode = edgeNode.vertex;
            
      if (distances.get(currentNode) < currentDistance) {
        continue;
	    }

      // 현재 노드에 연결되어 있는 node list
      nodeList = graph.get(currentNode);
      for (int i=0; i<nodeList.size(); i++) {
        adjacentNode = nodeList.get(i);
        adjacent = adjacentNode.vertex;
        weight = adjacentNode.distance;
        distance = currentDistance + weight;
        
        if (distance < distances.get(adjacent)) {
        	distances.put(adjacent, distance);
          pq.add(new Edge(distance, adjacent));
        }
        
      }

      return distances;
    }
}