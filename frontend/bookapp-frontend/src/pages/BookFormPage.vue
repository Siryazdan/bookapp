<template>
  <div class="min-h-screen bg-gradient-to-br from-white to-blue-100 dark:from-gray-800 dark:to-gray-900 text-black dark:text-white flex justify-center items-center px-4 py-12">
    <div class="bg-white dark:bg-gray-800 shadow-2xl rounded-2xl p-8 w-full max-w-2xl animate-fade-in">
      <h1 class="text-3xl font-bold mb-8 text-center text-gray-800 dark:text-white">
        {{ isEditMode ? '✏️ Edit Book' : '📘 Create New Book' }}
      </h1>

      <form @submit.prevent="handleSubmit" class="space-y-6">
        <div>
          <label class="block mb-1 font-semibold text-gray-700 dark:text-gray-300">Title</label>
          <input v-model="book.title" required type="text"
                 class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition" />
        </div>

        <div>
          <label class="block mb-1 font-semibold text-gray-700 dark:text-gray-300">Author</label>
          <input v-model="book.author" required type="text"
                 class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition" />
        </div>

        <div>
          <label class="block mb-1 font-semibold text-gray-700 dark:text-gray-300">Genre</label>
          <select v-model="book.genre" required
                  class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition">
            <option value="" disabled>Select genre</option>
            <option value="Sci-Fi">Fiction</option>
            <option value="Romance">Non-Fiction</option>
            <option value="Fantasy">Fantasy</option>
            <option value="Mystery">Mystery</option>
            <option value="Horror">Biography</option>
            <option value="History">History</option>
          </select>
        </div>

        <div>
          <label class="block mb-1 font-semibold text-gray-700 dark:text-gray-300">Publication Year</label>
          <input type="number" v-model.number="book.publicationYear" required
                 class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition" />
        </div>

        <div>
          <label class="block mb-1 font-semibold text-gray-700 dark:text-gray-300">Description</label>
          <textarea v-model="book.description" required
                    class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 resize-none h-32 focus:outline-none focus:ring-2 focus:ring-blue-500 transition"></textarea>
        </div>

        <div>
          <label class="block mb-1 font-semibold text-gray-700 dark:text-gray-300">Cover Image URL</label>
          <input v-model="book.coverImageUrl" @input="updatePreview"
                 class="w-full px-4 py-2 rounded-lg border border-gray-300 dark:border-gray-600 bg-gray-50 dark:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 transition" />
        </div>

        <div v-if="previewUrl" class="flex justify-center mt-4">
          <img :src="previewUrl" alt="Cover Preview" class="w-40 h-60 object-cover rounded-lg shadow-md" />
        </div>

        <div class="space-y-3">
          <button type="submit"
                  class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition">
            {{ isEditMode ? 'Update Book' : 'Create Book' }}
          </button>

          <button v-if="isEditMode" @click="handleDelete" type="button"
                  class="w-full bg-red-600 hover:bg-red-700 text-white font-semibold py-3 rounded-lg transition">
            🗑️ Delete Book
          </button>
        </div>
      </form>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { createBook, updateBook, getBook, deleteBook } from '../api/BookApi.js'

const route = useRoute()
const router = useRouter()
const isEditMode = computed(() => !!route.params.id)

const book = ref({
  title: '',
  author: '',
  genre: '',
  publicationYear: new Date().getFullYear(),
  description: '',
  coverImageUrl: '',
})

const previewUrl = ref('')

const updatePreview = () => {
  previewUrl.value = book.value.coverImageUrl || ''
}

onMounted(async () => {
  console.log("BookFormPage mounted. Route ID:", route.params.id)
  if (isEditMode.value) {
    try {
      const response = await getBook(route.params.id)
      book.value = response.data
      updatePreview()
    } catch (error) {
      console.error('Error fetching book:', error)
    }
  }
})

const handleSubmit = async () => {
  try {
    if (isEditMode.value) {
      await updateBook(route.params.id, book.value)
    } else {
      await createBook(book.value)
    }
    await router.push('/books')
  } catch (error) {
    console.error('Error saving book:', error)
  }
}

const handleDelete = async () => {
  const confirmed = confirm("Are you sure you want to delete this book?");
  if (!confirmed) return;

  try {
    await deleteBook(route.params.id)
    await router.push('/books')
  } catch (error) {
    console.error('Error deleting book:', error)
  }
}
</script>
