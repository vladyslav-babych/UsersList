package com.otaman.users_list.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.otaman.users_list.ui.navigation.Screen.Profile.AGE_KEY
import com.otaman.users_list.ui.navigation.Screen.Profile.COUNTRY_KEY
import com.otaman.users_list.ui.navigation.Screen.Profile.FIRST_NAME_KEY
import com.otaman.users_list.ui.navigation.Screen.Profile.GENDER_KEY
import com.otaman.users_list.ui.navigation.Screen.Profile.LAST_NAME_KEY
import com.otaman.users_list.ui.screens.main.MainScreen
import com.otaman.users_list.ui.screens.profile.ProfileScreen
import com.otaman.users_list.ui.viewmodels.MainScreenViewModel

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

            MainScreen(
                viewModel = viewModel,
                onNameClick = { firstName, lastName, age, gender, country ->
                    navController.navigate(Screen.Profile.buildRoute(
                        firstName = firstName,
                        lastName = lastName,
                        age = age,
                        gender = gender,
                        country = country
                    ))
                }
            )
        }
        composable(
            route = Screen.Profile.route,
            arguments = listOf(
                navArgument(name = FIRST_NAME_KEY) {
                    type = NavType.StringType
                    defaultValue = "Dwayne"
                    nullable = false
                },
                navArgument(name = LAST_NAME_KEY) {
                    type = NavType.StringType
                    defaultValue = "Johnson"
                    nullable = false
                },
                navArgument(name = AGE_KEY) {
                    type = NavType.StringType
                    defaultValue = "50"
                    nullable = false
                },
                navArgument(name = GENDER_KEY) {
                    type = NavType.StringType
                    defaultValue = "Male"
                    nullable = false
                },
                navArgument(name = COUNTRY_KEY) {
                    type = NavType.StringType
                    defaultValue = "Dwayne"
                    nullable = false
                }
            )
        ) {
            val firstNameArg = it.arguments?.getString(FIRST_NAME_KEY) ?: "Dwayne"
            val lastNameArg = it.arguments?.getString(LAST_NAME_KEY) ?: "Johnson"
            val ageArg = it.arguments?.getString(AGE_KEY) ?: "50"
            val genderArg = it.arguments?.getString(GENDER_KEY) ?: "Male"
            val countryArg = it.arguments?.getString(COUNTRY_KEY) ?: "USA"

            ProfileScreen(
                firstName = firstNameArg,
                lastName = lastNameArg,
                age = ageArg,
                gender = genderArg,
                country = countryArg,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}