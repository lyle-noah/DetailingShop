document.addEventListener('DOMContentLoaded', function() {
    const modalExist = document.getElementById('cart-modal-exist');
    const closeButtonExist = modalExist.querySelector('.close-button');
    const continueShoppingBtnExist = document.getElementById('continue-shopping-exist');
    const goToCartBtnExist = document.getElementById('go-to-cart-exist'); // 장바구니로 이동 버튼

    if (closeButtonExist) {
        closeButtonExist.addEventListener('click', function() {
            modalExist.style.display = 'none';
        });
    }

    if (continueShoppingBtnExist) {
        continueShoppingBtnExist.addEventListener('click', function() {
            modalExist.style.display = 'none';
        });
    }

    if (goToCartBtnExist) {
        goToCartBtnExist.addEventListener('click', function() {
            window.location.href = '/cart'; // 장바구니 페이지로 이동
        });
    }
});
