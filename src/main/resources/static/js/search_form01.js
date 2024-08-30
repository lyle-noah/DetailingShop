document.addEventListener('DOMContentLoaded', function () {
    // PC 버전 검색 아이콘 요소 가져오기
    const searchIconPC = document.getElementById('search-icon-pc');
    const searchModalPC = document.getElementById('search-modal');
    const searchOverlayPC = document.getElementById('search-overlay');

    // 모바일 버전 검색 아이콘 요소 가져오기
    const searchIconMobile = document.getElementById('search-icon-mobile');
    const searchModalMobile = document.getElementById('search-modal');
    const searchOverlayMobile = document.getElementById('search-overlay');

    // PC 버전 검색 아이콘 클릭 이벤트
    if (searchIconPC) {
        searchIconPC.addEventListener('click', function () {
            searchModalPC.classList.add('show'); // 애니메이션을 적용하기 위해 'show' 클래스를 추가
            searchModalPC.classList.remove('hidden'); // hidden 클래스를 제거하여 모달을 표시
            searchOverlayPC.classList.remove('hidden');
        });
    }

    // 모바일 버전 검색 아이콘 클릭 이벤트
    if (searchIconMobile) {
        searchIconMobile.addEventListener('click', function () {
            searchModalMobile.classList.add('show'); // 애니메이션을 적용하기 위해 'show' 클래스를 추가
            searchModalMobile.classList.remove('hidden'); // hidden 클래스를 제거하여 모달을 표시
            searchOverlayMobile.classList.remove('hidden');
        });
    }

    // 검색 모달의 오버레이 클릭 이벤트 (PC & 모바일 공통)
    const closeSearchModal = function () {
        searchModalPC.classList.remove('show');
        searchModalPC.classList.add('hidden');
        searchOverlayPC.classList.add('hidden');

        searchModalMobile.classList.remove('show');
        searchModalMobile.classList.add('hidden');
        searchOverlayMobile.classList.add('hidden');
    };

    if (searchOverlayPC) {
        searchOverlayPC.addEventListener('click', closeSearchModal);
    }

    if (searchOverlayMobile) {
        searchOverlayMobile.addEventListener('click', closeSearchModal);
    }
});
