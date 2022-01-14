# Background

### background-color

- transparent | 색상

### background-image

- none | url("경로")

### background-repeat

- repeat | repeat-x | repeat-y | no-repeat

### background-position

- 방향: top | bottom | left | right | center
- 단위: px | em | rem

```css
background-position: top right;
background-position: 100px 30px;
```

### background-size

- default: auto
- 단위: px | em | rem
- cover: 비율을 유지, 요소의 더 넓은 너비에 맞춤
- contain: 비율을 유지, 요소의 더 짧은 너비에 맞춤

### background-attachment

> 요소의 배경 이미지 스크롤 특성

- default: scroll : 이미지가 요소를 따라서 같이 스크롤
- fixed: 이미지가 뷰포트에 고정, 스크롤 X