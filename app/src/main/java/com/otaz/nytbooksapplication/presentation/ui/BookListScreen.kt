package com.otaz.nytbooksapplication.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import com.otaz.nytbooksapplication.presentation.components.TopAppBar
import com.otaz.nytbooksapplication.presentation.theme.AppTheme
import com.otaz.nytbooksapplication.presentation.ui.BookListEvent.*
import com.otaz.nytbooksapplication.presentation.vm.BookListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    onNavigateToBookDetailScreen: (String) -> Unit,
    bookListViewModel: BookListViewModel,
){
    val books = bookListViewModel.books.value
    val loading = bookListViewModel.isLoading.value
    val selectedCategory = bookListViewModel.selectedCategory.value
//    val onLoad = bookListViewModel.onLoad.value
//    val defaultCategory = bookListViewModel.category.value
    val searchDbQuery = bookListViewModel.searchDbQuery.value

    val focusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    AppTheme{
        Scaffold(
            topBar = {
                TopAppBar(
                    searchDbQuery = searchDbQuery,
//                    onLoad = onLoad,
//                    defaultCategory = defaultCategory,
                    focusRequester = focusRequester,
                    focusManager = focusManager,
                    categoryScrollPosition = bookListViewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onSearchQueryDbChanged = bookListViewModel::onSearchQueryDbChanged,
                    onSelectedCategoryChanged = bookListViewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = bookListViewModel::onChangedCategoryScrollPosition,
                    newCategorySearchEvent = { bookListViewModel.onTriggerEvent(NewCategorySearchEvent) },
                    newSearchDbEvent = { bookListViewModel.onTriggerEvent(NewSearchDbEvent) },
                    resetForNextSearch = bookListViewModel::resetForNextSearch
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