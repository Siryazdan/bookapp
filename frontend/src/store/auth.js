import { defineStore } from 'pinia'
import api from '../api/axios'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || '',
    }),

    getters: {
        isAuthenticated: state => !!state.token,
    },

    actions: {
        setToken(token) {
            this.token = token
            localStorage.setItem('token', token)
        },

        async login(email, password) {
            const response = await api.post('/auth/login', { email, password })
            this.setToken(response.data.token)
        },

        async register(username, email, password) {
            const response = await api.post('/auth/register', { username, email, password })
            this.setToken(response.data.token)
        },

        logout() {
            this.token = ''
            localStorage.removeItem('token')
        },
    },
})
