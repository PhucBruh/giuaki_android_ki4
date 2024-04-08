package com.phucbruh.giua_ki.data

import com.phucbruh.giua_ki.data.Book
import com.phucbruh.giua_ki.data.BookRepository

class MockBookRepositoryImpl : BookRepository {
    private val books = bookList

    override fun getAllBooks(): List<Book> {
        return books.toList()
    }

    override fun getBookById(id: Int): Book? {
        return books.find { it.id == id }
    }

    override fun filterBooks(searchQuery: String): List<Book> {
        val newBooks = books.toMutableList()
        return newBooks.filter { it.name.contains(searchQuery, ignoreCase = true) }.toList()
    }

    override fun addBooks(book: Book): Int {
        val nextId = books.maxByOrNull { it.id ?: 0 }?.id?.plus(1) ?: 1
        val newBook = book.copy(id = nextId)
        books.add(newBook)
        return nextId
    }

    override fun deleteBook(book: Book) {
        books.removeIf { it.id == book.id }
    }

    override fun editBook(book: Book) {
        val existingBookIndex = books.indexOfFirst { it.id == book.id }
        if (existingBookIndex != -1) {
            books[existingBookIndex] = book
        }
    }
}

val bookList = mutableListOf(
    Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997, 1),
    Book("To Kill a Mockingbird", "Harper Lee", 1960, 2),
    Book("1984", "George Orwell", 1949, 3),
    Book("Pride and Prejudice", "Jane Austen", 1813, 4),
    Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 5),
    Book("The Catcher in the Rye", "J.D. Salinger", 1951, 6),
    Book("Brave New World", "Aldous Huxley", 1932, 7),
    Book("The Hobbit", "J.R.R. Tolkien", 1937, 8),
    Book("One Hundred Years of Solitude", "Gabriel García Márquez", 1967, 9),
    Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, 10)
)
