import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { autoAnimatePlugin } from '@formkit/auto-animate/vue'
import App from './App.vue'
import router from './router'
import './assets/main.css'

// Restore dark mode from the last visit
if (localStorage.getItem('theme') === 'dark') {
    document.documentElement.classList.add('dark')
}

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(autoAnimatePlugin)

app.mount('#app')
