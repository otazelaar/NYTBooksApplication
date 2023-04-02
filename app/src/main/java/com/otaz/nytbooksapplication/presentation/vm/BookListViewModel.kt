package com.otaz.nytbooksapplication.presentation.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.domain.model_fake.SBResult
import com.otaz.nytbooksapplication.presentation.components.BookListEvent
import com.otaz.nytbooksapplication.presentation.components.BookListEvent.*
import com.otaz.nytbooksapplication.use_cases.SearchBookUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val searchBookUC: SearchBookUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    private val _books: MutableState<List<SBResult>> = mutableStateOf(ArrayList())
    val books: State<List<SBResult>> = _books

    val query = mutableStateOf("")

    val isLoading = mutableStateOf(false)

    fun onTriggerEvent(event: BookListEvent){
        viewModelScope.launch {
            try {
                if(event == NewSearchEvent){
                    searchBook()
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun searchBook(){
        resetSearchState()

        // Search functionality breaks down with extra whitespaces on the end
        val title = query.value.trim()

        searchBookUC.execute(
            apikey, title
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: SearchBookUC: Error: $error")}
        }.launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String){
        setQuery(query)
    }

    private fun setQuery(query: String){
        this.query.value = query
    }

    private fun resetSearchState(){
        _books.value = listOf()
    }

    fun resetForNextSearch(){
        query.value = ""
        _books.value = listOf()
    }
}