document.addEventListener("DOMContentLoaded", function() {
    // 수량 관련 요소 선택
    const quantityInput = document.getElementById('quantity');
    const minusButton = quantityInput.previousElementSibling;
    const plusButton = quantityInput.nextElementSibling;
    const totalPriceElement = document.querySelector('.product-page .total-price span');
    const unitPrice = parseFloat(document.querySelector('.product-page .product-price').textContent.replace(/[^0-9.-]+/g,""));

    // 수량 변경 시 총 가격 업데이트 함수
    function updateTotalPrice() {
        const quantity = parseInt(quantityInput.value);
        const totalPrice = (unitPrice * quantity).toLocaleString();  // 총 가격 계산 및 포맷팅
        totalPriceElement.textContent = `₩${totalPrice}`;
    }

    // 마이너스 버튼 클릭 이벤트
    minusButton.addEventListener('click', () => {
        let quantity = parseInt(quantityInput.value);
        if (quantity > 1) {
            quantityInput.value = quantity - 1;
            updateTotalPrice();
        }
    });

    // 플러스 버튼 클릭 이벤트
    plusButton.addEventListener('click', () => {
        let quantity = parseInt(quantityInput.value);
        quantityInput.value = quantity + 1;
        updateTotalPrice();
    });

    // 수량 입력 필드 직접 수정 시 이벤트
    quantityInput.addEventListener('input', () => {
        let quantity = parseInt(quantityInput.value);
        if (isNaN(quantity) || quantity < 1) {
            quantityInput.value = 1;
        }
        updateTotalPrice();
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