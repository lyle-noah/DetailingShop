document.addEventListener("DOMContentLoaded", function () {
    const zoomFactor = 2; // 확대 배율 설정
    const imageTarget = document.getElementById('mainImage'); // 대상 이미지
    const magnifierDiv = document.querySelector('.magnifier'); // 돋보기 요소
    const hoverText = document.getElementById('hoverText'); // 안내 문구
    const lensDiv = document.createElement('div'); // 렌즈 요소 생성
    lensDiv.className = 'lens';
    imageTarget.parentElement.appendChild(lensDiv);

    // 확대 효과 함수 정의
    function magnify(e) {
        const rect = imageTarget.getBoundingClientRect();
        const lensWidth = lensDiv.offsetWidth;
        const lensHeight = lensDiv.offsetHeight;
        let mousePosX = e.clientX - rect.left; // 마우스 X 좌표
        let mousePosY = e.clientY - rect.top; // 마우스 Y 좌표

        // 렌즈가 이미지 밖으로 나가지 않도록 제한
        if (mousePosX < lensWidth / 2) mousePosX = lensWidth / 2;
        if (mousePosX > rect.width - lensWidth / 2) mousePosX = rect.width - lensWidth / 2;
        if (mousePosY < lensHeight / 2) mousePosY = lensHeight / 2;
        if (mousePosY > rect.height - lensHeight / 2) mousePosY = rect.height - lensHeight / 2;

        // 렌즈 위치 설정
        lensDiv.style.left = `${mousePosX - lensWidth / 2}px`;
        lensDiv.style.top = `${mousePosY - lensHeight / 2}px`;

        // 확대 이미지 위치 설정
        const bgPosX = -(mousePosX * zoomFactor - magnifierDiv.clientWidth / 2);
        const bgPosY = -(mousePosY * zoomFactor - magnifierDiv.clientHeight / 2);
        magnifierDiv.style.backgroundImage = `url('${imageTarget.src}')`; // 배경 이미지 설정
        magnifierDiv.style.backgroundSize = `${imageTarget.width * zoomFactor}px ${imageTarget.height * zoomFactor}px`; // 배경 크기 설정
        magnifierDiv.style.backgroundPosition = `${bgPosX}px ${bgPosY}px`;
    }

    // 이미지에 mousemove 이벤트 핸들러 연결
    imageTarget.addEventListener('mousemove', function(e) {
        magnifierDiv.style.display = 'block'; // 돋보기 표시
        hoverText.style.opacity = '0'; // 호버 시 텍스트 숨김
        lensDiv.style.display = 'block'; // 렌즈 표시
        magnify(e); // 렌즈 및 확대 이미지 위치 계산
    });

    // 이미지에 마우스가 나갔을 때 돋보기와 텍스트 상태 초기화
    imageTarget.addEventListener('mouseleave', function() {
        magnifierDiv.style.display = 'none';
        hoverText.style.opacity = '1';
        lensDiv.style.display = 'none'; // 렌즈 숨기기
    });
});
