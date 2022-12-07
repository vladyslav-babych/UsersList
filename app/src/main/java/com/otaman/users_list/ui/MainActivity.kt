package com.otaman.users_list.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.otaman.users_list.ui.navigation.SetupNavGraph
import com.otaman.users_list.ui.theme.UsersListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UsersListTheme {
                navController = rememberAnimatedNavController()

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SetupNavGraph(navController = navController)
                }
            }
        }
    }
}
