    // 전체 선택 기능
    document.getElementById('select-all').addEventListener('change', function(e) {
        const checkboxes = document.querySelectorAll('input[name="productCheckbox"]');
        checkboxes.forEach(checkbox => {
            checkbox.checked = e.target.checked;
        });
    });

    // 수량 변경 시 총 금액 업데이트
function updateTotalPrice(input) {
    const form = input.closest('form');
    const formData = new FormData(form);

    fetch(form.action, {
        method: 'POST',
        body: formData,
        headers: {
            'X-Requested-With': 'XMLHttpRequest',
            'Accept': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if (data.totalPrice !== undefined) {
            document.getElementById('total-price').innerText = '₩' + new Intl.NumberFormat().format(data.totalPrice);
        } else if (data.error) {
            console.error('Error:', data.error);
        }
    })
    .catch(error => console.error('Error updating total price:', error));
}

// 아임포트 결제 요청 처리
function requestPay() {
    const IMP = window.IMP; // 생략 가능
    IMP.init('imp02854257'); // 아임포트 식별코드

    // 결제 정보 설정
    IMP.request_pay({
        pg: 'kakao', // 결제 방식 (카카오페이)
        pay_method: 'card', // 결제 수단
        merchant_uid: 'order_' + new Date().getTime(), // 주문번호
        name: '주문명: 결제 테스트',
        amount: parseFloat(document.getElementById('total-price').innerText.replace(/[^0-9.-]+/g,"")), // 결제 금액
        buyer_email: 'buyer@example.com',
        buyer_name: '구매자 이름',
        buyer_tel: '010-1234-5678',
        buyer_addr: '서울특별시 강남구 삼성동',
        buyer_postcode: '123-456'
    }, function (rsp) {
        if (rsp.success) {
            // 결제 성공 시 처리 로직
            window.location.href = '/pay/success?imp_uid=' + rsp.imp_uid + '&merchant_uid=' + rsp.merchant_uid;
        } else {
            // 결제 실패 시 처리 로직
            alert('결제에 실패하였습니다. 에러 내용: ' + rsp.error_msg);
        }
    });
}