document.addEventListener('DOMContentLoaded', function() {
    const menuButton = document.querySelector('.mobile-menu-button');
    const closeButton = document.querySelector('#close-mobile-menu');
    const mobileAsideMenu = document.getElementById('mobile-aside-menu');
    const logo = document.querySelector('.header-main .logo');
    const navContainer = document.querySelector('.nav-container');
    const mobileBreakpoint = 1024;

    // 모바일 메뉴 토글 기능
    menuButton.addEventListener('click', function() {
        mobileAsideMenu.classList.toggle('translate-x-0');
        mobileAsideMenu.classList.toggle('-translate-x-full');
    });

    closeButton?.addEventListener('click', function() {
        mobileAsideMenu.classList.add('-translate-x-full');
        mobileAsideMenu.classList.remove('translate-x-0');
    });

    // 헤더 높이 계산 및 body의 padding-top 설정
    function adjustBodyPadding() {
        const headerHeight = document.querySelector('.header').offsetHeight;
        document.body.style.paddingTop = `${headerHeight}px`;
    }

    // 로고 및 네비게이션 메뉴의 초기 마진 설정 및 동적 조정
    function adjustLogoAndNavContainer() {
        const windowWidth = window.innerWidth;

        if (windowWidth <= mobileBreakpoint) {
            // 모바일 환경일 때 로고 마진 조정
            let initialMargin = 330;

            // 현재 창 너비와 비교하여 줄어든 마진 값 계산
            const calculatedMargin = Math.max((windowWidth - 340) / 2, 0);
            if (calculatedMargin < initialMargin) {
                initialMargin = calculatedMargin;
            }

            logo.style.marginLeft = `${initialMargin}px`;
            logo.style.marginRight = `${initialMargin}px`;

            // 네비게이션 메뉴는 모바일에서 숨기기
            navContainer.style.display = 'none';
        } else {
            // PC 환경일 때 네비게이션 메뉴 마진 조정
            let initialNavMargin = 50;

            const calculatedNavMargin = Math.max((windowWidth - 1200) / 2, 0);
            if (calculatedNavMargin < initialNavMargin) {
                initialNavMargin = calculatedNavMargin;
            }

            navContainer.style.marginLeft = `${initialNavMargin}px`;
            navContainer.style.marginRight = `${initialNavMargin}px`;

            // 로고 마진을 고정된 값으로 설정
            logo.style.marginLeft = `${initialNavMargin}px`;
            logo.style.marginRight = `${initialNavMargin}px`;

            // 네비게이션 메뉴 보이기
            navContainer.style.display = 'flex';
        }
    }

    // 페이지 로드 시 및 창 크기 조정 시 패딩과 마진 조정
    adjustBodyPadding();
    adjustLogoAndNavContainer();
    window.addEventListener('resize', () => {
        adjustBodyPadding();
        adjustLogoAndNavContainer();
    });
});
