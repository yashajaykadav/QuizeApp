function toggleMode(mode) {
    document.getElementById('loginForm').style.display = mode === 'login' ? 'block' : 'none';
    document.getElementById('registerForm').style.display = mode === 'register' ? 'block' : 'none';
}

async function handleLogin(e) {
    e.preventDefault();
    const btn = document.getElementById('loginBtn');
    btn.innerText = 'Signing in...';
    btn.disabled = true;

    try {
        const payload = {
            email: document.getElementById('loginEmail').value,
            password: document.getElementById('loginPassword').value
        };

        const res = await fetch('/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const err = await res.text();
            throw new Error(err || 'Invalid Credentials');
        }

        const data = await res.json();
        localStorage.setItem('jwtToken', data.token);
        localStorage.setItem('userRole', data.role);
        
        if (data.role === 'STUDENT') {
            window.location.href = '/student-dashboard.html';
        } else if (data.role === 'ADMIN') {
            window.location.href = '/admin-dashboard.html';
            showToast('Admin Dashboard coming soon... Redirecting temporarily.', 'warning');
        }
        
    } catch (err) {
        showToast(err.message, 'error');
    } finally {
        btn.innerText = 'Sign In';
        btn.disabled = false;
    }
}

async function handleRegister(e) {
    e.preventDefault();
    const btn = document.getElementById('regBtn');
    btn.innerText = 'Registering...';
    btn.disabled = true;

    try {
        const payload = {
            name: document.getElementById('regName').value,
            email: document.getElementById('regEmail').value,
            password: document.getElementById('regPassword').value,
            role: document.getElementById('regRole').value
        };

        const res = await fetch('/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const err = await res.text();
            throw new Error(err || 'Registration failed');
        }

        showToast('Registration Successful! Please log in.');
        toggleMode('login');
    } catch (err) {
        showToast(err.message, 'error');
    } finally {
        btn.innerText = 'Create Account';
        btn.disabled = false;
    }
}

// Redirect if already logged in!
document.addEventListener('DOMContentLoaded', () => {
    if (localStorage.getItem('jwtToken')) {
        const role = localStorage.getItem('userRole');
        if (role === 'STUDENT') {
            window.location.href = '/student-dashboard.html';
        } else if (role === 'ADMIN') {
            window.location.href = '/admin-dashboard.html';
        }
    }
});
