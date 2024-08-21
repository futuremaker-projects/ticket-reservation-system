import http from "k6/http";
import { check } from "k6";

export let options = {
    vus: 100, // 가상 사용자 수
    duration: '100s', // 테스트 지속 시간
};

export default function () {
    let response = http.get("http://localhost:8080/api/concerts");

    // 응답 확인
    check(response, {
        'is status 200': (r) => r.status === 200,
    });
}

// export const options = {
//     discardResponseBodies: true,
//     scenarios: {
//         contacts: {
//             executor: "per-vu-iterations",
//             vus: 10,
//             iterations: 20,
//             maxDuration: "30s",
//         },
//     },
// };
//
// export default function () {
//     let res = http.get("https://test.k6.io/contacts.php");
//     check(res, {
//         "is status 200": (r) => r.status === 200,
//     });
// }
