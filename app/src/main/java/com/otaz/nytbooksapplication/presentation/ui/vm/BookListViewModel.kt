package com.otaz.nytbooksapplication.presentation.ui.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookCategory.*
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookListEvent
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookListEvent.*
import com.otaz.nytbooksapplication.use_cases.SearchBookUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * The cachedBookList() is called everytime the ViewModel is *** in the init block. This results in
 *     the list being regularly updated by the bookDao replacing the previous list.
 *
 *     This list must be replaced and not added as to not end up with books from past lists.
 *
 *      1. Get books from network and cache them into the database
 *      2. Fill the mutableState<List<Book>> from the database
 *      3. Delete and update the cache with new list
 *      - How often should I do this and how do I write the logic to do this?
 *      4.
 *
 *      everytime I make a call to the network I get the up to date list.
 *      I probably dont want to keep getting a list from the network, caching it, using it
 *      and then deleting it before getting from the network again...
 *
 *      if the network call for List<book> is for the list screen and the only way I can navigate
 *      to the detail screren is through clicking on a book in the BookDetailScreen than
 *
 *
 */
@HiltViewModel
class BookListViewModel @Inject constructor(
    private val searchBookUC: SearchBookUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    private val _books: MutableState<List<Book>> = mutableStateOf(ArrayList())
    val books: State<List<Book>> = _books

    val isLoading = mutableStateOf(false)

    private val _query: MutableState<String> = mutableStateOf("")
    val query: State<String> = _query

    init {
        searchBook()
    }

    fun onTriggerEvent(event: BookListEvent){
        viewModelScope.launch {
            try {
                if(event == NewSearchEvent){
                    searchBook()
                }
            }catch (e: Exception){
                Log.e(TAG, "BookListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun searchBook(){
        resetSearchState()
        searchBookUC.execute(
            apikey, _query.value
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: searchBookUC: Error: $error")}
        }.launchIn(viewModelScope)
    }

    fun onQueryChanged(query: String){
        setQuery(query)
    }

    private fun setQuery(query: String){
        this._query.value = query
    }

    /**
     * Called when a new search is executed and when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    private fun resetSearchState(){
        _books.value = listOf()
//        Not doing pagination yet but save this for when I do
//        page.value = 1
//        onChangeCategoryScrollPosition(0)
        // might need to reset index here as well
//        if(selectedCategory.value?.value != _query.value) clearSelectedCategory()
    }

    /**
     * Called when Icon in SearchAppBar is clicked to reset the search state.
     */
    fun resetForNextSearch(){
        _query.value = ""
        _books.value = listOf()
//        page.value = 1
//        onChangeCategoryScrollPosition(0)
        // might need to reset index here as well
//        if(selectedCategory.value?.value != query.value) clearSelectedCategory()
    }
}