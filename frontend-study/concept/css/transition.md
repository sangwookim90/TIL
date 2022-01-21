# Transition (전환)
> 요소의 전환(시작과 끝) 효과를 지정하는 단축 속성

### transition-property (속성명)
> 전환 효과를 사용할 속성 이름을 지정
- default: all > 모든 속성에 적용
- 속성이름 > 전환 효과를 사용할 속성 이름 명시

### transition-duration (지속시간)
> 전환 효과의 지속시간을 지정
- default: 0s > 전환 효과 없음
- 시간 > 지속시간(s)을 지정

```css
  transition:
    width .5s,
    background-color 2s;
```

### transition-timing-function (타이밍함수)
> 전환 효과의 타이밍(Easing) 함수를 지정
- default: ease > 느리게 - 빠르게 - 느리게 = cubic-bezier(0.25, 0.1, 0.25, 1)
- linear > 일정하게 = cubic-bezier(0, 0, 1, 1)
- ease-in > 느리게 - 빠르게 = cubic-bezier(0.42, 0, 1, 1)
- ease-out > 빠르게 - 느리게 = cubic-bezier(0, 0, 0.58, 1)

### transition-delay (대기시간)

> 전환 효과가 몇 초 뒤에 시작할지 대기시간 지정
- default: 0s > 대기시간 없음
- 대기시간(s) 지정