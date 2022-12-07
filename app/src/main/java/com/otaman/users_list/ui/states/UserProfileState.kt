package com.otaman.users_list.ui.states

import com.otaman.users_list.domain.models.UserProfile

sealed class UserProfileState {
    data class UserProfileData(
        val userProfileData: List<UserProfile>,
        val message: String
    ): UserProfileState()
    object Loading: UserProfileState()
}