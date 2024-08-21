// modal.js

document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('cart-modal');
    const closeButton = modal.querySelector('.close-button');
    const addToCartForms = document.querySelectorAll('form[action="/cart/add"]');

    addToCartForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault(); // Prevent the default form submission

            const formData = new FormData(this);

            fetch(this.action, {
                method: 'POST',
                body: formData
            })
            .then(response => {
                if (response.ok) {
                    // Show the modal if the request was successful
                    modal.classList.remove('hidden');
                } else {
                    // Handle error
                    console.error('Error adding item to cart');
                }
            })
            .catch(error => console.error('Error:', error));
        });
    });

    closeButton.addEventListener('click', function() {
        modal.classList.add('hidden');
    });

    document.querySelector('#cart-modal .btn').addEventListener('click', function() {
        window.location.href = '/cart'; // Redirect to cart page
    });

    document.querySelector('#cart-modal .continue-shopping-button').addEventListener('click', function() {
        modal.classList.add('hidden');
    });
});
