package com.otaz.nytbooksapplication.ui.presentation.book_list_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BookListScreen(
    navController: NavController,
    bookListViewModel: BookListViewModel,
){
    val latestBooksUiState = bookListViewModel.latestBooksUiState.value



}