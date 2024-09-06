// 좋아요 버튼을 눌렀을때 동작 로직 정리
document.querySelectorAll('.overlay form').forEach(form => {
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(form);
        const actionUrl = form.getAttribute('action');
        const button = form.querySelector('button');

        // 현재 페이지 URL 추가 (인코딩하여 서버에 전송)
        const currentUrl = window.location.href;
        formData.append('redirectUrl', encodeURIComponent(currentUrl));  // URL을 인코딩하여 전송

        fetch(actionUrl, {
            method: 'POST',
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            if (data.redirect) {
                // 로그인 페이지로 리다이렉트
                window.location.href = data.redirect;
            } else if (data.likeState !== undefined) {
                // 좋아요 상태에 따라 버튼 텍스트 변경
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
