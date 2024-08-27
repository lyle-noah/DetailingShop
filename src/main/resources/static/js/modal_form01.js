document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('cart-modal');
    const closeButton = modal.querySelector('.close-button');
    const continueShoppingBtn = document.getElementById('continue-shopping');
    const goToCartBtn = document.getElementById('go-to-cart'); // 장바구니로 이동 버튼

    if (closeButton) {
        closeButton.addEventListener('click', function() {
            modal.style.display = 'none';
        });
    }

    if (continueShoppingBtn) {
        continueShoppingBtn.addEventListener('click', function() {
            modal.style.display = 'none';
        });
    }

    if (goToCartBtn) {
        goToCartBtn.addEventListener('click', function() {
            window.location.href = '/cart'; // 장바구니 페이지로 이동
        });
    }
});
