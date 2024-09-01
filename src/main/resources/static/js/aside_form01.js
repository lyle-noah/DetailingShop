document.addEventListener('DOMContentLoaded', function () {
    // 모바일 메뉴 아이콘 요소 가져오기
    const asideIcon = document.getElementById('mobile-menu-button');
    const asideModal = document.getElementById('aside-modal');
    const asideOverlay = document.getElementById('aside-overlay');
    const closeAsideButton = document.getElementById('close-aside-button');

    // 모바일 메뉴 아이콘 클릭 이벤트
    if (asideIcon) {
        asideIcon.addEventListener('click', function () {
            asideModal.classList.remove('hide');
            asideModal.classList.add('show');
            asideModal.classList.remove('hidden');
            asideOverlay.classList.remove('hidden');
        });
    }

    // 오버레이 클릭 및 닫기 버튼 클릭 이벤트
    const closeModal = function () {
        asideModal.classList.remove('show');
        asideModal.classList.add('hide');
        setTimeout(function () {
            asideModal.classList.add('hidden');
            asideOverlay.classList.add('hidden');
        }, 300); // 애니메이션 시간이 지나고 나서 숨김 처리
    };

    if (asideOverlay) {
        asideOverlay.addEventListener('click', closeModal);
    }

    if (closeAsideButton) {
        closeAsideButton.addEventListener('click', closeModal);
    }
});
