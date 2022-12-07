package com.otaman.users_list.networking.responses

import com.squareup.moshi.Json

data class UserProfileResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: User
) {
    data class User(
        @Json(name = "id") val id: String,
        @Json(name = "firstName") val firstName: String,
        @Json(name = "lastName") val lastName: String,
        @Json(name = "age") val age: Int,
        @Json(name = "gender") val gender: String,
        @Json(name = "country") val country: String
    )
}