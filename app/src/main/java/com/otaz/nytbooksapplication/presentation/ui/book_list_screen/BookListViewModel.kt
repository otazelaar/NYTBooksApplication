package com.otaz.nytbooksapplication.presentation.ui.book_list_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otaz.nytbooksapplication.domain.model.Book
import com.otaz.nytbooksapplication.use_cases.book_list.GetCurrentBestSellerListByCategoryUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getCurrentBestSellerListByCategoryUC: GetCurrentBestSellerListByCategoryUC,
    @Named("nyt_apikey") private val apikey: String,
): ViewModel() {
    // Is a private version of this Mutable State necessary? not sure but it works in the previous project for now
    val books: MutableState<List<Book>> = mutableStateOf(ArrayList())

    val isLoading = mutableStateOf(false)
    val date = "current"
    val category = "hardcover-fiction"

    init {
        getBookListUC(
            date = date,
            category = category,
            apikey = apikey
        )
    }

    private fun getBookListUC(date: String, category: String, apikey: String){
        Log.d(TAG, "BookListViewModel: getBookListUC: running")

        getCurrentBestSellerListByCategoryUC.execute(
            date = date,
            category = category,
            apikey = apikey,
        ).onEach { dataState ->
            isLoading.value = dataState.loading
            dataState.data?.let { list -> books.value = list }
            dataState.error?.let { error -> Log.e(TAG,"BookListViewModel: getBookListUC: Error: $error")}
        }.launchIn(viewModelScope)
    }
}