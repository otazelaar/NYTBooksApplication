package com.otaz.nytbooksapplication.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.components.ShimmerBookListCardItem
import com.otaz.nytbooksapplication.presentation.navigation.Screen

@Composable
fun BookList(
    loading: Boolean,
    books: List<Book>,
    onNavigateToBookDetailScreen: (String) -> Unit,
    onChangeBookScrollPosition: (Int) -> Unit,
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
            val bookListScrollState = rememberLazyListState()
            LazyColumn(
                state = bookListScrollState,
            ){
                itemsIndexed(
                    items = books,
                ){index, book ->
                    onChangeBookScrollPosition(index)
                    BookListView(
                        book = book,
                        onClick = {
                            val route = Screen.BookDetail.route + "/${book.id}"
                            onNavigateToBookDetailScreen(route)
                        },
                    )
                }
            }
        }
    }
}