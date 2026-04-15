/* api/api.js - API calls abstraction */
const BASE_API = '/api/v1';

async function performLogin(event) {
    event.preventDefault();
    
    const usernameInput = document.getElementById('username').value;
    const passwordInput = document.getElementById('password').value;
    const btn = document.getElementById('loginBtn');
    const btnText = btn.innerHTML;
    
    btn.innerHTML = `<span style="opacity:0.7">Kutilmoqda...</span>`;
    btn.disabled = true;
    
    try {
        const res = await fetch(`${BASE_API}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: usernameInput, password: passwordInput })
        });
        
        const data = await res.json();
        
        if(res.ok) {
            localStorage.setItem('token', data.token);
            navigateTo('dashboardPage'); 
            loadDashboardView('home');
        } else {
            alert(data.message || 'Login xato');
        }
    } catch(err) {
        console.error(err);
        alert('Tarmoq xatosi');
    } finally {
        btn.innerHTML = btnText;
        btn.disabled = false;
    }
}
