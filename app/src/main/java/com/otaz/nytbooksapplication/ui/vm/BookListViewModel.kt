package com.otaz.nytbooksapplication.ui.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.ui.BookCategory.*
import com.otaz.nytbooksapplication.ui.event.BookListEvent
import com.otaz.nytbooksapplication.ui.event.BookListEvent.*
import com.otaz.nytbooksapplication.domain.use_case.GetBookListUC
import com.otaz.nytbooksapplication.domain.use_case.SearchBookDbUC
import com.otaz.nytbooksapplication.ui.BookCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * BookListViewModel...
 * 
 *      How do I know that the flow is giving us one result? if i click on a button a few times too
 *      fast, it results in too many Http requests.
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

    val selectedCategory: MutableState<BookCategory?> = mutableStateOf(GET_HARDCOVER_FICTION)

    private var listScrollPosition: Int = 0
    private var categoryScrollPosition: Int = 0
    val isLoading = mutableStateOf(false)

    val searchDbQuery: MutableState<String> = mutableStateOf("")

    private val date = mutableStateOf("current")

    init { getBookList() }

    fun onTriggerEvent(event: BookListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewCategorySearchEvent -> getBookList()
                    is NewSearchDbEvent -> searchBookDb()
                    is ResetForNextSearchEvent -> resetForNextSearch()
                }
            }catch (e: Exception){
                Log.e(TAG, "BookListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    /**
     * [getBookList] retrieves a list of books from the network for a specified category and date.
     *      The books are cached
     *      The books are collect from a flow using the DataState *** and used to update the current
     *      list of books.
     *
     *      - The date will always be "current" as the purpose of the app is to be a current NYT
     *      best seller's list.
     *      - The category is changed depending on the user input. Each time a category is searched,
     *      more books will be added to the database. This entire database is searched using the top
     *      search bar. The default category is "Hardcover Fiction" so that results are present upon
     *      opening the application.
     *
     *      The lifecycle of the getBookListUC is tied to this view model using viewModelScope and
     *      will be cancelled when ViewModel.onCleared is called behind the scenes
     *
     *      Please see GetBookListUC for comments for more information.
     *
     */
    private fun getBookList(){
        Log.d(TAG, "BookListViewModel: getBookList: running")

        resetGetBookList()
        val currentCategory = selectedCategory.value?.value.toString()

        // Get books from network and collect them from the flow
        // This flow is tied to the viewModelScope lifecycle.
        // As a result, it is managed by
        getBookListUC.execute(
            date.value, currentCategory, apikey
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: getBookListUC: Error: $error")}
        }.launchIn(viewModelScope)
    }

    private fun searchBookDb(){
        Log.d(TAG, "BookListViewModel: searchBookDb: running")

        resetSearchBookDb()
        searchBookDbUC.execute(
            search = searchDbQuery.value
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> _books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: searchBookDbUC: Error: $error")}
            Log.d(TAG, "BookListViewModel: searchBookDb: ${dataState.data}")
        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun onSelectedCategoryChanged(category: BookCategory?){
        setCategory(category)
    }

    private fun setCategory(category: BookCategory?) {
        this.selectedCategory.value = category
    }

    fun onChangedCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    fun onChangedBookScrollPosition(position: Int){
        listScrollPosition = position
    }

    fun onSearchQueryDbChanged(query: String){
        setSearchQueryDb(query)
    }

    /**
     * This function is called when [searchBookDb] is executed which is triggered by [NewSearchDbEvent].
     * This will reset the [_books], [categoryScrollPosition],and [selectedCategory] values for a new
     * search.
     */
    private fun resetSearchBookDb(){
        _books.value = listOf()
        onChangedCategoryScrollPosition(0)
        onChangedBookScrollPosition(0)
        // might need to reset index here as well
        if(selectedCategory.value?.value != searchDbQuery.value) clearSelectedCategory()
    }

    /**
     * This function is called when [getBookList] is executed which is triggered by [NewCategorySearchEvent].
     * This will reset the [_books], and [selectedCategory] values for a new search.
     */
    private fun resetGetBookList(){
        _books.value = listOf()
        searchDbQuery.value = ""
        onChangedCategoryScrollPosition(0)
        onChangedBookScrollPosition(0)
    }

    /**
     * This function is called when Search Icon in TopAppBar is clicked. Clicking this will trigger
     * the [ResetForNextSearchEvent]. This function will reset the [searchDbQuery], [_books],
     * [categoryScrollPosition], and [selectedCategory] values for a new search.
     */
    private fun resetForNextSearch(){
        searchDbQuery.value = ""
        _books.value = listOf()
        onChangedCategoryScrollPosition(0)
        if(selectedCategory.value?.value != searchDbQuery.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory(){
        setCategory(null)
        selectedCategory.value = null
    }

    private fun setSearchQueryDb(query: String){
        this.searchDbQuery.value = query
    }
}