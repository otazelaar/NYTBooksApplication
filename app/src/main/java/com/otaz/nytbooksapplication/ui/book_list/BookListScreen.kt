package com.otaz.nytbooksapplication.ui.book_list

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.otaz.nytbooksapplication.ui.theme.AppTheme
import com.otaz.nytbooksapplication.ui.event.BookListEvent.*
import com.otaz.nytbooksapplication.ui.util.getAllBookCategories
import com.otaz.nytbooksapplication.ui.vm.BookListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    onNavigateToBookDetailScreen: (String) -> Unit,
    bookListViewModel: BookListViewModel,
) {
    val books = bookListViewModel.books.value
    val loading = bookListViewModel.isLoading.value
    val selectedCategory = bookListViewModel.selectedCategory.value
    val searchDbQuery = bookListViewModel.searchDbQuery.value

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    AppTheme {
        Scaffold(
            topBar = {
                BookListTopAppBar(
                    searchDbQuery = searchDbQuery,
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    bookCategories = getAllBookCategories(),
                    selectedCategory = selectedCategory,
                    onSearchQueryDbChanged = bookListViewModel::onSearchQueryDbChanged,
                    newCategorySelectedEvent = bookListViewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = bookListViewModel::onChangedCategoryScrollPosition,
                    newCategorySearchEvent = { bookListViewModel.onTriggerEvent(NewCategorySearchEvent) },
                    newSearchDbEvent = { bookListViewModel.onTriggerEvent(NewSearchDbEvent) },
                    resetForNextSearch = { bookListViewModel.onTriggerEvent(ResetForNextSearchEvent) },
                )

            },
        ) {
            BookList(
                loading = loading,
                books = books,
                onNavigateToBookDetailScreen = onNavigateToBookDetailScreen,
                onChangeBookScrollPosition = bookListViewModel::onChangedBookScrollPosition,
            )
        }
    }
}