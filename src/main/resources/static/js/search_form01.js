document.addEventListener('DOMContentLoaded', function () {
    const searchIcon = document.getElementById('search-icon');
    const searchModal = document.getElementById('search-modal');
    const searchOverlay = document.getElementById('search-overlay');

    searchIcon.addEventListener('click', function () {
        searchModal.classList.add('show'); // 애니메이션을 적용하기 위해 'show' 클래스를 추가
        searchModal.classList.remove('hidden'); // hidden 클래스를 제거하여 모달을 표시
        searchOverlay.classList.remove('hidden');
    });

    searchOverlay.addEventListener('click', function () {
        searchModal.classList.remove('show'); // 'show' 클래스를 제거하여 애니메이션 종료
        searchModal.classList.add('hidden'); // hidden 클래스를 다시 추가하여 모달을 숨김
        searchOverlay.classList.add('hidden');
    });
});
