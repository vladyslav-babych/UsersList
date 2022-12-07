package com.otaman.users_list.networking

import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.util.Resource

class UsersRepositoryImpl(
    private val usersService: UsersService,
    private val jwtManager: JWTManager
): UsersRepository {
    override suspend fun getUsersIds(): Resource<UserId> {
        return try {
            Resource.Success(
                data = usersService.getUsersIds(
                    token = "Bearer ${jwtManager.getToken()}"
                ).toUsersIds()
            )
        }
        catch (error: Exception) {
            error.printStackTrace()
            Resource.Error(
                message = error.message ?: "Error occurred"
            )
        }
    }

    override suspend fun getUserProfileById(id: String): Resource<UserProfile> {
        return try {
            Resource.Success(
                data = usersService.getUserProfileById(
                    id = id,
                    token = "Bearer ${jwtManager.getToken()}"
                ).toUserProfile()
            )
        }
        catch (error: Exception) {
            error.printStackTrace()
            Resource.Error(
                message = error.message ?: "Error occurred"
            )
        }
    }
}