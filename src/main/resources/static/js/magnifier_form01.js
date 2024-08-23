function magnify() {
    const mainImage = document.getElementById('mainImage');
    const zoomLens = document.querySelector('.zoom-lens');
    const zoomedImageContainer = document.querySelector('.zoomed-image-container');
    const zoomedImage = document.querySelector('#zoomed-image');

    const lensWidth = 200;  // 렌즈의 너비
    const lensHeight = 200; // 렌즈의 높이
    const zoomFactor = 1;   // JavaScript에서 배율은 1로 고정

    // 페이지 로드 시 초기 설정
    window.addEventListener('load', () => {
        zoomedImageContainer.style.display = 'none';
    });

    // 마우스가 이미지에 들어갔을 때 렌즈와 확대 이미지 컨테이너를 표시
    mainImage.addEventListener('mouseenter', () => {
        zoomLens.style.display = 'block';
        zoomedImageContainer.style.display = 'block';
        zoomedImage.src = mainImage.src;  // 확대된 이미지에 원본 이미지 경로 설정
    });

    // 마우스가 이미지 위에서 움직일 때 렌즈와 확대 이미지를 업데이트
    mainImage.addEventListener('mousemove', (e) => {
        const rect = mainImage.getBoundingClientRect();
        let x = e.clientX - rect.left - lensWidth / 2;
        let y = e.clientY - rect.top - lensHeight / 2;

        // 렌즈가 이미지 영역 밖으로 나가지 않도록 제한
        if (x > rect.width - lensWidth) x = rect.width - lensWidth;
        if (x < 0) x = 0;
        if (y > rect.height - lensHeight) y = rect.height - lensHeight;
        if (y < 0) y = 0;

        zoomLens.style.left = `${x}px`;
        zoomLens.style.top = `${y}px`;

        // 렌즈의 위치를 기준으로 확대된 이미지의 위치 설정
        const zoomX = (x / rect.width) * 100;
        const zoomY = (y / rect.height) * 100;

        // 확대된 이미지가 컨테이너에 여백 없이 정확히 표시되도록 설정
        zoomedImage.style.transformOrigin = `${zoomX}% ${zoomY}%`;
        zoomedImage.style.transform = `translate(-${x * 2}px, -${y * 2}px) scale(${zoomFactor})`;
    });

    // 마우스가 이미지에서 나갔을 때 렌즈와 확대 이미지 컨테이너를 숨김
    mainImage.addEventListener('mouseleave', () => {
        zoomLens.style.display = 'none';
        zoomedImageContainer.style.display = 'none';
    });
}

// 페이지가 로드되면 돋보기 기능을 실행
magnify();
