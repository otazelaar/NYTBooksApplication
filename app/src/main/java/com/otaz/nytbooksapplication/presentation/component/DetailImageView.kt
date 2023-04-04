package com.otaz.nytbooksapplication.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.constant.DEFAULT_BOOK_IMAGE
import com.otaz.nytbooksapplication.presentation.constant.IMAGE_HEIGHT

@Composable
fun DetailImageView(
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