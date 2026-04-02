const API_BASE = '/api';

// Helper for making API requests with JWT Injection
async function fetchApi(endpoint, options = {}) {
    const token = localStorage.getItem('jwtToken');
    
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };

    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }

    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            ...options,
            headers
        });

        // Handle 401 Unauthorized globally (redirect to login)
        if (response.status === 403 || response.status === 401) {
            localStorage.removeItem('jwtToken');
            localStorage.removeItem('userRole');
            window.location.href = '/index.html';
            return null;
        }

        const contentType = response.headers.get("content-type");
        let data = null;
        if (contentType && contentType.indexOf("application/json") !== -1) {
             data = await response.json();
        } else {
             data = await response.text();
        }
        
        if (!response.ok) {
            throw new Error(data && data.message ? data.message : (typeof data === 'string' ? data : 'API Request Failed'));
        }

        return data;
    } catch (error) {
        showToast(error.message, 'error');
        throw error;
    }
}

function showToast(message, type = 'success') {
    let toast = document.getElementById('toast');
    if (!toast) {
        toast = document.createElement('div');
        toast.id = 'toast';
        document.body.appendChild(toast);
    }
    
    toast.className = `toast ${type}`;
    toast.innerText = message;
    
    // Trigger reflow
    void toast.offsetWidth;
    
    toast.classList.add('show');
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

function logout() {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userRole');
    window.location.href = '/index.html';
}

function checkAuth() {
    const token = localStorage.getItem('jwtToken');
    if (!token && window.location.pathname !== '/index.html' && window.location.pathname !== '/') {
        window.location.href = '/index.html';
    }
}

// Call on every page load except login
document.addEventListener('DOMContentLoaded', () => {
    checkAuth();
});
