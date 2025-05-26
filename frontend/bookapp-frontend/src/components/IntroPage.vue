<template>
  <div class="relative h-screen bg-gradient-to-r from-purple-600 via-pink-500 to-red-500 animate-gradient-x">
    <div class="absolute inset-0 bg-black bg-opacity-50"></div>
    <div class="relative z-10 flex flex-col items-center justify-center h-full text-center text-white px-4">
      <h1 class="text-5xl md:text-6xl font-bold mb-4">Welcome to Armin's Library</h1>
      <p class="text-xl md:text-2xl mb-6 max-w-2xl">
        Hi, I'm Armin. I love reading books, and that's why I built this Web app—to share my passion and help you manage your personal library.
      </p>
      <router-link to="/books">
        <button class="bg-white text-purple-700 font-semibold py-3 px-6 rounded-lg shadow-lg hover:bg-purple-100 transition duration-300">
          Explore My Library
        </button>
      </router-link>
      <div class="flex justify-between w-full max-w-2xl mt-6 px-4">
        <router-link to="/register">
          <button class="bg-transparent text-white border border-white font-semibold py-2 px-4 rounded-lg hover:bg-white hover:text-purple-700 transition duration-300">
            Register
          </button>
        </router-link>
        <router-link to="/login">
          <button class="bg-transparent text-white border border-white font-semibold py-2 px-4 rounded-lg hover:bg-white hover:text-purple-700 transition duration-300">
            Login
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
@keyframes gradient-x {
  0%, 100% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
}

.animate-gradient-x {
  background-size: 200% 200%;
  animation: gradient-x 10s ease infinite;
}
</style>
