// mobileMenu_form01.js

document.addEventListener('DOMContentLoaded', () => {
    const submenuToggles = document.querySelectorAll('.submenu-toggle');

    submenuToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const submenu = this.nextElementSibling;
            const isOpen = submenu.classList.contains('open');

            // 모든 서브메뉴를 닫습니다.
            document.querySelectorAll('.custom-submenu.open').forEach(openSubmenu => {
                openSubmenu.classList.remove('open');
                openSubmenu.style.maxHeight = null;
            });

            // 현재 클릭된 서브메뉴만 열거나 닫습니다.
            if (!isOpen) {
                submenu.classList.add('open');
                submenu.style.maxHeight = submenu.scrollHeight + "px";
            } else {
                submenu.classList.remove('open');
                submenu.style.maxHeight = null;
            }
        });
    });

    // 서브메뉴 트리거를 선택합니다.
    const customSubmenuToggles = document.querySelectorAll('.custom-submenu-toggle');

    customSubmenuToggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const submenuContent = this.nextElementSibling;
            const isOpen = submenuContent.classList.contains('open');

            // 모든 서브서브메뉴를 닫습니다.
            document.querySelectorAll('.custom-submenu-content.open').forEach(openSubmenuContent => {
                openSubmenuContent.classList.remove('open');
                openSubmenuContent.style.maxHeight = null;
            });

            // 현재 클릭된 서브서브메뉴만 열거나 닫습니다.
            if (!isOpen) {
                submenuContent.classList.add('open');
                submenuContent.style.maxHeight = submenuContent.scrollHeight + "px";
            } else {
                submenuContent.classList.remove('open');
                submenuContent.style.maxHeight = null;
            }
        });
    });
});
