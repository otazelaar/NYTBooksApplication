package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.otaz.nytbooksapplication.presentation.components.TopAppBar
import com.otaz.nytbooksapplication.presentation.theme.AppTheme
import com.otaz.nytbooksapplication.presentation.ui.vm.BookListViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookListScreen(
    onNavigateToBookDetailScreen: (String) -> Unit,
    bookListViewModel: BookListViewModel,
){
    val books = bookListViewModel.books.value
    val loading = bookListViewModel.isLoading.value
    val selectedCategory = bookListViewModel.selectedCategory.value

    AppTheme{
        Scaffold(
            topBar = {
                TopAppBar(
                    onExecuteSearch = { bookListViewModel.onTriggerEvent(BookListEvent.NewCategorySearchEvent) },
                    categoryScrollPosition = bookListViewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onSelectedCategoryChanged = bookListViewModel::onSelectedCategoryChanged,
                    onChangedCategoryScrollPosition = bookListViewModel::onChangedCategoryScrollPosition,
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