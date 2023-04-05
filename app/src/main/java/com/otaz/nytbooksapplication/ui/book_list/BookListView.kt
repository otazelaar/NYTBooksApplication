package com.otaz.nytbooksapplication.ui.book_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book
import java.util.*

@Composable
fun BookListView(
    book: Book,
    onClick: () -> Unit,
){
    Column{
        BookListImageView(
            book = book,
            onClick = onClick,
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
                val title = book.title
                Text(
                    text = "${decapitalizeTitle(title)} by ${book.author}",
                    modifier = Modifier
                        .clickable(onClick = onClick),
                    style = MaterialTheme.typography.h4,
                )
            }
        }
    }
}


fun decapitalizeTitle(title: String): String {

    // The following code is only capitalizing the first character of the String and is not able
    // to capitalize the other words in the Title. Keep in mind that it should ignore "and"
    // Also, this is UI logic. should this be managed here on BookListView or elsewhere?
    // my gut says here on BookListView.

    fun String.titlecaseFirstCharIfItIsLowercase() = replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
    val allLowercaseAuthor = title.lowercase()
    return allLowercaseAuthor.titlecaseFirstCharIfItIsLowercase()
}