document.addEventListener("DOMContentLoaded", function() {
    const quantityInput = document.getElementById('quantityInput');
    const finalCartCountInput = document.getElementById('finalCartCount');
    const decreaseButton = document.querySelector('.cartCount-selector button:first-child');
    const increaseButton = document.querySelector('.cartCount-selector button:last-child');
    const totalPriceElement = document.getElementById('totalPrice');

    // unitPrice 값을 소수로 변환
    const unitPrice = parseFloat(totalPriceElement.dataset.unitPrice);

    // unitPrice 값 확인
    console.log("Unit Price:", unitPrice);

    function updateTotalPrice() {
        let quantity = parseInt(quantityInput.value);

        // 수량이 비어있거나 NaN이면 0으로 설정
        if (isNaN(quantity) || quantity < 1) {
            quantity = 0;
            quantityInput.value = quantity;
        }

        // 최대 수량 제한
        if (quantity > 100) {
            quantity = 100;
            quantityInput.value = quantity;
        }

        // 총 금액 계산
        if (!isNaN(quantity) && !isNaN(unitPrice)) {
            const totalPrice = unitPrice * quantity;
            totalPriceElement.textContent = totalPrice.toLocaleString() + '원';
        } else {
            totalPriceElement.textContent = '0원'; // 기본값으로 0원 설정
        }
    }

    function updateCartCount() {
        finalCartCountInput.value = quantityInput.value;
        updateTotalPrice();
    }

    function increaseCartCount() {
        let quantity = parseInt(quantityInput.value);
        quantity = isNaN(quantity) ? 0 : quantity;

        if (quantity < 100) { // 최대 수량 제한
            quantityInput.value = quantity + 1;
            updateCartCount();
        }
    }

    function decreaseCartCount() {
        let quantity = parseInt(quantityInput.value);

        if (isNaN(quantity) || quantity <= 1) {
            quantity = 0;
        } else {
            quantity = quantity - 1;
        }
        quantityInput.value = quantity;
        updateCartCount();
    }

    // 이벤트 리스너 추가
    decreaseButton.addEventListener('click', function(event) {
        event.preventDefault();
        decreaseCartCount();
    });

    increaseButton.addEventListener('click', function(event) {
        event.preventDefault();
        increaseCartCount();
    });

    quantityInput.addEventListener('input', updateTotalPrice);

    // 초기 총 금액 설정
    updateTotalPrice();


    // 모달창 설정 기본 설정
    document.querySelector('.cart-form').addEventListener('submit', function(event) {
        event.preventDefault();
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
