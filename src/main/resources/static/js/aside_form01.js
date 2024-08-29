document.addEventListener('DOMContentLoaded', function() {
    const menuButton = document.getElementById('mobile-menu-button');
    const closeButton = document.getElementById('close-mobile-menu');
    const mobileAsideMenu = document.getElementById('mobile-aside-menu');

    menuButton.addEventListener('click', function() {
        mobileAsideMenu.classList.toggle('-translate-x-full');
    });

    closeButton.addEventListener('click', function() {
        mobileAsideMenu.classList.add('-translate-x-full');
    });

    const toggles = document.querySelectorAll('.dropdown-toggle');
    toggles.forEach(toggle => {
        toggle.addEventListener('click', function() {
            const target = document.querySelector(this.getAttribute('data-target'));
            target.classList.toggle('open');
        });
    });
});