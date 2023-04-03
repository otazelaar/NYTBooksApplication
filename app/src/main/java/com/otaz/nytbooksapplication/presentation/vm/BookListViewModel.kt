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
import com.otaz.nytbooksapplication.use_cases.GetBookListByCategoryUC
import com.otaz.nytbooksapplication.use_cases.SearchBookDbUC
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
 */

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val searchBookDbUC: SearchBookDbUC,
    private val getBookListByCategoryUC: GetBookListByCategoryUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    private val _books: MutableState<List<Book>> = mutableStateOf(ArrayList())
    val books: State<List<Book>> = _books

    val selectedCategory: MutableState<BookCategory?> = mutableStateOf(null)

    var listScrollPosition: Int = 0
    var categoryScrollPosition: Int = 0
    val isLoading = mutableStateOf(false)

    val searchDbQuery: MutableState<String> = mutableStateOf("")

    private val date = mutableStateOf("current")

    init { getBookListByCategory() }

    fun onTriggerEvent(event: BookListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewCategorySearchEvent -> getBookListByCategory()
                    is NewSearchDbEvent -> searchBookDb()
                    is ResetForNextSearchEvent -> resetForNextSearch()
                }
            }catch (e: Exception){
                Log.e(TAG, "MovieListViewModel: onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private fun getBookListByCategory(){
        Log.d(TAG, "BookListViewModel: getBookList: running")

        resetGetBookListByCategory()
        var category = selectedCategory.value
        val defaultCategory = "Hardcover Fiction"

        if (category == null){
            category = getBookCategory(defaultCategory)
        }

        getBookListByCategoryUC.execute(
            date.value, category?.value.toString(), apikey
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
     * This function is called when [getBookListByCategory] is executed which is triggered by [NewCategorySearchEvent].
     * This will reset the [_books], and [selectedCategory] values for a new search.
     */
    private fun resetGetBookListByCategory(){
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