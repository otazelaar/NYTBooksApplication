package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.compose.components.ShimmerBookListCardItem

@Composable
fun BookList(
    loading: Boolean,
    books: List<Book>,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        if (loading && books.isEmpty()) {
            ShimmerBookListCardItem(
                imageHeight = 250.dp,
                padding = 8.dp
            )
        } else {
            LazyColumn{
                itemsIndexed(
                    items = books
                ){_, book ->
                    BookListView(
                        book = book
                    )
                }
            }
        }
    }
}