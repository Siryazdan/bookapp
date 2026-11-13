import axios from 'axios'

// Backend URL. Can be changed with VITE_API_URL in a .env file.
const instance = axios.create({
    baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8081/api',
})

// Send the JWT with every request (if logged in)
instance.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = `Bearer ${token}`
    }
    return config
})

// Token missing or expired -> log out and go to the login page
instance.interceptors.response.use(
    response => response,
    error => {
        const isAuthCall = error.config?.url?.startsWith('/auth/')
        if (error.response?.status === 401 && !isAuthCall) {
            localStorage.removeItem('token')
            if (window.location.pathname !== '/login') {
                window.location.href = '/login'
            }
        }
        return Promise.reject(error)
    }
)

export default instance
