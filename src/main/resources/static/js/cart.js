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