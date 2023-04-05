package com.otaz.nytbooksapplication.ui.book_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.R
import com.otaz.nytbooksapplication.domain.model.Book

@Composable
fun BookDetailCard(
    book: Book,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        book.title.let { title ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h2,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .padding(horizontal = 8.dp)
        ) {
            val author = book.author
            Text(
                text = "Author :  $author",
                style = MaterialTheme.typography.h4
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
                .padding(horizontal = 8.dp)
        ) {
            val weeksOnList = book.weeks_on_list
            Text(
                text = "Weeks On List :   $weeksOnList",
                style = MaterialTheme.typography.h4
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, top = 14.dp)
        ) {
            Text(
                text = stringResource(R.string.book_content_description),
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
            )
        }
        book.description.let { plot ->
            Row(
                modifier = Modifier
                    .wrapContentWidth(Alignment.Start)
                    .width(width = 400.dp)
                    .padding(vertical = 4.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = plot,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}