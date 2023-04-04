package com.otaz.nytbooksapplication.db

class BookDaoFake(
    private val appDatabaseFake: AppDatabaseFake
) : BookDao {

    override suspend fun insertBooks(books: List<BookEntity>): LongArray {
        appDatabaseFake.books.addAll(books)
        // return success
        return longArrayOf(1)
    }

    override suspend fun getBookById(id: String): BookEntity? {
        return appDatabaseFake.books.find { it.id == id }
    }

    override suspend fun searchBooks(search: String?): List<BookEntity> {
        return appDatabaseFake.books // return the entire list for simplicity
    }

    override suspend fun getAllBooks(): List<BookEntity> {
        return appDatabaseFake.books // return the entire list for simplicity
    }
}