package com.otaman.users_list.ui.navigation

sealed class Screen(val route: String) {
    object Main: Screen(route = "main_screen")
    object Profile: Screen(route = "profile_screen/{firstName}/{lastName}/{age}/{gender}/{country}") {
        fun buildRoute(
            firstName: String,
            lastName: String,
            age: String,
            gender: String,
            country: String
        ): String {
            return "profile_screen/$firstName/$lastName/$age/$gender/$country"
        }

        const val FIRST_NAME_KEY = "firstName"
        const val LAST_NAME_KEY = "lastName"
        const val AGE_KEY = "age"
        const val GENDER_KEY = "gender"
        const val COUNTRY_KEY = "country"
    }
}