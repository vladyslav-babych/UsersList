package com.otaman.users_list.ui.navigation


sealed class Screen(val route: String) {
    object Main: Screen(route = "main_screen")
    object Profile: Screen(route = "profile_screen/{profile}") {
        fun buildRoute(profile: String): String {
            return "profile_screen/$profile"
        }

        const val PROFILE_KEY = "profile"
    }
}