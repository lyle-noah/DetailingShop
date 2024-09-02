document.addEventListener('DOMContentLoaded', function() {
    // 로고와 네비바 마진 계산 기능
    const logo = document.querySelector('.header-main .logo');
    const navContainer = document.querySelector('.nav-container');
    const mobileBreakpoint = 1024;

    // 사용자메뉴(텍스트) 요소 사이 | 막대바 동적 기능 변수
    const userMenu = document.querySelector('.user-menu-text');
    const links = userMenu.querySelectorAll('a');

    // 사용자메뉴(텍스트) 요소 사이 | 막대바 동적 기능
    links.forEach((link, index) => {
        if (index < links.length - 1 && link.style.display !== 'none') {
            const separator = document.createElement('span');
            separator.textContent = '|';
            separator.style.margin = '0 12px';
            link.after(separator);
        }
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
