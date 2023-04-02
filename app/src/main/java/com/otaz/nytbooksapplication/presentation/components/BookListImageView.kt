package com.otaz.nytbooksapplication.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.otaz.nytbooksapplication.domain.model_fake.SBResult

@Composable
fun BookListImageView(
    book: SBResult,
    onClick: () -> Unit,
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        book.publisher?.let { Text(text = it) }
//        val image = book.book_image.let {
//            loadPicture(
//                url = it,
//                defaultImage = DEFAULT_BOOK_IMAGE,
//            ).value
//        }
//        image?.let { img ->
//            Image(
//                bitmap = img.asImageBitmap(),
//                contentDescription = "book image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(630.dp),
//                contentScale = ContentScale.Crop,
//            )
//        }
    }
}