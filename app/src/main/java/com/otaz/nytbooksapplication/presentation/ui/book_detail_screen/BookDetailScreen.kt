package com.otaz.nytbooksapplication.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.presentation.components.BookDetailView
import com.otaz.nytbooksapplication.presentation.components.ShimmerBookListCardItem
import com.otaz.nytbooksapplication.presentation.constants.IMAGE_HEIGHT
import com.otaz.nytbooksapplication.presentation.theme.AppTheme
import com.otaz.nytbooksapplication.presentation.ui.book_detail_screen.BookDetailEvent.*
import com.otaz.nytbooksapplication.presentation.ui.vm.BookDetailViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel,
    bookId: String?,
) {
    if(bookId == null){
        ShimmerBookListCardItem(
            imageHeight = 250.dp,
            padding = 8.dp
        )
    } else {
        val loading = viewModel.loading.value
        val book = viewModel.savedBook.value
        val onLoad = viewModel.onLoad.value

        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(GetBookDetailEvent(bookId))
        }

        AppTheme{
            Scaffold{
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if(loading && book == null){
                        ShimmerBookListCardItem(imageHeight = IMAGE_HEIGHT.dp)
                    } else if(!loading && book == null && onLoad){
                        // TODO("Show Invalid Book")
                    } else if(book != null){
                        BookDetailView(
                            book = book,
                        )
                    }
                }
            }
        }
    }
}