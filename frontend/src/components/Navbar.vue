<template>
  <nav class="bg-white text-black dark:bg-gray-900 dark:text-white transition-colors duration-300 shadow-md py-4 px-6 flex justify-between items-center">
    <!-- Logo or App Name -->
    <router-link to="/books" class="text-xl font-bold text-blue-600 hover:text-blue-800">
      BookApp
    </router-link>

    <!-- Navigation Buttons -->
    <div class="flex items-center gap-4">
      <router-link
          to="/books/new"
          class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
      >
        + Add Book
      </router-link>
      <button
          @click="logout"
          class="text-gray-600 hover:text-red-500 transition text-sm"
      >
        Logout
      </button>
      <button @click="toggleDark" class="text-gray-700 dark:text-gray-300 hover:text-black transition">
        {{ isDark ? '🌙 Dark Mode' : '☀️ Light Mode' }}
      </button>
    </div>
  </nav>
</template>

<script setup>
import {useRouter} from 'vue-router'
import {useAuthStore} from '../store/auth.js'
import {ref} from "vue";

const authStore = useAuthStore()
const router = useRouter()

function logout() {
  authStore.logout()
  router.push('/login')
}
const isDark = ref(false)

function toggleDark() {
  isDark.value = !isDark.value

  if (isDark.value) {
    document.documentElement.classList.add('dark')
  } else {
    document.documentElement.classList.remove('dark')
  }
}


</script>
