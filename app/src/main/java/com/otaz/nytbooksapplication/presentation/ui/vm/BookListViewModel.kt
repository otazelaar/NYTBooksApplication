package com.otaz.nytbooksapplication.presentation.ui.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookCategory
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookCategory.*
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookListEvent
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.BookListEvent.*
import com.otaz.nytbooksapplication.presentation.ui.book_list_screen.getBookCategory
import com.otaz.nytbooksapplication.use_cases.book_list.GetBookListUC
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
 */

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getBookListUC: GetBookListUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    private val _books: MutableState<List<Book>> = mutableStateOf(ArrayList())
    val books: State<List<Book>> = _books

    val selectedCategory: MutableState<BookCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0
    val isLoading = mutableStateOf(false)

    // Default value set to "hardcover-fiction"
    private val queryCategory: MutableState<String> = mutableStateOf("hardcover-fiction")
    private val date = mutableStateOf("current")

    /**
     * 1. Get books from network and cache them into the database
     * 2. Fill the mutableState<List<Book>> from the database
     * 3. Delete and update the cache with new list
     *      - How often should I do this and how do I write the logic to do this?
     * 4.
     *
     * everytime I make a call to the network I get the up to date list.
     * I probably dont want to keep getting a list from the network, caching it, using it
     * and then deleting it before getting from the network again...
     *
     * if the network call for List<book> is for the list screen and the only way I can navigate
     * to the detail screren is through clicking on a movie in the BookDetailScreen than
     *
     *
     */

    init {
        // TODO("Make sure the category chip isSelected == true when this init block runs!")
        getBookList()
    }

    fun onTriggerEvent(event: BookListEvent){
        viewModelScope.launch {
            try {
                if(event == NewCategorySearchEvent){
                    getBookList()
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun getBookList(){
        Log.d(TAG, "BookListViewModel: getBookList: running")

        getBookListUC.execute(
            date.value, queryCategory.value, apikey
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: getBookListUC: Error: $error")}
        }.launchIn(viewModelScope)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getBookCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangedCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    private fun setSelectedCategory(category: BookCategory?){
        selectedCategory.value = category
    }

    private fun onQueryChanged(query: String){
        setQuery(query)
    }

    private fun setQuery(query: String){
        this.queryCategory.value = query
    }
}