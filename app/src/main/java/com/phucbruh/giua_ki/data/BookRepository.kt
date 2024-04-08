package com.phucbruh.giua_ki.data

import com.phucbruh.giua_ki.data.Book

interface BookRepository {

    fun getAllBooks(): List<Book>

    fun getBookById(id: Int): Book?

    fun filterBooks(searchQuery: String): List<Book>

    fun addBooks(book: Book): Int

    fun deleteBook(book: Book)

    fun editBook(book: Book)
}


