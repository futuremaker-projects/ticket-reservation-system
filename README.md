### ERD 설계

<br>

<img width="700" alt="스크린샷 2024-07-05 오전 2 01 42" src="https://github.com/futuremaker019/ticket-reservation-system/assets/47493140/fe58fef8-5def-43c4-b9c1-5dbf741cce2f">


---

### API 명세서입니다.

https://first-longan-7e1.notion.site/API-3c9b22117eae4c079f6228051e908ef7?pvs=4

| 도메인 | 기능               | HTTP Method | URL                                                              | Request                             | Response                                                                                                                                                                           | Error                                      |
|-----|------------------|-------------|------------------------------------------------------------------|-------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------| 
| 대기열 | 대기열에 들어갔을때 토큰 발급 | POST        | /queue/token                                                     |                                     | {<br>id: 1 <br>userId : 1<br>token: ‘UUID’,<br>status: WAIT,<br>createdAt: 2024-07-10 10:10:10,<br>expiredAt: 2024-07-10. 10:15:10<br>}                                            |                                            |
| 콘서트 | 콘서트 목록조회         | GET         | /concerts                                                        |                                     | {<br>concerts: [ <br>{ <br>id: 1, <br>name: ‘콘서트1’<br>},<br>{ <br>id: 2, <br>name: ‘콘서트2’<br>}, <br>]<br>}                                                                         |                                            |
|     | 콘서트 단일조회         | GET         | /concert/{concertId}                                             | concertId : 1                       | {<br>id: 1<br>name: ‘콘서트1’<br>}                                                                                                                                                    |                                            |
| 예매  | 예약가능 날짜조회        | GET         | /reservation/concert/{concertId}/available-date                  | concertId : 1                       | {<br>id : 1,<br>name: ‘콘서트1’,<br>concertSchedules: [<br>{<br>id: 1,<br>openedAt: 2024-02-10 02:30<br>},<br>{<br>id: 2,<br>openedAt: 2024-02-15 02:30<br>}<br>]<br>}                |                                            |
|     | 예약가능 좌석조회        | GET         | /reservation/concert-schedule/{concertScheduleId}/available-seat | concertScheduleId: 1                | {<br>seats: [<br>{<br>id: 2,<br>seatId: 3,<br>concertScheduleId: 1<br>occupied: false<br>},<br>{<br>id: 3,<br>seatId: 5,<br>concertScheduleId: 1<br>occupied: false<br>}<br>]<br>} |                                            |
|     | 죄석예약 요청          | POST        | /reservation                                                     | {<br>concertId: 0<br>seatId: 0<br>} | void<br>(200, SUCCESS)                                                                                                                                                             |                                            |
|     | 결제               | POST        | /reservation/payment                                             | {<br>userId: 0<br>point: 1000<br>}  | void<br>(200, SUCCESS)                                                                                                                                                             | PointException, `포인트가 부족합니다. 부족한 포인트 - %d` |
| 포인트 | 포인트 조회           | GET         | /point                                                           | {<br>userId: 0<br>}                 | {<br>point: 0<br>}                                                                                                                                                                 |                                            |
|     | 포인트 충전           | POST        | /point                                                           | {<br>userId: 0 <br>point: 0<br>}    | void<br>(200, SUCCESS)                                                                                                                                                             |                                            |
