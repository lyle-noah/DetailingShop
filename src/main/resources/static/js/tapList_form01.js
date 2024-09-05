document.querySelectorAll('.category-btn').forEach(button => {
    button.addEventListener('click', (event) => {
        event.preventDefault(); // 기본 동작 방지

        const category = button.getAttribute('data-category'); // 버튼의 카테고리 속성 가져오기
        document.getElementById('selectedCategory').value = category; // hidden 필드에 카테고리 값 설정

        // FormData 객체 생성 (form 데이터를 전송하기 위한 객체)
        const form = document.getElementById('categoryForm');
        const formData = new FormData(form);  // FormData 생성

        // CSRF 토큰을 가져와 FormData에 추가
        const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        formData.append('_csrf', csrfToken);  // CSRF 토큰을 FormData에 추가

        fetch('/product/category', {
            method: 'POST',
            body: formData
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);  // 서버에서 받은 데이터를 출력
            const productContainer = document.getElementById('product-container');
            productContainer.innerHTML = '';  // 기존 상품 목록 제거

            data.forEach(product => {
                const productCard = document.createElement('div');
                productCard.className = 'product-card';
                productCard.innerHTML = `
                    <img src="${product.imgurl}" alt="${product.productName}">
                    <h3>${product.productName}</h3>
                    <p>${product.productPrice}원</p>
                `;
                productContainer.appendChild(productCard);
            });
        })
        .catch(error => {
            console.error('Error:', error);  // 에러 발생 시 출력
        });
    });
});
