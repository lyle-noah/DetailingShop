document.addEventListener("DOMContentLoaded", function() {
    const quantityInput = document.getElementById('quantityInput');
    const finalCartCountInput = document.getElementById('finalCartCount');
    const decreaseButton = document.querySelector('.cartCount-selector button:first-child');
    const increaseButton = document.querySelector('.cartCount-selector button:last-child');
    const totalPriceElement = document.getElementById('totalPrice');
    const unitPrice = parseInt(totalPriceElement.dataset.unitPrice); // 개별 상품 가격

    function updateCartCount() {
        finalCartCountInput.value = quantityInput.value;
        updateTotalPrice();
    }

    function updateTotalPrice() {
        const currentCartCount = parseInt(quantityInput.value);
        const totalPrice = currentCartCount * unitPrice;
        totalPriceElement.textContent = totalPrice.toLocaleString() + "원";
    }

    function increaseCartCount() {
        var input = document.getElementById("quantityInput");
        input.value = parseInt(input.value) + 1;
        updateFinalCartCount(input.value);
    }

    function decreaseCartCount() {
        var input = document.getElementById("quantityInput");
        if (parseInt(input.value) > 1) {
            input.value = parseInt(input.value) - 1;
            updateFinalCartCount(input.value);
        }
    }

    function updateFinalCartCount(value) {
        document.getElementById("finalCartCount").value = value;
    }

    quantityInput.addEventListener('input', updateCartCount);

    decreaseButton.addEventListener('click', function(event) {
        event.preventDefault();
        decreaseCartCount();
    });

    increaseButton.addEventListener('click', function(event) {
        event.preventDefault();
        increaseCartCount();
    });

    // CART 버튼을 누를 때 수량 업데이트
    document.querySelector('.cart-form').addEventListener('submit', function(event) {
        finalCartCountInput.value = quantityInput.value; // 최종 수량을 서버로 전송
    });

    // 초기 총 가격 설정
    updateTotalPrice();
