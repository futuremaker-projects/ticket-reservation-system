# 콘서트 예약 서비스

<details>
    <summary style="font-weight: bold; font-size: 17px;">요구사항</summary>

## Description

- **`콘서트 예약 서비스`** 를 구현해 봅니다.
- 대기열 시스템을 구축하고, 예약 서비스는 작업가능한 유저만 수행할 수 있도록 해야합니다.
- 사용자는 좌석예약 시에 미리 충전한 잔액을 이용합니다.
- 좌석 예약 요청시에, 결제가 이루어지지 않더라도 일정 시간동안 다른 유저가 해당 좌석에 접근할 수 없도록 합니다.

## Requirements

- 아래 5가지 API 를 구현합니다.
  - 유저 토큰 발급 API
  - 예약 가능 날짜 / 좌석 API
  - 좌석 예약 요청 API
  - 잔액 충전 / 조회 API
  - 결제 API
- 각 기능 및 제약사항에 대해 단위 테스트를 반드시 하나 이상 작성하도록 합니다.
- 다수의 인스턴스로 어플리케이션이 동작하더라도 기능에 문제가 없도록 작성하도록 합니다.
- 동시성 이슈를 고려하여 구현합니다.
- 대기열 개념을 고려해 구현합니다.

</details>

<br>

<details>
    <summary style="font-weight: bold; font-size: 17px;">마일스톤</summary>

```mermaid
gantt
  title 항해 플러스 서버 구축 콘서트 예약 시스템 마일스톤
  dateFormat  YYYY-MM-DD
  section 서버구축 설계 및 구현
    마일스톤, ERD, Mock API                     :active, des1, 2024-06-30, 2024-07-05
    Swagger 적용 및 기능 구현(대기열, 예약, 결제)     :active, dev1, 2024-07-06, 2024-07-12
    대기열 인터셉터, Log filter 적용               :active, dev2, 2024-07-14, 2024-07-19
  
  section 동시성 문제 확인 및 코드 리팩토링
    락 적용을 위한 리팩토링                        :active, enh1, 2024-07-20, 2024-07-26
  
  section 적은 부하로 트래픽 처리
    Redis 캐쉬전략                            :active, buf1, 2024-07-27, 2024-07-29
    Redis를 이용한 대기열 리팩토링                :active, buf1, 2024-07-30, 2024-08-02
  section 부하 축소하기
    인덱스 적용하여 쿼리성능 개선                  :active, opt1, 2024-08-03, 2024-08-05
    트랜잭션 범위 조절 및 애플리케이션 이벤트 적용     :active, opt1, 2024-08-06, 2024-08-09
  section 책임 분리
    Kafka를 적용한 책임 분리                     :active, mon1, 2024-08-10, 2024-08-16
  section 장애 대응
    부하 테스트를 통한 장애대응 계획수립              :active, mon1, 2024-08-17, 2024-08-23
```
</details>

<br>

<details>
    <summary style="font-weight: bold; font-size: 17px;">시퀀스 다이어그램</summary>

#### 콘서트 및 콘서트 스케줄 조회
<img width="700" alt="스크린샷 2024-07-05 오전 2 01 42" src="docs/images/sequenceDiagram/concert.png">

#### 예약가능 날짜 및 좌석 조회, 포인트로 좌석 예약
<img width="700" alt="스크린샷 2024-07-05 오전 2 01 42" src="docs/images/sequenceDiagram/reservation.png">

#### 포인트 충전, 사용
<img width="200" alt="스크린샷 2024-07-05 오전 2 01 42" src="docs/images/sequenceDiagram/point.png">

</details>

<br>

<details>
    <summary style="font-weight: bold; font-size: 17px;">ERD</summary>

<img width="700" alt="스크린샷 2024-07-05 오전 2 01 42" src="./docs/images/readme/ticket-reservation.png">

</details>

<br>

<details>
    <summary style="font-weight: bold; font-size: 17px;">API 명세서</summary>

### 대기열 토큰 발급 API

Endpoint

```
POST /queue/token
```

Response
```json
Http Status: 200 ok

Body: 
{
  id: 1
  userId : 1
  token: ‘UUID’,
  status: WAIT,
  createdAt: 2024-07-10 10:10:10,
  expiredAt: 2024-07-10. 10:15:10
}
```

### 콘서트 목록조회 API

Endpoint
```
GET /concerts
```

Response
```json
Http Status: 200 ok
        
Body:
  {
      concerts: [ 
         { 
            id: 1,  
           name: ‘콘서트1’
          },
         { 
            id: 2,  
           name: ‘콘서트2’
          }, 
      ]
  }
```

### 콘서트 단일조회 API

Endpoint
```
GET /concert/{concertId}
```

Request Body
```
{
  concertId: 1
} 
```

Response
```json
Http Status: 200 ok

Response Body:
        
  {
    id: 1
    name: ‘콘서트1’
  }
```

### 예약가능 날짜 조회 API

Endpoint
```
GET /concertSchedules/concerts/{cocnertId}
```

Request Body
```
{
  concertId: 1
} 
```

Response
```json

ResponseBody: 
{
    id : 1,
    name: ‘콘서트1’,
    concertSchedules: [
        {
           id: 1,
           openedAt: 2024-02-10 02:30
        },
        {
           id: 2,
           openedAt: 2024-02-15 02:30
        },
        ...
    ]
}
```

### 예약가능 좌석조회 API

EndPoint
```
/concertSchedules/{concertScheduleId}/seats
```

Request Body 
```
{
  concertSchedule: 1
}
```

Response
```json
Http Status: 200 ok

Response Body:
{
  seats: [
     {
         id: 2,
         seatId: 3,
         concertScheduleId: 1
         occupied: false
     },
    {
          id: 3,
          seatId: 5,
          concertScheduleId: 1
          occupied: false
    }
   ]
}
```

### 좌석예약 API

Endpoint
```
POST /reservation
```

Request Body
```
{
  concertId: 1
  concertSchedule: 1,
  seatId: [1, 2]
}
```

Response
```
void
(200, SUCCESS)
```

### 결제 API

Endpoint
```
POST /reservation/payment
```

Request Body
```
{
    reservationId: 1,
    amount: 1000
}
```

Response
```
void
(200, SUCCESS)
```

### 포인트 조회

Endpoint
```
GET /point
```

Request Body
```
{
  userId: 1
}
```

Response
```
{
  point: 100
}
```

### 포인트 충전

Endpoint
```
POST /point/charge
```

Request Body
```
{
  point: 1000
}
```

Response
```
void
(200, SUCESS)
```

### API 명세서 정리표(링크)

https://first-longan-7e1.notion.site/API-3c9b22117eae4c079f6228051e908ef7?pvs=4

</details>

<br>

<details>
  <summary style="font-weight: bold; font-size: 17px;">Swagger</summary>

<img width="700" alt="스크린샷 2024-07-05 오전 2 01 42" src="docs/images/swagger/swagger.png">

</details>

<br>

<details>
  <summary style="font-weight: bold; font-size: 17px;">동시성 분석 및 개선 - 개선</summary>

```
동시성 로직 파악
동시성 해결 방법
각 케이스 별로 어떤 락이 맞는지
트랜잭션 위치
```

> 예약하기 기능의 동시성 이슈 및 제어

- 시나리오
  - 콘서트 예약시 여러명의 사용자가 복수개의 좌석을 선택하여 예약을 진행합니다.

- 적용한 락 종류
  - `비관적 락`
- 적용 이유
  - 좌석의 경우 많은 사용자가 동시에 점유하는 상황(경쟁 조건 - race condition)이 빈번하게 일어날 수 있으며 또한 좌석을 선점한 상태의 일관성을 유지하기 위해 `비관적 락`을 사용했습니다.

- 적용 위치
  - 선택된 좌석을 점유 상태인지 조회하는 쿼리에서 비관적 락을 사용하였습니다.

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "1500"))
@Query("select t from Ticket t where t.id.concertScheduleId = :concertScheduleId and t.id.seatId in :seats")
List<Ticket> findAllWithPessimisticLock(@Param("concertScheduleId") Long concertScheduleId, @Param("seats") List<Long> seats);
```

- 테스트 조건
  - 1000명의 사용자가 동시에 2개의 좌석을 임시 선점하는 시나리오
  - 테스트 실패 (락 미적용)
    - 2개의 동일한 좌석이 7번의 서로 다른 예약에 의해 선점됨
  - 테스트 성공 (비관적락 적용)
    - 2개의 좌석이 하나의 예약으로만 선점됨

<img src="image/reservation/reservation-without-lock.png" width="600">

<br>

<img src="image/reservation/reservation-with-lock.png" width="380">

- 성능 테스트

| 사용자수          | 테스트 시간체크     |
|---------------|--------------|
| 1000명 동시성 테스트 | 1sec 277 ms  |
| 10000명 동시성 테스트 | 7sec 133 ms  |
| 20000명 동시성 테스트 | 15sec 797 ms |

<img src="image/reservation/10.png" width="380">
<br>
<img src="image/reservation/100-test.png" width="380">
<br>
<img src="image/reservation/200-test.png" width="380">

<br>
<br>

> 포인트 충전 기능의 동시성 이슈 및 제어

- 시나리오
  - 사용자의 실수로 포인트 충전요청이 동시에 여러번 일어난 경우
  - 적용한 락 종류
    - 낙관적 락
  - 적용 이유
    - 빈번하게 일어나는 동시성의 이슈가 아닌 사용자의 실수로 일어나는 오류를 방지하며, <br>Version을 사용해 어플리케이션 상에서 해당 행위를 방지하기 위해 적용했습니다.
  - 적용 위치
    - 토큰을 이용하여 사용자가 가지고 있는 포인트를 조회시 낙관적락을 적용했습니다.
  ```java
    @Lock(LockModeType.OPTIMISTIC)
    @Query("select u from UserAccount u where u.token = :token")
    Optional<UserAccount> findByToken(@Param("token") String token);
  ```
- 테스트 조건
  - 10번의 반복된 포인트 충전 시도 시

<img src="image/point/10-test.png" width="380">

- 트랜잭션의 위치를 서비스가 아닌 리포지토리로 변경시 기존 10번에서 15번까지 테스트 통과

<img src="image/point/15-test.png" width="380">

</details>


<br>

<details>
  <summary style="font-weight: bold; font-size: 17px;">Caching 전략 - 생성</summary>

</details>


<br>

<details>
<summary style="font-weight: bold; font-size: 17px;">Redis를 이용한 대기열 리팩토링 - 생성</summary>


</details>

<br>

<details>
  <summary style="font-weight: bold; font-size: 17px;">인덱스를 적용한 성능최적화 - 추가</summary>

> 제공되는 서비스의 요구사항 중 쿼리의 성능을 개선하여 검색 및 수정, 삭제의 성능을 높일수 있는 쿼리를 찾아 수정

### 개선이 필요한 쿼리
- 선택한 콘서트의 콘서트 스케줄을 한달 단위 or 선택한 날짜의 기간검색이 가능하도록 쿼리 개선

### 테스트를 위한 더미 데이터 정보

| 데이터 수량 | 콘서트 id  | 날짜시작일      | 날짜종료일      |
|--------|---------|------------|------------|
| 200만개  | 1 ~ 500 | 2022-01-01 | 2024-12-31 |

- 최초 5개의 콘서트 id를 사용하여 테스트 수행 시, 카디널리티가 너무 작아 인덱스의 효과가 미미하였다.
- 현재의 테스트는 500개의 콘서트 id를 순차적으로 적용해 카디널리티를 높여 효과적으로 인덱스가 적용될 수 있도록 수정하였다.

### 개선이 필요한 쿼리 형태
```sql
select * 
from schedule_id_op 
where concert_id = 2 
  and opened_at between '2024-07-01 00:00:00' and '2024-07-31 23:59:59';
```
- 현재 쿼리의 성능개선으로 concert_id, opened_at 컬럼의 인덱스를 생성하는 작업을 진행했다.
- 인덱스는 아래와 같이 생성함
  1. 인덱스 미적용
  1. concert_id 단일 인덱스
  2. opened_at 단일 인덱스
  3. concert_id, opened_at의 복합 인덱스
  4. concert_id, opened_at의 각각 단일 인덱스 생성


### 성능비교

|                        | 인덱스 형태     | 검색 소요시간  |  
|------------------------|------------|----------|
| 미적용                    | N/A        | 352ms    |        
| concert_id             | 단일 인덱스     | 68ms     |        
| opened_at              | 단일 인덱스     | 339md    |
| concert_id, opened_at  | 2개의 단일 인덱스 | 52ms     |
| concert_id, opened_at  | 복합 인덱스     | **35ms** |
**각 쿼리의 소요시간은 약 10번의 테스트를 한 소요시간**

- 인덱스 적용 후 테스트 시 효과가 가장 두드려지게 보였던 3가지 인덱스의 `explain` 키워드를 적용해 보았다.
  - concert_id 단일 인덱스

    | select_tye | type | possible_keys  | key            | ref   | filtered | extra       |
        |------------|------|----------------|----------------|-------|----------|-------------|
    | simple     | ref  | idx_concert_id | idx_concert_id | const | 11.11    | using where |

  - concert_id, opened_at 2개의 단일 인덱스

    | select_tye | type | possible_keys                     | key                               | ref   | filtered | extra       |
        |------------|------|-----------------------------------|-----------------------------------|-------|----------|-------------|
    | simple     | ref  | idx_concert_id <br> idx_opened_at | idx_concert_id <br> idx_opened_at | const | 5.1      | using where |
  - concert_id, opened_at 복합 인덱스

    | select_tye | type  | possible_keys            | key                      | ref | filtered | extra                 |
        |------------|-------|--------------------------|--------------------------|-----|----------|-----------------------|
    | simple     | range | idx_concert_id_opened_at | idx_concert_id_opened_at |     | 100      | using index condition |

- 해당 where 절의 조건에서는 모두 인덱스가 사용되었다고 나왔지만 `extra`를 비교시 복합 인덱스만 `using index condition` 을 보여주었으며 `filtered` 또한 `100` 인것을 확인했다.

### 결론

- 콘서트 스케줄의 날짜 기간검색 시, 인덱스를 사용하면 인덱스를 사용하지 않았을때 보다 약 90.06%의 성능이 개선되었다.
- 2개의 각 단일 인덱스보다 복합 인덱스를 사용했을 때 약 32.69%으로의 성능이 개선되었다.


</details>

<br>

<details>
  <summary style="font-weight: bold; font-size: 17px;">서비스 규모 확장에 따른 Transaction 분리 및 고찰 - 수정</summary>


</details>
  
<br>

<details>
  <summary style="font-weight: bold; font-size: 17px;">Kafka를 이용한 책임분리 및 Transactional Outbox Pattern 구현 보고서 - 생성</summary>


</details>

<br>

<details>
    <summary style="font-weight: bold; font-size: 17px;">부하테스트 및 장애 대응 보고서</summary>

> 부하 테스트를 통해 개선이 필요한 API를 선별하여 성능개선을 한다.

<br>

<details>
    <summary style="font-weight: bold">테스트 환경</summary>

#### 성능 테스트 환경 설정

<div style="display: flex; gap: 30px">
  <img src="./docs/images/app-spec.png" style="width: 300px">
  <img src="./docs/images/k6-compose.png" style="width: 300px">
</div>

| 용어           | 설명            | 적용                              | 
|--------------|---------------|---------------------------------|
| reservations | - 최소로 보장하는 자원 | - cpu : 100% <br> - memory: 1G  |
| limits       | 최대 사용 가능한 자원  | - cpu : 100%, <br> - memory: 1G |

> docker container 를 이용하여 테스트할 애플리케이션 서버를 격리하였고 가용한 자원의 영역을 설정하였다.
> 또한 prometheus, grafana를 이용해 k6 부하테스트의 시각화를 하였다.

<br>

#### 시나리오 환경

<img src="./docs/images/k6-test-env.png" style="width: 700px">

> 가상 사용자는 100명을 기준으로 작성하였으며, 테스트에 따라 iterations를 적절히 수정하여 테스트하였음

</details>

<br>

<details>
    <summary style="font-weight: bold">부하테스트 및 결과</summary>

<br>

<details>
    <summary style="font-weight: bold">콘서트 목록조회</summary>

- 문제 확인
  - 콘서트 리스트 조회시 전체 조회로 인해 서버에 많은 부하를 가중시킴

- 테스트 데이터 : 10만개
- 테스트 결과

<div style="display: flex; flex-direction: column; gap: 20px; align-items: center">
  <img src="./docs/images/concert/concert1-fix.png" style="width: 700px">
  <img src="./docs/images/concert/concert2-fix.png" style="width: 700px">
</div>

- 개선
  - 페이지네이션을 추가하여 목록 조회시 속도 개선

| 지표  | 페이지네이션 적용전 | 적용후    | 비고          |
|-----|------------|--------|-------------|
| p95 | 1.06 min   | 311 ms | 99.51% 성능향상 |
| p99 | 1.07 min   | 468 ms | 99.27% 성능향상 |

> 페이지네이션이 적용되지 않은 상태에서 목록 조회시 성능 측정이 불가능하였으며, 페이지네이션 적용 후 많은 개선이 일어났다.
</details>

<br>

<details>
    <summary style="font-weight: bold">콘서트 스케줄의 월별 조회</summary>

- 문제 확인
  - 달력뷰를 위한 콘서트 스케줄의 월별 조회시 인덱스의 부제로 인한 속도 저하
- 데스트 데이터 : 2백만개
- 테스트 결과

<div style="display: flex; flex-direction: column; gap: 20px; align-items: center">
  <img src="./docs/images/concert-schedule/schedule1-fix.png" style="width: 700px">
  <img src="./docs/images/concert-schedule/schedule2-fix.png" style="width: 700px">
  <img src="./docs/images/concert-schedule/schedule3-fix.png" style="width: 700px">
</div>

- 개선
  - concert_id 와 opened_at 을 가지는 복합 인덱스를 적용하여 기능 개선함

| 지표                 |                              | 인덱스 적용전      | 적용후          | 비고            |
|--------------------|------------------------------|--------------|--------------|---------------|
| Iterations         | - 얼마나 많은 "사용자 행동"이 실행되었는지 확인 | - 약 190 ms   | - 75ms       | - 60.53% 성능향상 |
| HTTP Latency Stats | - 요청 지연의 통계 데이터, 서버 성능의 일관성을 분석                             | - 약 190 ms   | - 75ms       | - 60.53% 성능향상 |
| HTTP Request Rate  | - 초당 처리된 HTTP 요청 수, 처리 가능한 최대 요청수 파악이 가능                             | - 약 4k req/s | - 약 7k req/s | - 42.85% 성능향상 |

</details>

<br>

<details>
  <summary style="font-weight: bold">콘서트 스케줄의 좌석 목록조회</summary>

- 문제 확인
  - 콘서트 스케줄의 연관된 좌석을 조회하는 과정에서 좌석의 concert_schedule_id의 인덱스 부제로 인한 조인 속도저하
- 데스트 데이터
  - 콘서트 스케줄 : 10만개
  - seat : 5백만개
- 테스트 결과

<div style="display: flex; flex-direction: column; gap: 20px; align-items: center">
  <img src="./docs/images/seat/seat1-fix.png" style="width: 700px">
  <img src="./docs/images/seat/seat2-fix.png" style="width: 700px">
  <img src="./docs/images/seat/seat3-fix.png" style="width: 700px">
</div>

- 개선
  - Seat의 외례키인 concert_schedule_id 에 인덱스를 적용하여 조인속도 개선

| 지표  | 인덱스 적용전 | 인덱스 적용후 | 비고           |
|-----|---------|---------|--------------|
| p95 | 130 ms  | 97.5 ms | 25% 성능 향상    | 
| p99 | 212 ms  | 148 ms  | 30.19% 성능 향상 | 

</details>

</details>

