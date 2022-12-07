package com.otaman.users_list.ui.states

import com.otaman.users_list.domain.models.UserId

sealed class UsersIdsState {
    data class UsersIdsData(val usersIdsData: UserId): UsersIdsState()
    data class Error(val message: String): UsersIdsState()
    object Loading: UsersIdsState()
}