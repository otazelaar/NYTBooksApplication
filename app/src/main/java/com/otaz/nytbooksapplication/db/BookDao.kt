package com.otaz.nytbooksapplication.db

import androidx.room.*

/**
 *  The Long value returned by insertBook represents whether or not the insert was successful.
 *      If successful, it will return the row number in the database. If unsuccessful, it will
 *      return the integer -1.
 *
 *      insertBooks() will insert a Book if it does not already exist and update those that are
 *      already there. It does this by checking the PrimaryKey which is set to the ISBN13
 *      number. This number is specific to each book. However, it does not delete old books in the
 *      database. As a result, the database will continue to hold old books no longer present on the
 *      current best seller list. This will result in the loadBooks() function becoming incorrect as
 *      time goes on.
 *
 *      loadBooks() searches the entire database of books at any point in time. It is important to
 *      know that books are stored in the database everytime a network call is made. So the database
 *      continues to grow each time the user clicks on a new category. This will result in the issue
 *      of storing books in the database that are no longer present on the best sellers list. BookDao
 *      is not deleting older books at this point in time. Searching will become incorrect overtime as
 *      new books are added.
 */

@Dao
interface BookDao {

    @Upsert
    suspend fun insertBooks(books: List<BookEntity>): LongArray

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): BookEntity?

    @Query("SELECT * FROM books WHERE title LIKE '%' || :search || '%'")
    suspend fun searchBooks(search: String?): List<BookEntity>

    // Used for to verify the cache has been filled for testing
    @Query(" SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

}