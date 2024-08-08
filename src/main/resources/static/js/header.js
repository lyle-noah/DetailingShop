document.addEventListener('DOMContentLoaded', function() {
    const menuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');
    const header = document.querySelector('.header');

    // 모바일 메뉴 토글 기능
    menuButton.addEventListener('click', function() {
        mobileMenu.classList.toggle('hidden');
    });

    // 헤더 높이 계산 및 body의 padding-top 설정
    function adjustBodyPadding() {
        const headerHeight = header.offsetHeight;
        document.body.style.paddingTop = headerHeight + 'px';
    }

    // 페이지 로드 시 및 창 크기 조정 시 padding 조정
    adjustBodyPadding();
    window.addEventListener('resize', adjustBodyPadding);
});