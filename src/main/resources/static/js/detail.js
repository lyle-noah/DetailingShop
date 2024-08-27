document.addEventListener("DOMContentLoaded", function() {
    const quantityInput = document.getElementById('quantityInput');
    const finalCartCountInput = document.getElementById('finalCartCount');
    const decreaseButton = document.querySelector('.cartCount-selector button:first-child');
    const increaseButton = document.querySelector('.cartCount-selector button:last-child');
    const totalPriceElement = document.getElementById('totalPrice');
    const unitPrice = parseInt(totalPriceElement.dataset.unitPrice);

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

    // CART 버튼을 누를 때 수량 업데이트 및 비동기 요청
    document.querySelector('.cart-form').addEventListener('submit', function(event) {
        event.preventDefault(); // 페이지 리로드 방지

        finalCartCountInput.value = quantityInput.value;

        const formData = new FormData(this);
        const actionUrl = this.getAttribute('action');

        fetch(actionUrl, {
            method: 'POST',
            body: formData,
        })
        .then(response => response.text())
        .then(html => {
            if (html.includes('modal_form01')) {
                document.getElementById('cart-modal').style.display = 'flex';
            } else if (html.includes('modal_form02')) {
                document.getElementById('cart-modal-exist').style.display = 'flex';
            }
        })
        .catch(error => console.error('Error:', error));
    });

    // 초기 총 가격 설정
    updateTotalPrice();

    // 모달 창 닫기 로직 추가
    document.querySelectorAll('.close-button').forEach(button => {
        button.addEventListener('click', function() {
            document.getElementById('cart-modal').style.display = 'none';
            document.getElementById('cart-modal-exist').style.display = 'none';
        });
    });

    document.getElementById('continue-shopping-exist').addEventListener('click', function() {
        document.getElementById('cart-modal-exist').style.display = 'none';
    });

    document.getElementById('continue-shopping').addEventListener('click', function() {
        document.getElementById('cart-modal').style.display = 'none';
    });
});
