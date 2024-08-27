document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('cart-modal');
    const closeButton = modal.querySelector('.close-button');
    const continueShoppingBtn = document.getElementById('continue-shopping');

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
});
