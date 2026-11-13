<template>
  <div class="min-h-screen bg-gray-100 text-black dark:bg-gray-900 dark:text-white transition-colors duration-300">
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
                : 'bg-white text-gray-700 hover:bg-blue-100 dark:bg-gray-800 dark:text-gray-200 dark:hover:bg-gray-700'
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
              @edit="handleEdit"
              @delete="handleDelete"
          />
        </div>
      </section>

      <!-- To Read -->
      <section v-if="toReadBooks.length > 0" class="mb-10">
        <h2 class="text-xl font-semibold mb-3 text-gray-700 dark:text-white">📖 To Read</h2>
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
          <BookCard
              v-for="book in toReadBooks"
              :key="book.id"
              :book="book"
              @favorite="markAsFavorite"
              @toRead="markAsToRead"
              @edit="handleEdit"
              @delete="handleDelete"
          />
        </div>
      </section>

      <!-- All Books (Filtered) -->
      <section>
        <h2 class="text-xl font-semibold mb-3 text-gray-700 dark:text-white">
          {{ selectedCategory ? `📂 ${selectedCategory} Books` : '🗂️ All Books' }}
        </h2>
        <div v-if="loading" class="text-gray-500">Loading books…</div>
        <div v-else-if="loadError" class="text-red-500">{{ loadError }}</div>
        <div v-else-if="filteredBooks.length === 0" class="text-gray-500">No books found.</div>
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
import { ref, onMounted, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
import { deleteBook, getAllBooks } from '../api/BookApi.js'
import { GENRES } from '../genres.js'
import Navbar from '../components/Navbar.vue'
import BookCard from '../components/BookCard.vue'

const router = useRouter()
const books = ref([])
const loading = ref(true)
const loadError = ref('')
const selectedCategory = ref(null)

const categories = ['All', ...GENRES]

// Favorites and "to read" are saved in the browser, so they survive a page reload
const loadIds = key => {
  try {
    return JSON.parse(localStorage.getItem(key)) || []
  } catch {
    return []
  }
}
const favoriteIds = ref(loadIds('favoriteBooks'))
const toReadIds = ref(loadIds('toReadBooks'))
watch(favoriteIds, ids => localStorage.setItem('favoriteBooks', JSON.stringify(ids)), { deep: true })
watch(toReadIds, ids => localStorage.setItem('toReadBooks', JSON.stringify(ids)), { deep: true })

const filterCategory = (category) => {
  selectedCategory.value = category === 'All' ? null : category
}

const filteredBooks = computed(() =>
    selectedCategory.value
        ? books.value.filter((book) => book.genre === selectedCategory.value)
        : books.value
)

const favoriteBooks = computed(() => books.value.filter((b) => favoriteIds.value.includes(b.id)))
const toReadBooks = computed(() => books.value.filter((b) => toReadIds.value.includes(b.id)))

const toggleId = (list, id) => {
  const index = list.value.indexOf(id)
  if (index === -1) list.value.push(id)
  else list.value.splice(index, 1)
}

const markAsFavorite = (id) => toggleId(favoriteIds, id)
const markAsToRead = (id) => toggleId(toReadIds, id)

const handleEdit = (id) => {
  router.push(`/books/${id}/edit`)
}

const handleDelete = async (id) => {
  if (!confirm('Are you sure you want to delete this book?')) return

  try {
    await deleteBook(id)
    books.value = books.value.filter((b) => b.id !== id)
    favoriteIds.value = favoriteIds.value.filter((x) => x !== id)
    toReadIds.value = toReadIds.value.filter((x) => x !== id)
  } catch (error) {
    alert('Could not delete the book.')
  }
}

onMounted(async () => {
  try {
    const response = await getAllBooks()
    books.value = response.data
  } catch (error) {
    loadError.value = 'Could not load your books. Is the backend running?'
  } finally {
    loading.value = false
  }
})
</script>
