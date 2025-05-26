import {createRouter, createWebHistory} from 'vue-router'

// Import pages (we'll create these files shortly)
import LoginPage from '../pages/LoginPage.vue'
import RegisterPage from '../pages/RegisterPage.vue'
import BookListPage from '../pages/BookListPage.vue'
import BookFormPage from "../pages/BookFormPage.vue";
import IntroPage from "../components/IntroPage.vue";

const routes = [
    {path: '/', name: 'Intro', component: IntroPage},
    {path: '/login', component: LoginPage},
    {path: '/register', component: RegisterPage},
    {path: '/books', component: BookListPage},
    {path: '/books/new', component: BookFormPage},
    {path: '/books/:id/edit', name: 'EditBook', component: BookFormPage},
    {path: '/', redirect: '/books'},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
