package com.otaman.users_list.networking

import com.otaman.users_list.domain.models.UserData
import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.networking.responses.UserProfileResponse
import com.otaman.users_list.networking.responses.UserProfileResponse.User
import com.otaman.users_list.networking.responses.UsersIdsResponse

fun UsersIdsResponse.toUsersIds(): UserId {
    return UserId(
        status = status,
        data = data
    )
}

fun UserProfileResponse.toUserProfile(): UserProfile {
    return UserProfile(
        status = status,
        data = data.toUserData()
    )
}

fun User.toUserData(): UserData {
    return UserData(
        id = id,
        firstName = firstName,
        lastName = lastName,
        age = age,
        gender = gender,
        country = country
    )
}