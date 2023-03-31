package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.compose.components.loadPicture
import com.otaz.nytbooksapplication.presentation.compose.constants.DEFAULT_BOOK_IMAGE

@Composable
fun BookListImageView(
    book: Book,
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        val image = book.book_image.let {
            loadPicture(
                url = it,
                defaultImage = DEFAULT_BOOK_IMAGE,
            ).value
        }
        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                contentDescription = "book image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(630.dp),
                contentScale = ContentScale.Crop,
            )
        }
    }
}