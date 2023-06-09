package com.otaz.nytbooksapplication.ui.book_list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.ui.util.BookCategory

@Composable
fun BookListCategoryBar(
    bookCategories: List<BookCategory>,
    selectedCategory: BookCategory?,
    newCategorySelectedEvent: (BookCategory) -> Unit,
    onChangedCategoryScrollPosition: (Int) -> Unit,
    newCategorySearchEvent: () -> Unit,
) {
    val categoryScrollState = rememberLazyListState()
    LazyRow(
        modifier = Modifier
            .padding(start = 8.dp, bottom = 8.dp),
        state = categoryScrollState,
    ) {
        items(bookCategories) { it ->
            BookListCategoryChip(
                category = it,
                isSelected = selectedCategory == it,
                newCategorySelectedEvent = {
                    newCategorySelectedEvent(it)
                    // Not sure about the following function and what it is doing yet
                    onChangedCategoryScrollPosition(categoryScrollState.firstVisibleItemIndex)
                },
                newCategorySearchEvent = newCategorySearchEvent,
            )
        }
    }
}