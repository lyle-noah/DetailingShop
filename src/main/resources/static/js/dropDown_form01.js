document.addEventListener('DOMContentLoaded', function() {
    const secondMenuItems = document.querySelectorAll('.secondmenu-item');

    secondMenuItems.forEach(item => {
        item.addEventListener('mouseenter', function() {
            const submenu = this.nextElementSibling;

            if (submenu && submenu.classList.contains('submenu-content')) {
                // 2차 메뉴의 위치를 가져옴
                const rect = this.getBoundingClientRect();

                // 메뉴 컨테이너의 스크롤과 상관없이 정확한 Y축 위치를 설정
                submenu.style.top = `${this.offsetTop}px`;
                submenu.style.left = `${rect.width}px`; // 2차 메뉴의 너비만큼 옆에 3차 메뉴 표시
            }
        });
    });
});

document.addEventListener('DOMContentLoaded', function() {
    const firstMenuItems = document.querySelectorAll('.firstmenu-item');
    const dropdowns = document.querySelectorAll('.dropdown');

    dropdowns.forEach(dropdown => {
        const firstMenuItem = dropdown.querySelector('.firstmenu-item');

        dropdown.addEventListener('mouseenter', function() {
            firstMenuItem.classList.add('active');
        });

        dropdown.addEventListener('mouseleave', function() {
            firstMenuItem.classList.remove('active');
        });
    });
});