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

    // 장바구니 추가 및 모달 관련 요소 선택
    const cartForm = document.querySelector('form.cart-form');
    const modal = document.getElementById('cart-modal');
    const continueShoppingBtn = document.getElementById('continue-shopping');
    const goToCartBtn = document.getElementById('go-to-cart');


    // 폼 제출 이벤트 막기
    cartForm.addEventListener('submit', function(event) {
        event.preventDefault(); // 폼 기본 제출 동작 막기

        // 모달 창 보이기
        modal.style.display = 'flex';
    });

    // 쇼핑 계속하기 버튼 클릭 시
    continueShoppingBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    // 장바구니로 이동 버튼 클릭 시
    goToCartBtn.addEventListener('click', function() {
        // 실제 장바구니로 이동
        cartForm.submit();
    });
})