document.addEventListener('DOMContentLoaded', function() {
    // 모바일 메뉴 버튼과 닫기 버튼, 사이드 메뉴 요소 가져오기
    const menuButton = document.getElementById('mobile-menu-button');
    const closeButton = document.getElementById('close-mobile-menu');
    const mobileAsideMenu = document.getElementById('mobile-aside-menu');

    // 모바일 메뉴 열기 (토글 기능)
    menuButton.addEventListener('click', function() {
        mobileAsideMenu.classList.toggle('-translate-x-full');
    });

    // 모바일 메뉴 닫기
    closeButton.addEventListener('click', function() {
        mobileAsideMenu.classList.add('-translate-x-full');
    });

    // 드롭다운 토글 기능
    const toggles = document.querySelectorAll('.dropdown-toggle');
    toggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const target = document.querySelector(this.getAttribute('data-target'));
            target.classList.toggle('open');
        });
    });
});