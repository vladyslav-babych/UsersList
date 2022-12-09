package com.otaman.users_list.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserProfile(
    val status: String,
    val profileData: UserData
)

@Parcelize
data class UserData(
    val id: String,
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: String,
    val country: String
): Parcelable