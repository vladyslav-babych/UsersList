package com.otaman.users_list.domain.models

data class UserProfile(
    val status: String,
    val data: UserData
)

data class UserData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val country: String
)