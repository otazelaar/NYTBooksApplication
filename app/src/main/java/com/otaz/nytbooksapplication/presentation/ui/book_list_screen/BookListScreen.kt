package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.otaz.nytbooksapplication.presentation.compose.theme.AppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    bookListViewModel: BookListViewModel,
){
    val books = bookListViewModel.books.value
    val loading = bookListViewModel.isLoading.value


    AppTheme{
        Scaffold{
            BookList(
                loading = loading,
                books = books,
            )
        }
    }
}