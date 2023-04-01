package com.otaz.nytbooksapplication.presentation.ui.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.ui.book_detail_screen.BookDetailEvent
import com.otaz.nytbooksapplication.presentation.ui.book_detail_screen.BookDetailEvent.*
import com.otaz.nytbooksapplication.use_cases.book_detail.GetSavedBookUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

// Make sure that you are handling paging just in case a list is longer than 20!

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getSavedBookUC: GetSavedBookUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel(){
    private val _savedBook: MutableState<Book?> = mutableStateOf(null)
    val savedBook: State<Book?> = _savedBook

    val onLoad: MutableState<Boolean> = mutableStateOf(false)
    val loading = mutableStateOf(false)

    fun onTriggerEvent(event: BookDetailEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is GetBookDetailEvent -> if(_savedBook.value == null) {
                        this@BookDetailViewModel.getSavedBook(event.book_id)
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
            }
        }
    }

    private fun getSavedBook(book_id: String){
        Log.d(TAG, "SavedMoviesListViewModel: getSavedMovies: running")

        getSavedBookUC.execute(
            book_id = book_id,
        ).onEach { dataState ->
            loading.value = dataState.loading
            dataState.data?.let { data -> _savedBook.value = data
                Log.i(TAG, "getSavedBook: Success: ${data.primary_isbn13}")
            }
            dataState.error?.let { error -> Log.e(TAG, "BookDetailViewModel: getSavedBook: Error $error")}
        }.launchIn(viewModelScope)
    }
}