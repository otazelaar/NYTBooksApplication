package com.otaz.nytbooksapplication.ui.book_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.ui.util.DEFAULT_BOOK_IMAGE
import com.otaz.nytbooksapplication.ui.util.IMAGE_HEIGHT
import com.otaz.nytbooksapplication.ui.util.loadPicture

@Composable
fun BookDetailImageView(
    book: Book,
){
    book.book_image.let { url ->
        val image = loadPicture(
            url = url,
            defaultImage = DEFAULT_BOOK_IMAGE
        ).value
        image?.let { img ->
            Image(
                bitmap = img.asImageBitmap(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IMAGE_HEIGHT.dp),
                contentDescription = "Book Detail Image",
                contentScale = ContentScale.Crop,
            )
        }
    }
}