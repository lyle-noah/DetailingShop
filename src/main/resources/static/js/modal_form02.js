// modal_form02.js
document.addEventListener('DOMContentLoaded', function() {
    const modalExist = document.getElementById('cart-modal-exist');
    const closeButtonExist = modalExist.querySelector('.close-button');

    const addToCartForms = document.querySelectorAll('form[action="/cart/add"]');

    addToCartForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // 기본 폼 제출 동작 방지

            const formData = new FormData(this);

            fetch(this.action, {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    // 이미 장바구니에 있는 상품인 경우 모달 창을 표시
                    modalExist.classList.remove('hidden');
                } else {
                    console.error('Error adding item to cart');
                }
            })
            .catch(error => console.error('Error:', error));
        });
    });

    if (closeButtonExist) {
        closeButtonExist.addEventListener('click', function() {
            modalExist.classList.add('hidden');
        });
    }

    const goToCartButtonExist = document.querySelector('#go-to-cart-exist');
    if (goToCartButtonExist) {
        goToCartButtonExist.addEventListener('click', function() {
            window.location.href = '/cart'; // 장바구니 페이지로 리다이렉트
        });
    }

    const continueShoppingButtonExist = document.querySelector('#continue-shopping-exist');
    if (continueShoppingButtonExist) {
        continueShoppingButtonExist.addEventListener('click', function() {
            modalExist.classList.add('hidden');
        });
    }
});
