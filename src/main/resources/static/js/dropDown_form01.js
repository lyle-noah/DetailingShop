document.addEventListener('DOMContentLoaded', function() {
    // 모든 메뉴 항목을 가져옵니다.
    const menuItems = document.querySelectorAll('.nav-container a');

    // 메뉴 항목 클릭 시 활성화 상태 추가
    menuItems.forEach(item => {
        item.addEventListener('click', function() {
            menuItems.forEach(i => i.classList.remove('active')); // 기존 활성화 제거
            this.classList.add('active'); // 클릭한 메뉴에 활성화 추가
        });
    });
});
