# CSS-Selector

## 기본 선택자

### 전체 선택자 \* (Universal Selector) : 모든 요소를 선택

```css
* { color: red; }
```

### 태그 선택자 (Type Selector)

```css
li { color: red; }
```

### 클래스 선택자 (Class Selector)

```css
.orange { color: red; }
```

### 아이디 선택자 (ID Selector)

```css
#orange { color: red; }
```



## 연결 선택자 (combination selector)

> 선택자와 선택자를 연결해 적용 대상을 한정하는 선택자

### 일치 선택자 (Basic Combinator) : 선택자 2개를 동시에 만족하는 요소 선택

```css
/* span 태그와 orange 클래스를 만족하는 요소 */
span.orange { color: red; }
```



### 자식 선택자 (Child Combinator) : 선택자 A의 자식요소 B 선택

- 자식 요소에 스타일을 적용하는 선택자
- 두 요소 사이에 '>' 를 표시해 부모 요소와 자식 요소를 구분

```css
ul > .orange { color: red; }
```

```html
<ul>
  <li class="orange">오렌지</li>
</ul>
```



### 하위 선택자 (Descendant Combinator)

- 부모 요소에 포함된 **모든 하위 요소**에 스타일 적용
- 하위 선택자를 정의할 때는 상위 요소와 하위 요소를 나란히 쓴다.

```css
div .orange {
  color: red;
}
```

```html
<div>
  <ul>
    <li class="orange">오렌지</li>
  <ul>
</div>
```



### 인접 형제 선택자 (Adjacent Sibling Combinator)

- 같은 부모를 가진 형제 요소 중 첫번째 동생 요소에만 스타일 적용
- 요소1과 요소2는 같은 레벨이면서 요소1 이후 맨 먼저 오는 요소2에 스타일을 적용

```css
/* 망고 선택 */
.orange + li { color: red; }
```

```html
<ul>
  <li class="orange">오렌지</li>
  <li>망고</li>
  <li>사과</li>
</ul>
```



### 일반 형제 선택자 (General Sibling Combinator)

- 형제 요소들에 스타일 적용
- 인접 형제 선택자와 다른 점은 모든 형제 요소에 다 적용된다는 것

```css
/* 망고 사과 모두 선택 */
.orange ~ li { color: red; }
```

```html
<ul>
  <li class="orange">오렌지</li>
  <li>망고</li>
  <li>사과</li>
</ul>
```