package com.otaz.nytbooksapplication.presentation.navigation

/**
 * This class is used to define routes for compose only navigation.
 */

sealed class Screen(
    val route: String,
){
    object BookList: Screen("bookList")
}