package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.otaz.nytbooksapplication.presentation.components.TopAppBar
import com.otaz.nytbooksapplication.presentation.theme.AppTheme
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookListEvent.*
import com.otaz.nytbooksapplication.presentation.ui.vm.BookListViewModel

/**
 * BookListScreen uses state hoisting.
 *      Events are passed from the UI to the ViewModel to be handled.
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    onNavigateToBookDetailScreen: (String) -> Unit,
    bookListViewModel: BookListViewModel,
){
    val books = bookListViewModel.books.value
    val loading = bookListViewModel.isLoading.value

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    AppTheme{
        Scaffold(
            topBar = {
                TopAppBar(
                    onExecuteSearchBook = { bookListViewModel.onTriggerEvent(NewSearchEvent) },
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    expression = bookListViewModel.query.value,
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