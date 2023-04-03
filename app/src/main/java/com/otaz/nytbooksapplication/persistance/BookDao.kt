package com.otaz.nytbooksapplication.persistance

import androidx.room.*

/**
 *  The Long value returned by insertBook represents whether or not the insert was successful.
 *      If successful, it will return the row number in the database. If unsuccessful, it will
 *      return the integer -1.
 *
 *      insertBooks() will insert a Book if it does not already exist and update those that are
 *      already there. It does this by checking the PrimaryKey which is set to the ISBN13
 *      number. This number is specific to each book.
 */

@Dao
interface BookDao {

    @Upsert
    suspend fun insertBooks(books: List<BookEntity>): LongArray

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBookById(id: String): BookEntity?

    @Query("SELECT * FROM books WHERE title LIKE '%' || :search || '%'")
    fun loadBooks(search: String?): List<BookEntity>

//    @Query("SELECT * FROM books WHERE title LIKE '%' || :search || '%'")
//    fun loadBooks(search: String?): Flowable<List<BookEntity>>

//    @Query("SELECT * FROM database WHERE name LIKE '%' || : arg0 || '%'")
//    fun find(search:String?):List<BookEntity>

//    @Query("SELECT * FROM hamster WHERE name LIKE '%' || :search || '%'")
//    fun loadHamsters(search: String?): Flowable<List<Hamster>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertBook(book: BookEntity): Long
//
//    @Query("DELETE FROM books WHERE id = :primaryKey")
//    suspend fun deleteBook(primaryKey: String): Int
//
//    @Query(" SELECT * FROM books ")
//    suspend fun getAllBooks(): List<BookEntity>
//
//    @Query("DELETE FROM books WHERE id IN (:ids)")
//    suspend fun deleteBooks(ids: List<String>): Int
//
//    @Query("DELETE FROM books")
//    suspend fun deleteAllBooks()
}