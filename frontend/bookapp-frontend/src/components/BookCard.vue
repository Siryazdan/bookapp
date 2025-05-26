<template>
  <div class="bg-white dark:bg-gray-800 rounded-2xl shadow-md hover:shadow-lg transition-all duration-300 p-5 w-full h-full flex flex-col justify-between">
    <!-- Book Cover -->
    <div v-if="book.coverImageUrl" class="mb-4">
      <img
          v-if="book.coverImageUrl"
          :src="book.coverImageUrl"
          alt="Cover Image"
          class="w-full max-h-[400px] object-contain rounded-xl border border-gray-200 dark:border-gray-600 mb-4"
      />
    </div>

    <!-- Book Info -->
    <div class="text-center space-y-2">
      <h3 class="text-xl font-extrabold text-gray-800 dark:text-white truncate">
        {{ book.title }}
      </h3>
      <p class="text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Author:</span> {{ book.author }}</p>
      <p class="text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Genre:</span> {{ book.genre }}</p>
      <p class="text-sm text-gray-500 dark:text-gray-400"><span class="font-semibold">Year:</span> {{ book.publicationYear }}</p>
      <p class="text-sm text-gray-600 dark:text-gray-300 truncate"><span class="font-semibold">Description:</span> {{ book.description }}</p>
    </div>

    <!-- Action Buttons -->
    <div class="flex justify-around items-center mt-6">
      <button @click="$emit('favorite', book.id)"
              class="text-red-500 hover:text-red-600 dark:hover:text-red-400 transition transform hover:scale-110"
              title="Add to favorites">
        ❤️
      </button>

      <button @click="$emit('toRead', book.id)"
              class="text-blue-500 hover:text-blue-600 dark:hover:text-blue-400 transition transform hover:scale-110"
              title="Add to To-Read">
        📘
      </button>

      <button @click="$emit('edit', book.id)"
              class="text-yellow-500 hover:text-yellow-600 dark:hover:text-yellow-400 transition transform hover:scale-110"
              title="Edit Book">
        ✏️
      </button>

      <button @click="$emit('delete', book.id)"
              class="text-red-600 hover:text-red-700 dark:hover:text-red-500 transition transform hover:scale-110"
              title="Delete Book">
        🗑️
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps(['book'])
defineEmits(['favorite', 'toRead', 'edit', 'delete'])
</script>


