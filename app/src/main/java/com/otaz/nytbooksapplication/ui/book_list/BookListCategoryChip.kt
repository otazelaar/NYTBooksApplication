package com.otaz.nytbooksapplication.ui.book_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.otaz.nytbooksapplication.ui.util.BookCategory

@Composable
fun BookListCategoryChip(
    category: BookCategory,
    isSelected: Boolean = false,
    newCategorySelectedEvent: (BookCategory) -> Unit,
    newCategorySearchEvent: () -> Unit,
){
    Surface(
        modifier = Modifier
            .padding(end = 8.dp),
        elevation = 8.dp,
        shape = RoundedCornerShape(20.dp),
        color = if(isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.primary,
        border = BorderStroke(width = 2.dp, brush = Brush.linearGradient(
            0.0f to Color.Red,
            0.3f to Color.Green,
            0.8f to Color.Blue,
            start = Offset(0.0f, 0.0f),
            end = Offset(0.0f, 100.0f),
        )),
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        newCategorySelectedEvent(category)
                        newCategorySearchEvent()
                    }
                )
        ) {
            Text(
                text = category.value,
                fontWeight = if(isSelected) FontWeight(500) else FontWeight(400),
                style = MaterialTheme.typography.button,
                color = if(isSelected) MaterialTheme.colors.onSurface else MaterialTheme.colors.background,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}