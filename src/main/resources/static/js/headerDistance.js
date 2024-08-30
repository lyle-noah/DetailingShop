document.addEventListener('DOMContentLoaded', function() {
    const menuButton = document.querySelector('.mobile-menu-button');
    const mobileMenu = document.querySelector('.mobile-menu');
    const header = document.querySelector('.header');
    const main = document.querySelector('main');

    // 모바일 메뉴 토글 기능
    menuButton.addEventListener('click', function() {
        mobileMenu.classList.toggle('hidden');
    });

    // 헤더 높이 계산 및 body와 main의 padding-top 설정
    function adjustBodyAndMainPadding() {
        const headerHeight = header.offsetHeight;
        const extraPadding = 50; // 추가적으로 더할 여백
        document.body.style.paddingTop = `${headerHeight}px`;
        main.style.paddingTop = `${headerHeight + extraPadding}px`;
    }

    // 페이지 로드 시 및 창 크기 조정 시 padding 조정
    adjustBodyAndMainPadding();
    window.addEventListener('resize', adjustBodyAndMainPadding);
});