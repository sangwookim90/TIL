# Mybatis

```
<insert id="insertBoard" parameterType="me.helpeachother.springbootmybatis.board.dto.BoardDto"
            useGeneratedKeys="true" keyProperty="boardIdx">
```
- useGeneratedKeys: DBMS가 자동 생성키를 지원할 경우에 사용
- keyPropertykeyProperty: useGeneratedKeys나 selectKey의 하위 엘리먼트에 의해 리턴되는 키를 의미
    - 게시글의 경우 board_idx 컬럼이 pk이면서 자동 생성이 되게끔 했기 때문에 이 컬럼을 사용