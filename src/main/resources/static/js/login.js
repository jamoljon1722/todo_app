const loginForm = document.getElementById('loginForm');
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = new FormData(loginForm);
    const data = {
        username: formData.get('username'),
        password: formData.get('password')
    };
    
    try {
        const response = await fetch('http://192.168.100.74:9090/user/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            credentials: 'include',
            body: JSON.stringify(data)
        });

        if (response.ok) {
          
            const responseData = await response.json(); 
            localStorage.setItem("fullName", responseData.fullName); 

            alert('Muvaffaqiyatli kirildi!');
            window.location.href = "home.html";
        } else {
            alert('Login yoki parol noto‘g‘ri!');
        }
    } catch (err) {
        alert('Xatolik yuz berdi!');
        console.error(err);
    }
});


