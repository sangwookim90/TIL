# 그래프 탐색 (Graph Search)

## BFS 와 DFS 란?

- 대표적인 그래프 탐색 알고리즘
  - 너비 우선 탐색 (Breadth First Search): 정점들과 같은 레벨에 있는 노드들을 먼저 탐색하는 방식
    - 한 단계씩 내려가면서, 해당 노드와 같은 레벨에 있는 노드들을 먼저 순회 함
  - 깊이 우선 탐색 (Depth First Search): 정점의 자식들을 먼저 탐색하는 방식
    - 한 노드의 자식을 타고 끝까지 순회한 후, 다시 돌아와서 다른 형제들의 자식을 타고 내려가며 순회 함
- HashMap 과 ArrayList 를 활용해서 그래프를 표현할 수 있음

## 너비 우선 탐색 (BFS, Breadth-First Search)

### 구현

- 자료구조 큐 활용
  - needVisit 큐와 visited 큐, 2개 사용

> Queue 구현

```java
// 그래프 구현
HashMap<String, ArrayList<String>> graph = new HashMap<String, ArrayList<String>>();
graph.put("A", new ArrayList<String>(Arrays.asList("B","C")));
graph.put("B", new ArrayList<String>(Arrays.asList("A","D")));
graph.put("C", new ArrayList<String>(Arrays.asList("A", "G", "H", "I")));

// Queue 특징 ArrayList로 표현
ArrayList<String> aList = new ArrayList<>();
aList.add("A");
aList.add("B");
// FIFO
String node = aList.remove(0);

aList.addAll(graph.get("C"));
```

> BFS 구현

```java
public class BFSSearch {
  public ArrayList<String> bfs(HashMap<String, ArrayList<String>> graph, String startNode) {
    ArrayList<String> visited = new ArrayList<>();
    ArrayList<String> needVisit = new ArrayList<>();
    
    needVisit.add(startNode);
    
    while (needVisit.size() > 0) {
      String node = needVisit.remove(0);
      
      if (!visited.contains(node)) {
        visited.add(node);
        needVisit.addAll(graph.get(node));
      }
    }
    
    return visited;
    
  }
}

```



### 시간 복잡도

- 일반적인 BFS 시간 복잡도
  - O(V+E) 	V: 노드 수, E: 간선 수

## 깊이 우선 탐색 (DFS, Depth First Search)

### 구현

- 자료구조 스택과 큐 활용
  - needVisit 스택과 visited 큐 사용

> Stack 구현

```java
// Stack 특징 ArrayList로 표현
ArrayList<String> aList = new ArrayList<>();
aList.add("A");
aList.add("B");

// pop
String node = aList.remove(aList.size()-1);
```

> DFS 구현

```java
public class DFSSearch {
  public ArrayList<String> dfs(HashMap<String, ArrayList<String>> graph, String startNode) {
    ArrayList<String> visited = new ArrayList<>();
    ArrayList<String> needVisit = new ArrayList<>();
    
    needVisit.add(startNode);
    
    while (needVisit.size() > 0) {
      String node = needVisit.remove(needVisit.size()-1);
      if(!visited.contains(node)) {
        visited.add(node);
        needVisit.addAll(graph.get(node));
      }
    }
    return visited;
  }
}
```

### 시간 복잡도

- 일반적인 BFS 시간 복잡도
  - O(V+E) 	V: 노드 수, E: 간선 수
