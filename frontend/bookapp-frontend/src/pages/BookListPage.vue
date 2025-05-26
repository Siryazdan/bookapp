<template>
  <div class="min-h-screen -gray-100 text-black dark:bg-gray-900 dark:text-white transition-colors duration-300">
    <Navbar />

    <div class="container mx-auto p-6">
      <!-- Category Filter -->
      <section class="mb-8">
        <h1 class="text-3xl font-bold text-gray-800 dark:text-white mb-4">📚 Explore Books</h1>
        <div class="flex flex-wrap gap-3">
          <button
              v-for="cat in categories"
              :key="cat"
              @click="filterCategory(cat)"
              :class="[
              'px-4 py-2 rounded-full border text-sm transition',
              selectedCategory === cat
                ? 'bg-blue-500 text-white'
                : 'bg-white text-gray-700 hover:bg-blue-100'
            ]"
          >
            {{ cat }}
          </button>
        </div>
      </section>

      <!-- Favorite Books -->
      <section v-if="favoriteBooks.length > 0" class="mb-10">
        <h2 class="text-xl font-semibold mb-3 text-gray-700 dark:text-white">❤️ Favorite Books</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          <BookCard
              v-for="book in favoriteBooks"
              :key="book.id"
              :book="book"
              @favorite="markAsFavorite"
              @toRead="markAsToRead"
          />
        </div>
      </section>

      <!-- To Read -->
      <section v-if="toReadBooks.length > 0" class="mb-10">
        <h2 class="text-xl font-semibold mb-3 text-gray-700">📖 To Read</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          <BookCard
              v-for="book in toReadBooks"
              :key="book.id"
              :book="book"
              @favorite="markAsFavorite"
              @toRead="markAsToRead"
          />
        </div>
      </section>

      <!-- All Books (Filtered) -->
      <section>
        <h2 class="text-xl font-semibold mb-3 text-gray-700 dark:text-white">
          {{ selectedCategory ? `📂 ${selectedCategory} Books` : '🗂️ All Books' }}
        </h2>
        <div v-if="filteredBooks.length === 0" class="text-gray-500">No books found.</div>
        <div v-else class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          <BookCard
              v-for="book in filteredBooks"
              :key="book.id"
              :book="book"
              @favorite="markAsFavorite"
              @toRead="markAsToRead"
              @edit="handleEdit"
              @delete="handleDelete"
          />
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { deleteBook, getAllBooks } from '../api/BookApi.js'
import Navbar from '../components/Navbar.vue'
import BookCard from '../components/BookCard.vue'
import router from '../router/index.js'

const books = ref([])
const selectedCategory = ref(null)

const categories = ['All', 'Sci-Fi', 'Romance', 'Fantasy', 'Mystery', 'Horror', 'History']

const filterCategory = (category) => {
  selectedCategory.value = category === 'All' ? null : category
}

const filteredBooks = computed(() => {
  return selectedCategory.value
      ? books.value.filter((book) => book.genre === selectedCategory.value)
      : books.value
})

const favoriteBooks = computed(() => books.value.filter((b) => b.isFavorite))
const toReadBooks = computed(() => books.value.filter((b) => b.status === 'to-read'))

const markAsFavorite = (id) => {
  const book = books.value.find((b) => b.id === id)
  if (book) book.isFavorite = !book.isFavorite
}

const markAsToRead = (id) => {
  const book = books.value.find((b) => b.id === id)
  if (book) {
    book.status = book.status === 'to-read' ? null : 'to-read'
  }
}

const handleEdit = (id) => {
  router.push(`/books/${id}/edit`)
}

const handleDelete = async (id) => {
  const confirmed = confirm('Are you sure you want to delete this book?')
  if (!confirmed) return

  try {
    await deleteBook(id)
    books.value = books.value.filter((b) => b.id !== id)
  } catch (error) {
    console.error('Error deleting book:', error)
  }
}

onMounted(async () => {
  try {
    const response = await getAllBooks()
    books.value = response.data
  } catch (error) {
    console.error('Error fetching books:', error)
  }
})
</script>

