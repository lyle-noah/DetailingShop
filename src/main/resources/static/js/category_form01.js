// 상품목록페이지 기능 중 ADD 버튼을 눌렀을때 동작 로직 정리
document.querySelectorAll('.add-to-cart-form').forEach(form => {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const actionUrl = form.getAttribute('action');

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
            } else if (html.includes('alertMessage_form01')) {
                alert('로그인이 필요합니다.');
                window.location.href = '/user/login?redirectUrl=' + encodeURIComponent(window.location.href);
            }
        })
        .catch(error => console.error('Error:', error));
    });
});

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
