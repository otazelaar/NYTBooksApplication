package com.otaz.nytbooksapplication.presentation.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp

/**
 * Make sure searching feature can unselects the category chip after searching as we are no longer
 *      searching in a specific category but the entire database of books. I may need to implement a
 *      new API GET function to search all of the books in history rather and determine if they are
 *      currently on the list or not.
 *
 */

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TopAppBar(
    onExecuteSearchBook: () -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    expression: String,
    onQueryChanged: (String) -> Unit,
    resetForNextSearch: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = 8.dp,
    ) {
        Column {
            SearchBar(
                onExecuteSearch = onExecuteSearchBook,
                focusRequester = focusRequester,
                focusManager = focusManager,
                expression = expression,
                onQueryChanged = onQueryChanged,
                resetForNextSearch = resetForNextSearch,
            )
        }
    }
}