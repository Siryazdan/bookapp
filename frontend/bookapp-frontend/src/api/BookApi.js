import axios from './axios' // ✅ your configured axios instance

export function createBook(book) {
    return axios.post('/books', book, {
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
        }
    })
}
export const getAllBooks = () => axios.get('/books')
export const getBook = id => axios.get(`/books/${id}`)
export const updateBook = (id, book) => axios.put(`/books/${id}`, book)
export const deleteBook = id => axios.delete(`/books/${id}`)
