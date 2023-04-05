package com.otaz.nytbooksapplication.ui.book_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.ui.book_list.BookListShimmerCardItem
import com.otaz.nytbooksapplication.ui.util.IMAGE_HEIGHT
import com.otaz.nytbooksapplication.ui.theme.AppTheme
import com.otaz.nytbooksapplication.ui.event.BookDetailEvent.*
import com.otaz.nytbooksapplication.ui.vm.BookDetailViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel,
    bookId: String?,
) {
    if(bookId == null){
        BookListShimmerCardItem(
            imageHeight = 250.dp,
            padding = 8.dp
        )
    } else {
        // fire a one-off event to get the book from api
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(GetBookDetailEvent(bookId))
        }

        val loading = viewModel.loading.value
        val book = viewModel.savedBook.value

        AppTheme{
            Scaffold{
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(loading && book == null){
                        ShimmerBookCardItem(imageHeight = IMAGE_HEIGHT.dp)
                    } else if(!loading && book == null && onLoad){
                        // With more time, the following composable would instead display an invalid book
                        ShimmerBookCardItem(imageHeight = IMAGE_HEIGHT.dp)
                    } else if(book != null){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            BookDetailImageView(book = book)
                            BookDetailCard(book = book)
                        }
                    }
                }
            }
        }
    }
}