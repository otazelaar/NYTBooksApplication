package com.otaz.nytbooksapplication.ui.book_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.ui.util.BookCategory

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun BookListTopAppBar(
    searchDbQuery: String,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    bookCategories: List<BookCategory>,
    selectedCategory: BookCategory?,
    onSearchQueryDbChanged: (String) -> Unit,
    newCategorySelectedEvent: (BookCategory) -> Unit,
    onChangedCategoryScrollPosition: (Int) -> Unit,
    newCategorySearchEvent: () -> Unit,
    newSearchDbEvent: () -> Unit,
    resetForNextSearch: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = 8.dp,
    ) {
        Column{
            BookListSearchBar(
                searchDbQuery = searchDbQuery,
                focusRequester = focusRequester,
                focusManager = focusManager,
                onSearchQueryDbChanged = onSearchQueryDbChanged,
                resetForNextSearch = resetForNextSearch,
                newSearchDbEvent = newSearchDbEvent,
            )
            BookListCategoryBar(
                bookCategories = bookCategories,
                selectedCategory = selectedCategory,
                newCategorySelectedEvent = newCategorySelectedEvent,
                onChangedCategoryScrollPosition = onChangedCategoryScrollPosition,
                newCategorySearchEvent = newCategorySearchEvent
            )
        }
    }
}