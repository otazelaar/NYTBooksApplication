package com.otaz.nytbooksapplication.db

import com.otaz.nytbooksapplication.data.db.BookEntity

class AppDatabaseFake{
    val books = mutableListOf<BookEntity>()
}