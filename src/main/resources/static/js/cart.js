document.addEventListener("DOMContentLoaded", function() {
    console.log("DOM fully loaded and parsed");

    // 삭제 버튼 클릭 이벤트 처리
    document.querySelectorAll('.cart-delete-btn').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();

            const form = this.closest('form');
            let cartStateInput = form.querySelector('input[name="cartState"]');

            if (!cartStateInput) {
                cartStateInput = document.createElement('input');
                cartStateInput.type = 'hidden';
                cartStateInput.name = 'cartState';
                cartStateInput.value = 'false'; // 장바구니 상태를 false로 변경
                form.appendChild(cartStateInput);
            }

            const confirmDelete = confirm("선택하신 상품을 삭제하시겠습니까?");
            if (confirmDelete) {
                form.submit(); // 삭제 요청을 서버로 전송
            }
        });
    });

    // 장바구니 수량 감소 버튼 클릭 이벤트 처리
    document.querySelectorAll('.quantity-decrease-btn').forEach(button => {
        button.addEventListener('click', function() {
            const quantityInput = this.closest('.quantity-control-wrapper').querySelector('.cart-item-quantity');
            let quantity = parseInt(quantityInput.value);

            if (isNaN(quantity) || quantity <= 1) {
                quantity = 1;
            } else {
                quantity--;
            }
            quantityInput.value = quantity;

            submitFormAndReload(this.closest('form'));
        });
    });

    // 장바구니 수량 증가 버튼 클릭 이벤트 처리
    document.querySelectorAll('.quantity-increase-btn').forEach(button => {
        button.addEventListener('click', function() {
            const quantityInput = this.closest('.quantity-control-wrapper').querySelector('.cart-item-quantity');
            let quantity = parseInt(quantityInput.value);

            if (isNaN(quantity)) {
                quantity = 1;
            } else if (quantity < 99) {
                quantity++;
            }
            quantityInput.value = quantity;

            submitFormAndReload(this.closest('form'));
        });
    });

    // 수량 입력창에서 숫자 변경 이벤트 처리
    document.querySelectorAll('.cart-item-quantity').forEach(input => {
        input.addEventListener('change', function() {
            let quantity = parseInt(this.value);

            if (isNaN(quantity) || quantity < 1) {
                quantity = 1;
            } else if (quantity > 99) {
                quantity = 99;
            }
            this.value = quantity;
        });
    });

    // 장바구니 수량 변경 버튼 클릭 시 페이지 새로고침
    document.querySelectorAll('.cart-update-btn').forEach(button => {
        button.addEventListener('click', function(event) {
            event.preventDefault();

            submitFormAndReload(this.closest('form'));
        });
    });

    // 입력된 폼 데이터를 서버로 전송하고 페이지를 새로고침하는 함수
    function submitFormAndReload(form) {
        const formData = new FormData(form);

        fetch(form.action, {
            method: 'POST',
            body: formData,
        })
        .then(response => {
            if (response.ok) {
                location.reload(); // 서버에 업데이트 후 페이지 새로고침
            } else {
                console.error('Failed to update cart item');
            }
        })
        .catch(error => console.error('Error:', error));
    }
});

/*
// 아임포트 결제 요청 처리
function requestPay() {
    const IMP = window.IMP;
    IMP.init('imp02854257');

    IMP.request_pay({
        pg: 'kakao',
        pay_method: 'card',
        merchant_uid: 'order_' + new Date().getTime(),
        name: '주문명: 결제 테스트',
        amount: parseFloat(document.getElementById('total-price').innerText.replace(/[^0-9.-]+/g,"")),
        buyer_email: 'buyer@example.com',
        buyer_name: '구매자 이름',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456'
    }, function (rsp) {
        if (rsp.success) {
            window.location.href = '/pay/success?imp_uid=' + rsp.imp_uid + '&merchant_uid=' + rsp.merchant_uid;
        } else {
            alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
        }
    });
}
*/
