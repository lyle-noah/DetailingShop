document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('cart-modal');
    const closeButton = modal.querySelector('.close-button');
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
                    modal.classList.remove('hidden');
                } else {
                    console.error('Error adding item to cart');
                }
            })
            .catch(error => console.error('Error:', error));
        });
    });

    if (closeButton) {
        closeButton.addEventListener('click', function() {
            modal.classList.add('hidden');
        });
    }

    const goToCartButton = document.querySelector('#go-to-cart');
    if (goToCartButton) {
        goToCartButton.addEventListener('click', function() {
            window.location.href = '/cart'; // 장바구니 페이지로 리다이렉트
        });
    }

    const continueShoppingButton = document.querySelector('#continue-shopping');
    if (continueShoppingButton) {
        continueShoppingButton.addEventListener('click', function() {
            modal.classList.add('hidden');
        });
    }
});
