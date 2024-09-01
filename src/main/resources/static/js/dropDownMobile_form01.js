document.addEventListener('DOMContentLoaded', function () {
    const toggles = document.querySelectorAll('.dropdown-toggle');

    toggles.forEach(toggle => {
        toggle.addEventListener('click', function () {
            const target = document.querySelector(this.getAttribute('data-target'));

            if (target.classList.contains('hidden')) {
                target.classList.remove('hidden');
                target.classList.add('show');
                this.textContent = '-';  // 버튼 텍스트를 '-'로 변경
            } else {
                target.classList.add('hidden');
                target.classList.remove('show');
                this.textContent = '+';  // 버튼 텍스트를 '+'로 변경
            }
        });
    });
});
