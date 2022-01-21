 # Transform (변환)
> 요소의 변환 효과
- transform: 변환함수1 변환함수2 ...;
- 원근법, 이동, 크기, 회전, 기울임

## 2D 변환 함수
### translate
- px 단위
- translate(x,y) > 이동(x축,y축)
- translate(x) > 이동(x축)
- translate(y) > 이동(y축)

### scale
- scale(x,y) > 크기(x축,y축)
- scaleX > 크기(x축)
- scaleY > 크기(y축)

### rotate | skew
- deg 단위
- rotate(degree) > 회전(각도)
- skew(x,y) > 기울임(x축,y축)
- skewX(x) > 기울임(x축)
- skewY(y) > 기울임(y축)

```css
  transform: rotate(45deg) scale(1.2) translate(10px, 15px)
``` 

## 3D 변환 함수
### translate
- px 단위
- translate(Z) > 이동(z축)
- translate3d(x,y,z) > 이동(x축,y축,z축)

### scale
- scaleZ(z) > 크기(z축)
- scale3d(x,y,z) > 크기(x축,y축,z축)

### perspective
- px 단위
- perspective(n) > 원근법(거리)

### rotate
- deg 단위
- rotateX(x) > 회전(x축)
- rotateY(y) > 회전(y축)
- rotateZ(z) > 회전(z축)
- rotate3d(x,y,z,a) > 회전(x축,y축,z축,각도)

```css
  /* 원근법 함수는 제일 앞에 작성해야 한다 */
  transform: perspective(500px) rotateX(45deg);
```

## perspective
> 하위 요소를 관찰하는 원근 거리를 지정
- px 단위

### perspective 속성과 함수 차이점
- perspective: 600px; > 관찰 대상의 부모에 적용 > 기준점 설정: perspective-origin
- transform: perspective(600px) > 관찰 대상에 적용 > 기준점 설정: transform-origin

## backface-visibility
> 3D 변환으로 회전된 요소의 뒷면 숨김 여부
- default: visible > 보임
- hidden > 숨김

