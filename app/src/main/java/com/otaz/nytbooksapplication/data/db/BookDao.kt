package com.otaz.nytbooksapplication.data.db

import androidx.room.*

/**
 *  The Long value returned by insertBook represents whether or not the insert was successful.
 *      If successful, it will return the row number in the database. If unsuccessful, it will
 *      return the integer -1.
 */

@Dao
interface BookDao {

    /**
     * insertBooks() will insert a Book if it does not already exist and update those that are
     *      already there. It does this by checking the PrimaryKey which is set to the ISBN13
     *      number. This number is specific to each book. However, it does not delete old books in the
     *      database. As a result, the database will continue to hold old books no longer present on the
     *      current best seller list. This will result in the loadBooks() function becoming incorrect as
     *      time goes on.
     */
    @Upsert
    suspend fun insertBooks(books: List<BookEntity>): LongArray

    /**
     * getBookByID() searches the database by primary key which in this case is the ISBN13 number
     *      which is specific to each book. it returns a single BookEntity which can than be mapped
     *      to the Book data class to be used in the UI.
     */
    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): BookEntity?

    /**
     * searchBooks() searches the entire database of books at any point in time. It is important
     *      to know that books are stored in the database everytime a network call is made. So the
     *      database continues to grow each time the user clicks on a new category. BookDao is not
     *      deleting older books at this point in time. Searching will become incorrect overtime as
     *      new books are added.
     */
    @Query("SELECT * FROM books WHERE title LIKE '%' || :search || '%'")
    suspend fun searchBooks(search: String?): List<BookEntity>

    /**
     * getAllBooks() is used to verify the cache has been filled for testing
     */
    @Query(" SELECT * FROM books")
    suspend fun getAllBooks(): List<BookEntity>

}