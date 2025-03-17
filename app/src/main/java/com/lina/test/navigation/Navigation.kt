package com.lina.test.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.gson.Gson
import com.lina.domain.models.News
import com.lina.test.R
import com.lina.test.ui.screens.DetailScreen
import com.lina.test.ui.screens.MainScreen
import com.lina.test.utils.CollectEffect
import com.lina.test.viewmodels.MainViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route,
            enterTransition = { fadeIn(animationSpec = tween(1000)) },
            exitTransition = { fadeOut(animationSpec = tween(1000)) }
        ) {
            val errorMsg = stringResource(R.string.error)
            val viewModel: MainViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                viewModel.sendEvent(MainViewModel.Event.LoadData)
            }
            MainScreen(
                title = "List",
                state = viewModel.uiState.collectAsState().value,
                onItemClick = { news ->
                    viewModel.sendEvent(
                        MainViewModel.Event.NavigateToDetail(
                            news
                        )
                    )
                },
                onLoadData = {
                    viewModel.sendEvent(MainViewModel.Event.LoadData)
                },
                onItemDelete = { news ->
                    viewModel.sendEvent(
                        MainViewModel.Event.DeleteData(
                            news
                        )
                    )
                },
                onErrorMsg = { snackbarHostState ->
                    viewModel.sendEvent(
                        MainViewModel.Event.ShowNetworkError(snackbarHostState)
                    )
                }
            )

            CollectEffect(effect = viewModel.effect) { effect ->
                when (effect) {
                    is MainViewModel.Effect.ShowNetworkError -> {
                        effect.snackbarHostState. showSnackbar(errorMsg)
                    }

                    is MainViewModel.Effect.NavigateToDetail -> {
                        val encode = Uri.encode(Gson().toJson(effect.news))
                        navController.navigate("${Screen.Detail.route}/${encode}")
                    }
                }
            }
        }
        composable(
            route = "${Screen.Detail.route}/{news}",
            arguments = listOf(navArgument("news") { type = NavType.StringType }),
            enterTransition = { fadeIn(animationSpec = tween(2000)) },
            exitTransition = { fadeOut(animationSpec = tween(2000)) }
        ) { backStackEntry ->
            val context = LocalContext.current
            val arg = backStackEntry.arguments?.getString("news")
            val news = Gson().fromJson(arg, News::class.java)
            if (news != null) {
                DetailScreen(
                    title = "${news.mediaId}",
                    item = news,
                    onBackClick = {
                        navController.popBackStack(Screen.Main.route, false)
                    },
                    onPdfClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            setDataAndType(Uri.parse(news.mediaUrl), "application/pdf")
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        context.startActivity(intent)
                    }
                )
            } else {
                navController.navigate(Screen.Main.route)
            }
        }
    }
}