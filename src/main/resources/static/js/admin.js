function showContent(sectionId) {
    // 모든 섹션을 숨김
    var sections = document.getElementsByClassName('content');
    for (var i = 0; i < sections.length; i++) {
        sections[i].style.display = 'none';
    }

    // 선택된 섹션을 표시
    var section = document.getElementById(sectionId);
    if (section) {
        section.style.display = 'block';
    }
}

document.addEventListener("DOMContentLoaded", function() {
    showContent('userManagement'); // 페이지 로드 시 기본으로 유저 관리 섹션 표시
});
