package com.otaz.nytbooksapplication.persistance

import androidx.room.*

/**
 *  The Long value returned by insertMovie represents whether or not the insert was successful.
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
    suspend fun insertBooks(books: List<SBResultEntity>): LongArray

    @Query("SELECT * FROM books WHERE title = :title")
    suspend fun getBookById(title: String): SBResultEntity?
}