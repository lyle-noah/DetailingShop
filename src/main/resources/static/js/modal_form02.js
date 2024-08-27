document.addEventListener('DOMContentLoaded', function() {
    const modalExist = document.getElementById('cart-modal-exist');
    const closeButtonExist = modalExist.querySelector('.close-button');
    const continueShoppingBtnExist = document.getElementById('continue-shopping-exist');

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
});
