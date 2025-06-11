const registerForm = document.getElementById('registerForm');
registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = new FormData(registerForm);
    const data = {
        fullName: formData.get('fullName'),
        username: formData.get('username'),
        password: formData.get('password')
    };
    
    try {
        const response = await fetch('http://192.168.100.74:9090/user/new', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            alert('Muvaffaqiyatli ro‘yxatdan o‘tildi! Endi tizimga kirishingiz mumkin.');
            window.location.href = 'login.html';
        } else {
            alert('Xatolik yuz berdi!');
        }
    } catch (err) {
        alert('Server bilan aloqa bo‘lmadi!');
        console.error(err);
    }
});




