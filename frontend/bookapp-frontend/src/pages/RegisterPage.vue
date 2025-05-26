<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-100 to-green-300">
    <div class="bg-white shadow-lg rounded-2xl p-8 w-full max-w-md">
      <h2 class="text-2xl font-bold text-center mb-6 text-gray-800">Register</h2>
      <form @submit.prevent="handleRegister" class="space-y-4">
        <input
            v-model="username"
            type="text"
            placeholder="Username"
            required
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
        />
        <input
            v-model="email"
            type="email"
            placeholder="Email"
            required
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
        />
        <input
            v-model="password"
            type="password"
            placeholder="Password"
            required
            class="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400"
        />
        <button
            type="submit"
            class="w-full bg-green-500 text-white py-2 rounded-lg hover:bg-green-600 transition"
        >
          Register
        </button>
      </form>
      <p v-if="error" class="text-red-500 text-sm mt-4 text-center">{{ error }}</p>
      <p class="text-center text-sm mt-6 text-gray-600">
        Already have an account?
        <router-link to="/login" class="text-green-600 hover:underline">Login</router-link>
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../store/auth'
import { useRouter } from 'vue-router'

const username = ref('')
const email = ref('')
const password = ref('')
const error = ref('')
const authStore = useAuthStore()
const router = useRouter()

async function handleRegister() {
  try {
    await authStore.register(username.value, email.value, password.value)
    router.push('/books')
  } catch (err) {
    error.value = 'Registration failed. Email may already exist.'
  }
}
</script>
