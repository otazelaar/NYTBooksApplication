package com.otaz.nytbooksapplication.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.otaz.nytbooksapplication.di.BaseApplication
import com.otaz.nytbooksapplication.presentation.navigation.Screen
import com.otaz.nytbooksapplication.presentation.ui.BookDetailScreen
import com.otaz.nytbooksapplication.presentation.screen.BookListScreen
import com.otaz.nytbooksapplication.presentation.vm.BookDetailViewModel
import com.otaz.nytbooksapplication.presentation.vm.BookListViewModel
import com.otaz.nytbooksapplication.presentation.vm.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var app: BaseApplication
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.BookList.route) {

                composable(
                    route = Screen.BookList.route
                ) {
                    val bookListViewModel = hiltViewModel<BookListViewModel>()

                    BookListScreen(
                        bookListViewModel = bookListViewModel,
                        onNavigateToBookDetailScreen = navController::navigate,
                    )
                }

                composable(
                    route = Screen.BookDetail.route + "/{title}",
                    arguments = listOf(navArgument("title") {
                        type = NavType.StringType
                    })
                ) { navBackStackEntry ->
                    val viewModel = hiltViewModel<BookDetailViewModel>()
                    BookDetailScreen(
                        title = navBackStackEntry.arguments?.getString("title"),
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}