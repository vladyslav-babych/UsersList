package com.otaman.users_list.networking.responses

import com.squareup.moshi.Json

data class UsersIdsResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val ids: List<String>
)
