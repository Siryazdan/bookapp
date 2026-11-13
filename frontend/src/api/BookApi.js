import axios from './axios' // configured axios instance (adds the JWT automatically)

export const getAllBooks = () => axios.get('/books')
export const getBook = id => axios.get(`/books/${id}`)
export const createBook = book => axios.post('/books', book)
export const updateBook = (id, book) => axios.put(`/books/${id}`, book)
export const deleteBook = id => axios.delete(`/books/${id}`)
