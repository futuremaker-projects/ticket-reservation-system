## index 를 활용한 쿼리 개선

### 목적
- 제공되는 서비스의 요구사항 중 쿼리의 성능을 개선하여 검색 및 수정, 삭제의 성능을 높일수 있는 쿼리를 찾아 수정

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
  and opened_at between '2024-01-01 00:00:00' and '2024-07-31 23:59:59';
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

- 해당 where 절의 조건에서는 모두 인덱스가 사용되었다고 나왔지만 `extra`를 비교시 복합 인덱스만 `using index condition` 을 보여주었으면 `filtered` 또한 `100` 인것을 확인했다.

### 결론

- 콘서트 스케줄의 날짜 기간검색 시, 인덱스를 사용하면 인덱스를 사용하지 않았을때 보다 약 90.06%의 성능이 개선되었다.
- 2개의 각 단일 인덱스보다 복합 인덱스를 사용했을 때 약 32.69%으로의 성능이 개선되었다.