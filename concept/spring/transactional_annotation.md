# @Transactional

## Transaction 성질
#### 원자성(Atomicity)
- 한 트랜잭션 내에서 실행한 작업들은 **하나**로 간주한다. 즉 모두 성공 또는 모두 실패
#### 일관성(Consistency)
- 트랜잭션은 일관성 있는 데이타베이스 상태를 유지한다.
#### 격리성(Isolation)
- 동시에 실행되는 트랜잭션들이 서로 영향을 미치지 않도록 격리해야한다.
#### 지속성(Durability)
- 트랜잭션을 성공적으로 마치면 결과가 항상 저장되어야 한다.


```Java
@Transactional
public int createApplication(ApplicationDto dto) {
    
    ...

    int result = applicationRepository.saveAndFlush(application); ---- (1)

    ...

    if(result == 1){
        projectApplicationRepository.saveAndFlush(projectApplication); ---- (2)
    }

    return result;
}
```

## Transactional을 사용하는 이유?
- (1)이 성공하고 (2)에서 에러가 발생하면 (1)을 자동으로 rollback해주기 위해 사용
- Transactional을 사용하지 않는다면, 직접 (1) 데이터를 삭제하여야 함.

## Spring에서 트랜잭션 처리 방법
### @Transactional (선언적 트랜잭션)

```Java
@Transactional
public boolean insertUser(User user){
    ...
}
```

- @Transactional이 추가되면, 이 클래스에 트랜잭션 기능이 적용된 프록시 객체 생성
- @Transactional이 포함된 메소드가 호출 될 경우, PlatformTransactionManager를 사용하여 트랜잭션을 시작하고, 결과에 따라 Commit 또는 Rollback

## 다수의 트랜잭션이 경쟁시 발생할 수 있는 문제
### Problem1 - Dirty Read
- 트랜잭션 A가 어떤 값을 1에서 2로 변경하고, 아직 커밋하지 않은 상황에서 트랜잭션B가 같은 값을 읽는 경우 트랜잭션 B는 2가 조회 된다.
- 트랜잭션 B가 2를 조회한 후 혹시 A가 롤백된다면, 트랜잭션B는 잘못된 값을 읽게 된 것이다.
- `트랜잭션이 완료되지 않은 상황에서 데이터에 접근을 허용할 경우` 발생할 수 있는 데이터 불일치이다.

### Problem2 - Non-Repeatable Read
- 트랜잭션 A가 어떤 값 1을 읽었다. 이후 A는 같은 쿼리를 또 실행할 예정인데, 그 사이에 트랜잭션 B가 값 1을 2로 바꾸고 커밋해버리면
A가 같은 쿼리 두번을 날리는 사이 두 쿼리의 결과가 다르게 되어 버린다.
- 즉, `한 트랜잭션에서 같은 쿼리를 두번 실행했을 때` 발생할 수 있는 데이터 불일치이다.
- Dirty Read에 비해 발생 확률이 낮음.

### Problem3 - Phantom Read
- 트랜잭션 A가 어떤 조건을 사용하여 특정 범위의 값들 [0,1,2,3,4]을 읽었다.
- 이후 A는 같은 쿼리를 실행할 예정인데, 그 사이에 트랜잭션 B가 같은 테이블에 값 [5,6,7]을 추가해버리면 A가 같은 쿼리 두번을 날리는 사이 두 쿼리의 결과가 다름.
- `한 트랜잭션에서 일정 범위의 레코드를 두번 이상 읽을 때` 발생하는 데이터 불일치이다.

## 다수의 트랜잭션 경쟁을 방지할 수 있는 속성
### 1. isolation(격리수준)
#### DEFAULT
- 기본 격리 수준(기본 설정, DB의 isolation Level을 따름)

#### READ_UNCOMMITED (level 0)
- 커밋되지 않는(트랜잭션 처리중인) 데이터에 대한 읽기를 허용
- 어떤 사용자가 A라는 데이터를 B라는 데이터로 변경하는 동안 다른 사용자는 B라는 아직 완료되지 않은 데이터 B를 읽을 수 있다.
- `Dirty Read` 발생 가능

#### READ_COMMITED (level 1)
- 트랜잭션이 커밋된 확정 데이터만 읽기 허용
- 어떠한 사용자가 A라는 데이터를 B라는 데이터로 변경하는 동안 다른 사용자는 해당 데이터에 접근할 수 없다.
- `Dirty Read` 방지

#### REPEATABLE_READ (level 2)
- 트랜잭션이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 shared lock이 걸리므로 다른 사용자는 그 영역에 해당되는 데이터에 대한 **수정 불가능**
- 선행 트랜잭션이 읽은 데이터는 트랜잭션이 종료될 때까지 후행 트랜잭션이 갱신하거나 삭제가 불가능하기 때문에 같은 데이터를 두 번 쿼리했을 때 일관성있는 결과를 리턴한다.
- `Non-Repeatable Read` 방지

#### SERIALIZABLE (level 3)
- 데이터의 일관성 및 동시성을 위해 MVCC(Multi Version Concurrency Control)을 사용하지 않음    
(MVCC는 다중 사용자 데이터베이스 성능을 위한 기술로, 데이터 조회 시 LOCK을 사용하지 않고 데이터의 버전을 관리해 데이터의 일관성 및 동시성을 높이는 기술)
- 트랜잭션이 완료될 때까지 SELECT 문장이 사용하는 모든 데이터에 shared lock이 걸리므로 다른 사용자는 그영역에 대한 **수정 및 입력 불가능**
- `Phantom Read` 방지

```Java
@Transactional(isolation=Isolation.DEFAULT)
public User findUser(Long id){
    ...
}
```

### 2. propagation (전파 옵션)
- 트랜잭션 동장 도중 다른 트랜잭션을 호출(실행)하는 상황을 선택할 수 있는 옵션
- @Transactional의 propagation 속성을 통해 피호출 트랜잭션의 입장에서는 호출한 쪽의 트랜잭션을 그대로 사용할 수도 있고, 새롭게 트랜잭션을 생성할 수도 있다.
#### REQUIRED
- default 속성, 부모 트랜잭션 내에서 실행하며 부모 트랜잭션이 없는 경우 새로운 트랜잭션 생성

#### SUPPORT
- 이미 시작된 트랜잭션이 있으면 참여하고 그렇지 않으면 트랜잭션 없이 진행

#### REQUIRES_NEW
- 부모 트랜잭션을 무시하고 무조건 새로운 트랜잭션 생성

#### MANDATORY
- REQUIRED와 비슷하게 부모 트랜잭션이 있으면 참여, 없으면 생성 대신 예외 발생
- 혼자서는 독립적으로 트랜잭션을 진행하면 안되는 경우에 사용

#### NOT_SUPPORTED
- 트랜잭션을 사용하지 않게 한다.
- 이미 진행중인 트랜잭션이 있으면 보류시킨다.

#### NEVER
- 트랜잭션을 사용하지 않도록 강제
- 이미 진행중인 트랜잭션이 존재하면 예외 발생

#### NESTED
- 이미 진행중인 트랜잭션이 있으면 중첩 트랜잭션을 시작    
(중첩 트랜잭션은 트랜잭션 안에 다시 트랜잭션을 만드는 것)

### 3. readOnly 속성
- 트랜잭션을 읽기 전용으로 설정
- 성능을 최적화하기 위해 사용
- 특정 트랜잭션 작업 안에서 쓰기 작업이 일어나는 것을 의도적으로 방지하기 위해 사용
- 일부 트랜잭션 매니저의 경우 읽기 전용 속성을 무시하고 쓰기 작업을 허용할 수도 있기 때문에 주의
- 일반적으로 읽기 전용 트랜잭션이 시작된 이후 INSERT, UPDATE, DELETE 같은 쓰기 작업이 진행되면 예외 발생
- get 나 find 같은 이름의 메소드를 모두 읽기전용으로 만들어 사용하면 편리
    
```Java
@Transactional(readOnly=true)
```
### 4. timeout
- 지정한 시간 내에 해당 메소드 수행이 완료되지 않은 경우 rollback 수행. -1일 경우 no timeout(Default=-1)
```Java
@Transactional(timeout=10)
```

