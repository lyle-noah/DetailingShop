document.addEventListener('DOMContentLoaded', function() {
        const changeProfileBtn = document.getElementById('change-profile-btn');
        const profileFileInput = document.getElementById('profile-file');
        const profileForm = document.getElementById('profile-form');
        const profileImg = document.getElementById('profile-img');
        const body = document.body;

        changeProfileBtn.addEventListener('click', function() {
            profileFileInput.click();
        });

        profileFileInput.addEventListener('change', function() {
            if (profileFileInput.files.length > 0) {
                profileForm.submit();
            }
        });

        const fadeInElements = document.querySelectorAll('.fade-in-up');
        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                }
            });
        });

        fadeInElements.forEach(el => observer.observe(el));

        profileImg.addEventListener('mouseover', function() {
            const imgSrc = profileImg.src;
            body.style.setProperty('--bg-img', `url(${imgSrc})`);
            body.classList.add('bg-blur');
            adjustTextColor(imgSrc);
        });

        profileImg.addEventListener('mouseleave', function() {
            body.classList.remove('bg-blur', 'light-mode', 'dark-mode');
        });

        function adjustTextColor(imgSrc) {
            const img = new Image();
            img.src = imgSrc;
            img.onload = function() {
                const canvas = document.createElement('canvas');
                canvas.width = img.width;
                canvas.height = img.height;
                const ctx = canvas.getContext('2d');
                ctx.drawImage(img, 0, 0, img.width, img.height);

                const imageData = ctx.getImageData(0, 0, canvas.width, canvas.height);
                const data = imageData.data;
                let r, g, b, avg;
                let colorSum = 0;

                for (let x = 0; x < data.length; x += 4) {
                    r = data[x];
                    g = data[x + 1];
                    b = data[x + 2];
                    avg = Math.floor((r + g + b) / 3);
                    colorSum += avg;
                }

                const brightness = Math.floor(colorSum / (img.width * img.height));

                if (brightness > 130) { // 임계값: 130 (밝으면 검정색, 어두우면 흰색)
                    body.classList.add('light-mode');
                    body.classList.remove('dark-mode');
                } else {
                    body.classList.add('dark-mode');
                    body.classList.remove('light-mode');
                }
            };
        }
    });