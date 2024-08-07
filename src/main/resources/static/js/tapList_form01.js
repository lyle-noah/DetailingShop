// scripts.js

// 상품 데이터 객체
const products = {
    external: [
        {name: "제품 1", img: "/img/Product/product0033.jpeg"},
        {name: "제품 2", img: "/img/Product/product0034.jpeg"},
        {name: "제품 3", img: "/img/Product/product0035.jpeg"},
        {name: "제품 4", img: "/img/Product/product0036.jpeg"},
        {name: "제품 5", img: "/img/Product/product0037.jpeg"},
        {name: "제품 6", img: "/img/Product/product0038.jpeg"},
    ],
    internal: [
        {name: "제품 7", img: "/img/Product/product0217.jpeg"},
        {name: "제품 8", img: "/img/Product/product0218.jpeg"},
        {name: "제품 9", img: "/img/Product/product0219.jpeg"},
        {name: "제품 10", img: "/img/Product/product0220.jpeg"},
        {name: "제품 11", img: "/img/Product/product0221.jpeg"},
        {name: "제품 12", img: "/img/Product/product0222.jpeg"},
    ],
    wheel: [
        {name: "제품 13", img: "/img/Product/product0234.jpeg"},
        {name: "제품 14", img: "/img/Product/product0235.jpeg"},
        {name: "제품 15", img: "/img/Product/product0236.jpeg"},
        {name: "제품 16", img: "/img/Product/product0237.jpeg"},
        {name: "제품 17", img: "/img/Product/product0238.jpeg"},
        {name: "제품 18", img: "/img/Product/product0239.jpeg"},
    ],
    towel: [
        {name: "제품 19", img: "/img/Product/product0334.jpeg"},
        {name: "제품 20", img: "/img/Product/product0335.jpeg"},
        {name: "제품 21", img: "/img/Product/product0336.jpeg"},
        {name: "제품 22", img: "/img/Product/product0337.jpeg"},
        {name: "제품 23", img: "/img/Product/product0338.jpeg"},
        {name: "제품 24", img: "/img/Product/product0339.jpeg"},
    ]
};

function renderProducts(category) {
    const productContainer = document.getElementById('product-container');
    productContainer.innerHTML = '';

    const productsToShow = products[category].slice(0, 4); // 각 카테고리에서 처음 4개 상품만 표시

    productsToShow.forEach(product => {
        const productCard = document.createElement('div');
        productCard.className = 'product-card';
        productCard.innerHTML = `
            <img src="${product.img}" alt="${product.name}">
            <h3>${product.name}</h3>
        `;
        productContainer.appendChild(productCard);
    });
}

// 카테고리 버튼 클릭 시 해당 카테고리의 상품을 로드합니다
document.querySelectorAll('.category-btn').forEach(button => {
    button.addEventListener('click', () => {
        const category = button.getAttribute('data-category');
        renderProducts(category);
    });
});

// 초기 카테고리 설정 (선택된 카테고리가 없으므로 기본값을 'external'로 설정)
renderProducts('external');
