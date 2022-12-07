package com.otaman.users_list.networking

import com.otaman.users_list.networking.responses.UserProfileResponse
import com.otaman.users_list.networking.responses.UsersIdsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UsersService {
    @GET("list")
    suspend fun getUsersIds(
        @Header("Authorization") token: String,
    ): UsersIdsResponse

    @GET("get/{id}")
    suspend fun getUserProfileById(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): UserProfileResponse
}