package com.otaz.nytbooksapplication.presentation.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.presentation.ui.BookCategory
import com.otaz.nytbooksapplication.presentation.ui.BookCategory.*
import com.otaz.nytbooksapplication.presentation.ui.BookListEvent
import com.otaz.nytbooksapplication.presentation.ui.BookListEvent.*
import com.otaz.nytbooksapplication.presentation.ui.getBookCategory
import com.otaz.nytbooksapplication.use_cases.GetBookListUC
import com.otaz.nytbooksapplication.use_cases.SearchBookDbUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
 *      to the detail screren is through clicking on a movie in the BookDetailScreen than
 *
 *
 */

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val searchBookDbUC: SearchBookDbUC,
    private val getBookListUC: GetBookListUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    private val _books: MutableState<List<Book>> = mutableStateOf(ArrayList())
    val books: State<List<Book>> = _books

    val selectedCategory: MutableState<BookCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Int = 0
    val isLoading = mutableStateOf(false)
    val onLoad = mutableStateOf(false)

    val searchDbQuery: MutableState<String> = mutableStateOf("")

    // Default value set to "hardcover-fiction"
    private val _category: MutableState<String> = mutableStateOf("hardcover-fiction")
    val category: State<String> = _category

    private val date = mutableStateOf("current")

    init {
        // TODO("Make sure the category chip isSelected == true when this init block runs!")
        setScreenToLoading()

        // if the following line doesn't work, try putting it into the setScreenLoading() above
        setSelectedCategory(selectedCategory.value)
        getBookList()

    }

    fun onTriggerEvent(event: BookListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewCategorySearchEvent -> getBookList()
                    is NewSearchDbEvent -> searchBookDb()
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun searchBookDb(){
        Log.d(TAG, "BookListViewModel: searchBookDb: running")

        resetSearchState()
        searchBookDbUC.execute(
            search = searchDbQuery.value
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: searchBookDbUC: Error: $error")}
            Log.d(TAG, "BookListViewModel: searchBookDb: ${dataState.data}")
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    private fun getBookList(){
        Log.d(TAG, "BookListViewModel: getBookList: running")

        resetSearchState()
        getBookListUC.execute(
            date.value, _category.value, apikey
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: getBookListUC: Error: $error")}
        }.launchIn(viewModelScope)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getBookCategory(category)
        setSelectedCategory(newCategory)
        onCategoryChanged(category)
    }

    fun onChangedCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    private fun setSelectedCategory(category: BookCategory?){
        selectedCategory.value = category
    }

    // Not sure if the following function should be private or if it should be called in the BookListScreen and passed to the UI
    private fun onCategoryChanged(query: String){
        setCategory(query)
    }

    private fun setCategory(query: String){
        this._category.value = query
    }

    private fun setScreenToLoading(){
        onLoad.value = true
    }

    // Manages updating the search bar text
    fun onSearchQueryDbChanged(query: String){
        setSearchQueryDb(query)
    }

    private fun setSearchQueryDb(query: String){
        this.searchDbQuery.value = query
    }

    /**
     * Called when a new search is executed and when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    private fun resetSearchState(){
        _books.value = listOf()
        onChangedCategoryScrollPosition(0)
        // might need to reset index here as well
        if(selectedCategory.value?.value != searchDbQuery.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setSelectedCategory(null)
        selectedCategory.value = null
    }

    /**
     * Called when Icon in SearchAppBar is clicked
     * This will reset search state. if this doesn't work then i need to make a separate function that
     * at least will clear the search query
     */
    fun resetForNextSearch(){
        searchDbQuery.value = ""
        _books.value = listOf()
        onChangedCategoryScrollPosition(0)
        if(selectedCategory.value?.value != searchDbQuery.value) clearSelectedCategory()
    }
}