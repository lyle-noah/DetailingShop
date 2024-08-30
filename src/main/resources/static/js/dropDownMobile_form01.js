document.addEventListener('DOMContentLoaded', function() {
    const toggles = document.querySelectorAll('.dropdown-toggle');

    toggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const target = document.querySelector(this.getAttribute('data-target'));

            // 애니메이션 중복 실행 방지
            if (target.classList.contains('animating')) {
                return;
            }

            if (target.classList.contains('open')) {
                closeMenu(target);
            } else {
                closeSiblingMenus(target);
                openMenu(target);
            }
        });
    });

    function openMenu(menu) {
        const parentMenuItem = menu.closest('.mobile-menu-item');

        // 상태를 animating으로 설정하여 중복 클릭 방지
        menu.classList.add('animating');
        parentMenuItem.style.height = (parentMenuItem.scrollHeight + menu.scrollHeight) + "px";

        menu.style.display = "block";
        menu.style.overflow = "hidden";
        menu.style.maxHeight = "0";
        menu.style.transition = "max-height 0.5s ease-in-out";

        requestAnimationFrame(() => {
            menu.style.maxHeight = menu.scrollHeight + "px";
        });

        menu.addEventListener('transitionend', function() {
            menu.style.maxHeight = "none";
            menu.style.overflow = "visible";
            menu.classList.add('open');
            menu.classList.remove('animating'); // 애니메이션 완료 후 animating 상태 제거
        }, { once: true });
    }

    function closeMenu(menu) {
        const parentMenuItem = menu.closest('.mobile-menu-item');

        // 상태를 animating으로 설정하여 중복 클릭 방지
        menu.classList.add('animating');
        parentMenuItem.style.height = (parentMenuItem.scrollHeight - menu.scrollHeight) + "px";

        menu.style.overflow = "hidden";
        menu.style.maxHeight = menu.scrollHeight + "px";
        menu.style.transition = "max-height 0.5s ease-in-out";

        requestAnimationFrame(() => {
            menu.style.maxHeight = "0";
        });

        menu.addEventListener('transitionend', function() {
            menu.style.display = "none";
            menu.classList.remove('open');
            menu.classList.remove('animating'); // 애니메이션 완료 후 animating 상태 제거
        }, { once: true });
    }

    function closeSiblingMenus(exceptTarget) {
        const parentMenu = exceptTarget.parentElement.parentElement;
        const siblingMenus = parentMenu.querySelectorAll('.dropdown-menu');

        siblingMenus.forEach(menu => {
            if (menu !== exceptTarget && menu.classList.contains('open')) {
                closeMenu(menu);
            }
        });
    }
});
