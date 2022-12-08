package com.otaman.users_list.ui.states

import com.otaman.users_list.domain.models.UserProfile

sealed class UsersState {
    data class UsersProfilesData(val usersProfilesData: List<UserProfile>, val failCount: Int): UsersState()
    data class Error(val message: String): UsersState()
    object Loading: UsersState()
}