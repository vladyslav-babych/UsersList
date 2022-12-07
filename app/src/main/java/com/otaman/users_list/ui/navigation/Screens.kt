package com.otaman.users_list.ui.navigation

sealed class Screen(val route: String) {
    object Main: Screen(route = "main_screen")
    object Profile: Screen(route = "profile_screen/{firstName}/{lastName}/{age}/{gender}/{country}") {
        fun buildRoute(
            firstName: String = "Dwayne",
            lastName: String = "Johnson",
            age: String = "50",
            gender: String = "Male",
            country: String = "USA"
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