(function() {
// Slide_form02.js
const productList = [
    { name: "제품 25", img: "/img/Product/product0240.jpeg" },
    { name: "제품 26", img: "/img/Product/product0241.jpeg" },
    { name: "제품 27", img: "/img/Product/product0242.jpeg" },
    { name: "제품 28", img: "/img/Product/product0243.jpeg" },
    { name: "제품 29", img: "/img/Product/product0244.jpeg" },
    { name: "제품 30", img: "/img/Product/product0245.jpeg" },
    { name: "제품 31", img: "/img/Product/product0246.jpeg" },
    { name: "제품 32", img: "/img/Product/product0247.jpeg" }
];

let currentStartIndex = 0;
const visibleProductsCount = 4;

function renderProducts() {
    const slidesContainer = document.querySelector('.product-slides');
    slidesContainer.innerHTML = '';

    const visibleProducts = productList.slice(currentStartIndex, currentStartIndex + visibleProductsCount);
    visibleProducts.forEach(product => {
        const slide = document.createElement('div');
        slide.className = 'product-slide';
        slide.innerHTML = `
            <img src="${product.img}" alt="${product.name}">
            <h3>${product.name}</h3>
        `;
        slidesContainer.appendChild(slide);
    });

    // 슬라이드가 끝에 도달하면 버튼을 비활성화
    document.querySelector('.product-nav.next').disabled = currentStartIndex + visibleProductsCount >= productList.length;
    document.querySelector('.product-nav.prev').disabled = currentStartIndex === 0;
}

function moveSlide(step) {
    // 슬라이드의 시작 인덱스를 업데이트하기 전에 마지막 슬라이드인지 확인
    if (step > 0 && currentStartIndex + visibleProductsCount >= productList.length) {
        return; // 마지막 슬라이드에서 더 이상 이동하지 않음
    }
    if (step < 0 && currentStartIndex === 0) {
        return; // 처음 슬라이드에서 더 이상 이동하지 않음
    }

    currentStartIndex = Math.min(Math.max(currentStartIndex + step, 0), productList.length - visibleProductsCount);
    renderProducts();
}

document.querySelector('.product-nav.prev').addEventListener('click', () => moveSlide(-1));
document.querySelector('.product-nav.next').addEventListener('click', () => moveSlide(1));

// 자동 슬라이드 기능을 제거합니다.
// setInterval(() => {
//     moveSlide(1);
// }, 5000);

// 초기 슬라이드 렌더링
renderProducts();

// 마우스 드래그 기능 추가
let startX, endX;
const slider = document.querySelector('.product-slider');

slider.addEventListener('mousedown', (event) => {
    startX = event.clientX;
    slider.addEventListener('mousemove', onMouseMove);
});

slider.addEventListener('mouseup', (event) => {
    endX = event.clientX;
    handleSwipe();
    slider.removeEventListener('mousemove', onMouseMove);
});

slider.addEventListener('mouseleave', () => {
    slider.removeEventListener('mousemove', onMouseMove);
});

function onMouseMove(event) {
    endX = event.clientX;
}

function handleSwipe() {
    const threshold = 50; // 스와이프 인식 기준 픽셀 수
    if (startX - endX > threshold) {
        moveSlide(1);
    } else if (endX - startX > threshold) {
        moveSlide(-1);
    }
}

})();
