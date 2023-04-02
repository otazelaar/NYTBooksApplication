package com.otaz.nytbooksapplication.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.presentation.ui.BookCategory
import com.otaz.nytbooksapplication.presentation.ui.getAllBookCategories
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TopAppBar(
    searchDbQuery: String,
//    onLoad: Boolean,
//    defaultCategory: String,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    categoryScrollPosition: Int,
    selectedCategory: BookCategory?,
    onSearchQueryDbChanged: (String) -> Unit,
    onSelectedCategoryChanged: (String) -> Unit,
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
        Column {
            SearchBar(
                searchDbQuery = searchDbQuery,
                focusRequester = focusRequester,
                focusManager = focusManager,
                onSearchQueryDbChanged = onSearchQueryDbChanged,
                resetForNextSearch = resetForNextSearch,
                newSearchDbEvent = { newSearchDbEvent() },
            )
            val scrollState = rememberScrollState()
            val coroutineScope = rememberCoroutineScope()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp)
                    .horizontalScroll(scrollState)
                    .wrapContentWidth(),
            ) {
                // restore scroll position after rotation
                coroutineScope.launch {
                    scrollState.scrollTo(categoryScrollPosition)
                }
                for (category in getAllBookCategories()) {
                    BookCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChanged = {
                            onSelectedCategoryChanged(it) // it = category.value
                            onChangedCategoryScrollPosition(scrollState.value)
                        },
                        newCategorySearchEvent = { newCategorySearchEvent() },
                    )
                }
            }
        }
    }
}