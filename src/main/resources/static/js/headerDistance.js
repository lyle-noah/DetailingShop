document.addEventListener("DOMContentLoaded", function() {
    const header = document.querySelector('header');
    const main = document.querySelector('main');

    function adjustMainPadding() {
        const headerHeight = header.offsetHeight;
        const extraPadding = 50; // 추가적으로 더할 여백 (예: 20px)
        main.style.paddingTop = `${headerHeight + extraPadding}px`;
    }

    adjustMainPadding();

    window.addEventListener('resize', adjustMainPadding);
});