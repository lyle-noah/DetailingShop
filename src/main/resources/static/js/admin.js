//function showContent(sectionId) {
//    // 모든 섹션을 숨김
//    var sections = document.getElementsByClassName('content');
//    for (var i = 0; i < sections.length; i++) {
//        sections[i].style.display = 'none';
//    }
//
//    // 선택된 섹션을 표시
//    var section = document.getElementById(sectionId);
//    if (section) {
//        section.style.display = 'block';
//    }
//}
//
//document.addEventListener("DOMContentLoaded", function() {
//    showContent('userManagement'); // 페이지 로드 시 기본으로 유저 관리 섹션 표시
//
//    // 서버에서 전달된 실제 데이터로 변경
//    var dailyLoginUsers = /*[[${dailyLoginUsers}]]*/ 0;  // 실제 데이터가 숫자로 렌더링
//    var totalUsers = /*[[${totalUsers}]]*/ 0;  // 실제 데이터가 숫자로 렌더링
//
//
//    // 일별 로그인한 유저 수 차트
//    var dailyLoginUsersCtx = document.getElementById('dailyVisitorsChart').getContext('2d');
//    var dailyLoginUsersChart = new Chart(dailyLoginUsersCtx, {
//        type: 'bar',
//        data: {
//            labels: ['월', '화', '수', '목', '금', '토', '일'],
//            datasets: [{
//                label: '일별 접속자 수',
//                data: [dailyLoginUsers],  // 서버에서 전달된 데이터 사용
//                backgroundColor: 'rgba(75, 192, 192, 0.2)',
//                borderColor: 'rgba(75, 192, 192, 1)',
//                borderWidth: 1
//            }]
//        },
//        options: {
//            scales: {
//                y: {
//                    beginAtZero: true
//                }
//            }
//        }
//    });
//
//    // 총 유저 수 차트
//    var totalUsersCtx = document.getElementById('totalVisitorsChart').getContext('2d');
//    var totalUsersChart = new Chart(totalUsersCtx, {
//        type: 'line',
//        data: {
//            labels: ['1주차', '2주차', '3주차', '4주차'],
//            datasets: [{
//                label: '총 접속자 수',
//                data: [totalUsers],  // 서버에서 전달된 데이터 사용
//                backgroundColor: 'rgba(153, 102, 255, 0.2)',
//                borderColor: 'rgba(153, 102, 255, 1)',
//                borderWidth: 1
//            }]
//        },
//        options: {
//            scales: {
//                y: {
//                    beginAtZero: true
//                }
//            }
//        }
//    });
//});
