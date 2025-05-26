import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || '',
        isAuthenticated: !!localStorage.getItem('token'),
    }),

    actions: {
        async login(email, password) {
            try {
                const response = await axios.post('http://localhost:8081/api/auth/login', {
                    email,
                    password,
                })

                this.token = response.data.token
                this.isAuthenticated = true
                localStorage.setItem('token', this.token)
                axios.defaults.headers.common['Authorization'] = `Bearer ${this.token}`
            } catch (error) {
                console.error('Login failed:', error)
                throw error
            }
        },

        async register(username, email, password) {
            try {
                const response = await axios.post('http://localhost:8081/api/auth/register', {
                    username,
                    email,
                    password,
                })

                this.token = response.data.token
                this.isAuthenticated = true
                localStorage.setItem('token', this.token)
                axios.defaults.headers.common['Authorization'] = `Bearer ${this.token}`
            } catch (error) {
                console.error('Registration failed:', error.response?.data || error.message)
                throw error
            }
        },

        logout() {
            this.token = ''
            this.isAuthenticated = false
            localStorage.removeItem('token')
            delete axios.defaults.headers.common['Authorization']
        }
    }
})
