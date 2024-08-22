import http from "k6/http";
import { check } from "k6";
import {randomIntBetween, randomItem} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

/**
 * 콘서트 검색 시나리오
 *  - 전체 콘서트 검색 vs 페이지네이션 적용한 콘서트 검색
 */
// export let options = {
//     scenarios: {
//         concert_select_scenario: {
//             vus: 100, // 가상 사용자
//             exec: 'concert_select_scenario',
//             executor: 'per-vu-iterations', // 각각의 가상 사용자들이 정확한 반복 횟수만큼 실행
//             iterations: 200
//         }
//     }
// };
//
// export function concert_select_scenario() {
//     let response = http.get("http://localhost:8080/api/concerts");
//     // 응답 확인
//     check(response, {
//         'is status 200': (r) => r.status === 200,
//     });
// }
// export function concert_select_scenario() {
//     let response = http.get("http://localhost:8080/api/concerts/paging");
//     // 응답 확인
//     check(response, {
//         'is status 200': (r) => r.status === 200,
//     });
// }

/**
 *  콘서트 스케줄에 연관된 좌석을 검색 시 좌석의 콘서트 스케줄에 인덱스를 추가 유무에 따른 조인성능 향상 확인 테스트
 */
// export let options = {
//     scenarios: {
//         select_seat_scenario: {
//             vus: 100, // 가상 사용자
//             exec: 'select_seat_scenario',
//             executor: 'per-vu-iterations', // 각각의 가상 사용자들이 정확한 반복 횟수만큼 실행
//             iterations: 1000
//         }
//     }
// };
//
// export function select_seat_scenario() {
//     // let concertScheduleId = randomIntBetween(1, 100000);
//     let response = http.get(`http://localhost:8080/api/concertSchedules/${600}/seats`);
//     // 응답 확인
//     check(response, {
//         'is status 200': (r) => r.status === 200,
//     });
// }


/**
 *  콘서트 스케줄의 달력뷰를 위한 월별주기 검색시 인덱스 미적용 vs 복합 인덱스 적용 부하 테스트
 */
export let options = {
    scenarios: {
        select_schedule_scenario: {
            vus: 100, // 가상 사용자
            exec: 'select_schedule_scenario',
            executor: 'per-vu-iterations', // 각각의 가상 사용자들이 정확한 반복 횟수만큼 실행
            iterations: 500
        }
    }
};

export function select_schedule_scenario() {
    let response = http.post(`http://localhost:8080/api/concertSchedules`, {concertId: 50, startDate: "2024-07-01T17:03:13.238Z", endDate: "2024-07-31T17:03:13.238Z"});
    // 응답 확인
    check(response, {
        'is status 200': (r) => r.status === 200,
    });
}
