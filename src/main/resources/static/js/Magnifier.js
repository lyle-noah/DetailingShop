// 요소 선택
const mainImage = document.getElementById('mainImage');
const zoomLens = document.getElementById('zoomLens');
const zoomedImageContainer = document.getElementById('zoomedImageContainer');
const zoomedImage = document.getElementById('zoomedImage');

// 렌즈 및 확대된 이미지 초기화
const lensWidth = 100;
const lensHeight = 100;
const zoomFactor = 2;  // 확대 배율

mainImage.addEventListener('mouseenter', () => {
    zoomLens.style.display = 'block';
    zoomedImageContainer.style.display = 'block';
    zoomedImage.src = mainImage.src;  // 확대된 이미지에 원본 이미지 경로 설정
});

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

    // 확대된 이미지의 위치 설정
    const xPercent = (x / rect.width) * 65;
    const yPercent = (y / rect.height) * 65;

    // 확대된 이미지의 transformOrigin과 transform 설정
    zoomedImage.style.transformOrigin = `${xPercent}% ${yPercent}%`;
    zoomedImage.style.transform = `translate(-${xPercent}%, -${yPercent}%) scale(${zoomFactor})`;
});

mainImage.addEventListener('mouseleave', () => {
    zoomLens.style.display = 'none';
    zoomedImageContainer.style.display = 'none';
});