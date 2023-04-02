package com.otaz.nytbooksapplication.presentation.screen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.otaz.nytbooksapplication.presentation.components.BookList
import com.otaz.nytbooksapplication.presentation.components.SearchAppBar
import com.otaz.nytbooksapplication.presentation.theme.AppTheme
import com.otaz.nytbooksapplication.presentation.components.BookListEvent.*
import com.otaz.nytbooksapplication.presentation.vm.BookListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    onNavigateToBookDetailScreen: (String) -> Unit,
    bookListViewModel: BookListViewModel,
){
    val books = bookListViewModel.books.value
    val loading = bookListViewModel.isLoading.value
    val query = bookListViewModel.query.value

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    AppTheme{
        Scaffold(
            topBar = {
                SearchAppBar(
                    onExecuteSearchBook = { bookListViewModel.onTriggerEvent(NewSearchEvent) },
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    query = query,
                    onQueryChanged = bookListViewModel::onQueryChanged,
                    resetForNextSearch = { bookListViewModel.resetForNextSearch() },
                )
            },
        ){
            BookList(
                loading = loading,
                books = books,
                onNavigateToBookDetailScreen = onNavigateToBookDetailScreen,
            )
        }
    }
}