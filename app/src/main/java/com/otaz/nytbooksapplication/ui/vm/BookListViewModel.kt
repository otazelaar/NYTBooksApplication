package com.otaz.nytbooksapplication.ui.vm

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.ui.util.BookCategory
import com.otaz.nytbooksapplication.ui.event.BookListEvent
import com.otaz.nytbooksapplication.ui.event.BookListEvent.*
import com.otaz.nytbooksapplication.domain.use_case.GetBookListUC
import com.otaz.nytbooksapplication.domain.use_case.SearchBookDbUC
import com.otaz.nytbooksapplication.ui.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val searchBookDbUC: SearchBookDbUC,
    private val getBookListUC: GetBookListUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    private val _books: MutableState<List<Book>> = mutableStateOf(ArrayList())
    val books: State<List<Book>> = _books

    val selectedCategory: MutableState<BookCategory?> = mutableStateOf(BookCategory.GET_HARDCOVER_FICTION)

    private var listScrollPosition: Int = 0
    private var categoryScrollPosition: Int = 0
    val isLoading = mutableStateOf(false)

    val searchDbQuery: MutableState<String> = mutableStateOf("")

    private val date = mutableStateOf("current")

    init { getBookList() }

    /**
     * Decides which events trigger which functions.
     */
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
     * [getBookList] is executed when [NewCategorySearchEvent] is triggered by the user selecting
     * a category. It is important to note that if the [NewCategorySearchEvent] is triggered too
     * many times from someone pressing the button too often, it will cause an error of too many
     * Http requests causing the data to not populate. With more time, this error will be fixed.
     *
     * The books are collect from a flow used to update the current list of books.
     *
     * The date will be set to a default "current" as the purpose of the app is to be a current NYT
     * best seller's list.
     *
     * The category is changed depending on the user input. Each time a category is searched, more
     * books will be added to the database. This entire database is searched using the top search
     * bar. The default category is "Hardcover Fiction" so that a list of books is already populated
     * upon launching the application.
     *
     * The lifecycle of the getBookListUC is tied to this view model using viewModelScope
     *
     * Please see GetBookListUC for comments for more information.
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

    /**
     * [searchBookDb] is executed when [NewSearchDbEvent] is triggered. This function calls the
     * [searchBookDbUC] which retrieves a List<BookEntity> from the database most alike the user input
     *  called [searchDbQuery]. The List<BookEntity> is then mapped to List<Book> to update the
     *  state of [_books] to be displayed in the UI.
     */
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

    /**
     * Updates the selected category value
     */
    fun onSelectedCategoryChanged(category: BookCategory?){
        this.selectedCategory.value = category
    }

    /**
     * Updates scroll position of the category list
     */
    fun onChangedCategoryScrollPosition(position: Int){
        categoryScrollPosition = position
    }

    /**
     * Updates scroll position of the book list
     */
    fun onChangedBookScrollPosition(position: Int){
        listScrollPosition = position
    }

    /**
     * Updates the search query value with the user input.
     */
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

    /**
     * This function clears the selected category value.
     */
    private fun clearSelectedCategory(){
        onSelectedCategoryChanged(null)
        selectedCategory.value = null
    }

    /**
     * This function clears the search query for new input.
     */
    private fun setSearchQueryDb(query: String){
        this.searchDbQuery.value = query
    }
}