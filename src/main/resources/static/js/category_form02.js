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
        .then(response => response.json()) // 서버가 JSON 형식으로 응답
        .then(data => {
            if (data.redirect) {
                if (data.message) {
                    alert(data.message);  // alertMessage_form01의 기능을 비동기 처리로 대체
                }
                window.location.href = data.redirect;
            } else if (data.likeState !== undefined) {
                // 좋아요 상태 변경
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