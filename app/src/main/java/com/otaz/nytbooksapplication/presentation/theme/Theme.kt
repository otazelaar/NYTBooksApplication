package com.otaz.nytbooksapplication.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightThemeColors = lightColors(
    primary = Yellow700,
    primaryVariant = Color.Black,
    onPrimary = Color.Black,
    secondary = DarkGrey,
    onSecondary = Color.Black,
    error = RedErrorDark,
    background = Grey2,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
)

private val DarkThemeColors = darkColors(
    primary = Yellow700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = DarkGrey,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = DarkGrey,
    onSurface = Color.White,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = QuickSandTypography,
        shapes = AppShapes
    ){
        val systemUiController = rememberSystemUiController()
        if(darkTheme){
            systemUiController.setSystemBarsColor(Color.Transparent)
            systemUiController.setNavigationBarColor(Color.Transparent)
            systemUiController.setStatusBarColor(Color.Transparent)
        } else{
            systemUiController.setSystemBarsColor(Color.Transparent)
            systemUiController.setNavigationBarColor(Color.Transparent)
            systemUiController.setStatusBarColor(Color.Transparent)

        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.background(MaterialTheme.colors.background)
            ){
                content()
            }
        }
    }
}