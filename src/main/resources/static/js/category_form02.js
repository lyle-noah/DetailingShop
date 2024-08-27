// 좋아요 버튼을 눌렀을때 동작 로직 정리
document.querySelectorAll('.overlay form').forEach(form => {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const actionUrl = form.getAttribute('action');
        const button = form.querySelector('button');

        fetch(actionUrl, {
            method: 'POST',
            body: formData,
        })
        .then(response => response.json())
        .then(data => {

//            console.log('Server response:', data); // 서버 응답 확인

            if (data.redirect) {
                // 비로그인 상태: 로그인 페이지로 리다이렉트
                if (data.message) {
                    alert(data.message); // 로그인 필요 알림
                }
                window.location.href = data.redirect;
            } else if (data.likeState !== undefined) {
                // 서버의 응답에 따라 버튼 상태 변경
                if (data.likeState) {
                    button.classList.add('WISHED');
                    button.textContent = 'WISHED';
                } else {
                    button.classList.remove('WISHED');
                    button.textContent = 'WISH';
                }
            }
        })
        .catch(error => console.error('Error:', error));
    });
});