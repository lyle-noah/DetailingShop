// 제품상세페이지(detail.html)의 likeButton_form01의 비동기 처리 로직
document.addEventListener("DOMContentLoaded", function() {
    const likeButtonForm = document.querySelector('.like-icon form');

    likeButtonForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const formData = new FormData(likeButtonForm);
        const actionUrl = likeButtonForm.getAttribute('action');
        const likeButtonImage = likeButtonForm.querySelector('.like-button-img');

        fetch(actionUrl, {
            method: 'POST',
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            if (data.redirect) {
                if (data.message) {
                    alert(data.message);
                }
                window.location.href = data.redirect;
            } else if (data.likeState !== undefined) {
                // 서버 응답에 따라 버튼 이미지 변경
                if (data.likeState) {
                    likeButtonImage.src = '/img/icon/icon_LikeButton2.png';
                } else {
                    likeButtonImage.src = '/img/icon/icon_LikeButton1.png';
                }
            }
        })
        .catch(error => console.error('Error:', error));
    });
});