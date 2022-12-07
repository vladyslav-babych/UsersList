package com.otaman.users_list.domain.repository

import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.util.Resource

interface UsersRepository {
    suspend fun getUsersIds(): Resource<UserId>
    suspend fun getUserProfileById(id: String): Resource<UserProfile>
}