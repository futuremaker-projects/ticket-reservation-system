import http from "k6/http";
import { check } from "k6";
import {randomIntBetween, randomItem} from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

/**
 * 콘서트 검색 시나리오
 *  - 전체 콘서트 검색 vs 페이지네이션 적용한 콘서트 검색
 */
export let options = {
    scenarios: {
        concert_select_scenario: {
            vus: 100, // 가상 사용자
            exec: 'concert_select_scenario',
            executor: 'per-vu-iterations', // 각각의 가상 사용자들이 정확한 반복 횟수만큼 실행
            iterations: 200
        }
    }
};

export function concert_select_scenario() {
    let response = http.get("http://localhost:8080/api/concerts");
    // 응답 확인
    check(response, {
        'is status 200': (r) => r.status === 200,
    });
}
export function concert_select_scenario() {
    let response = http.get("http://localhost:8080/api/concerts/paging");
    // 응답 확인
    check(response, {
        'is status 200': (r) => r.status === 200,
    });
}
