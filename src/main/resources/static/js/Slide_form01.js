(function() {
    let currentIndex1 = 0;
    const slides1 = document.querySelectorAll('.slides1 .slide');
    const dots1 = document.querySelectorAll('.dots1 .dot');

    function showSlide1(index) {
        if (index >= slides1.length) {
            currentIndex1 = 0;
        } else if (index < 0) {
            currentIndex1 = slides1.length - 1;
        } else {
            currentIndex1 = index;
        }

        const offset1 = -currentIndex1 * 100;
        document.querySelector('.slides1').style.transform = `translateX(${offset1}%)`;

        dots1.forEach(dot => dot.classList.remove('active'));
        dots1[currentIndex1].classList.add('active');
    }

    function nextSlide1() {
        showSlide1(currentIndex1 + 1);
    }

    function prevSlide1() {
        showSlide1(currentIndex1 - 1);
    }

    document.querySelector('.next1').addEventListener('click', nextSlide1);
    document.querySelector('.prev1').addEventListener('click', prevSlide1);

    dots1.forEach(dot => {
        dot.addEventListener('click', (event) => {
            showSlide1(parseInt(event.target.getAttribute('data-slide')));
        });
    });

    setInterval(nextSlide1, 5000);

    showSlide1(currentIndex1);
})();
