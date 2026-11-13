import { createRouter, createWebHistory } from 'vue-router'

import LoginPage from '../pages/LoginPage.vue'
import RegisterPage from '../pages/RegisterPage.vue'
import BookListPage from '../pages/BookListPage.vue'
import BookFormPage from '../pages/BookFormPage.vue'
import IntroPage from '../components/IntroPage.vue'

const routes = [
    { path: '/', name: 'Intro', component: IntroPage },
    { path: '/login', name: 'Login', component: LoginPage, meta: { guestOnly: true } },
    { path: '/register', name: 'Register', component: RegisterPage, meta: { guestOnly: true } },
    { path: '/books', name: 'Books', component: BookListPage, meta: { requiresAuth: true } },
    { path: '/books/new', name: 'NewBook', component: BookFormPage, meta: { requiresAuth: true } },
    { path: '/books/:id/edit', name: 'EditBook', component: BookFormPage, meta: { requiresAuth: true } },
    { path: '/:pathMatch(.*)*', redirect: '/' },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

// Protect pages that need a login
router.beforeEach(to => {
    const loggedIn = !!localStorage.getItem('token')
    if (to.meta.requiresAuth && !loggedIn) return { name: 'Login' }
    if (to.meta.guestOnly && loggedIn) return { name: 'Books' }
})

export default router
