package com.otaz.nytbooksapplication.ui.navigation

/**
 * This class is used to define routes for compose only navigation.
 */

sealed class Screen(
    val route: String,
){
    object BookList: Screen("bookList")
    object BookDetail: Screen("bookDetail")
}