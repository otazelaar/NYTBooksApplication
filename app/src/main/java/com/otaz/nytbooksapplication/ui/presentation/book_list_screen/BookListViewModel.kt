package com.otaz.nytbooksapplication.ui.presentation.book_list_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.uc.book_list.GetBookListUC
import com.otaz.nytbooksapplication.ui.state.LatestBooksUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Named

/**
 * This VM acts as the single source of truth for UI
 *
 */

@HiltViewModel
class BookListViewModel(
    private val getBookListUC: GetBookListUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    // ViewModel events are UI actions that originate from the ViewModel.
    // The following is a ViewModel event.
    private val books: MutableState<List<Book>> = mutableStateOf(ArrayList())

    private val query = mutableStateOf("")
    private val page = mutableStateOf(1)
    private val isLoading = mutableStateOf(false)

    init {
        getBookListUC()
    }

    private fun getBookListUC(){
        Log.d(TAG, "BookListViewModel: getBookListUC: query: ${query.value}, page: ${page.value}")

        getBookListUC.execute(
            apikey = apikey,
            query = query.value,
            page = page.value
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: getBookListUC: Error: $error")}
        }.launchIn(viewModelScope)
    }
}