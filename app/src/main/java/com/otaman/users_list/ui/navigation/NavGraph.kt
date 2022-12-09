package com.otaman.users_list.ui.navigation

import android.net.Uri
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.otaman.users_list.domain.models.UserData
import com.otaman.users_list.ui.navigation.Screen.Profile.PROFILE_KEY
import com.otaman.users_list.ui.screens.main.MainScreen
import com.otaman.users_list.ui.screens.profile.ProfileScreen
import com.otaman.users_list.ui.viewmodels.MainScreenViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        exitTransition = {
            slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = 250
                ),
                targetOffsetX = {-it}
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                animationSpec = tween(
                    durationMillis = 250
                ),
                targetOffsetX = {it}
            )
        },
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = 250
                ),
                initialOffsetX = {it}
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = 250
                ),
                initialOffsetX = {-it}
            )
        }
    ) {
        composable(
            route = Screen.Main.route
        ) {
            val viewModel: MainScreenViewModel = hiltViewModel()
            val moshi: Moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val jsonAdapter: JsonAdapter<UserData> = moshi.adapter(UserData::class.java)

            MainScreen(
                viewModel = viewModel,
                onNameClick = { profile ->
                    val json = Uri.encode(jsonAdapter.toJson(profile))

                    navController.navigate(Screen.Profile.buildRoute(
                        profile = json
                    ))
                }
            )
        }
        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument(name = PROFILE_KEY) {
                    type = ProfileType()
                    nullable = false
                }
            )
        ) {
            val profileArg = checkNotNull(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.arguments?.getParcelable(PROFILE_KEY, UserData::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.arguments?.getParcelable(PROFILE_KEY)
                }
            )

            ProfileScreen(
                profile = profileArg,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}