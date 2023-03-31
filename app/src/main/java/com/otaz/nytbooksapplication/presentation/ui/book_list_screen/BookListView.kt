package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book

@Composable
fun BookListView(
    book: Book,
){
    Column{
        BookListImageView(
            book = book,
        )
        Row(
            Modifier
                .fillMaxHeight()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(.80f)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.h4,
                )
            }
        }
    }
}