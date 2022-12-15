package com.otaman.users_list.fakes

import com.otaman.users_list.domain.models.UserData
import com.otaman.users_list.domain.models.UserId
import com.otaman.users_list.domain.models.UserProfile
import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.domain.util.Resource

class FakeUsersRepository: UsersRepository {
    val profiles = mapOf(
        "test_id1" to UserData(
            id = "test_id1",
            firstName = "Name1",
            lastName = "LastName1",
            age = 20,
            gender = "Male",
            country = "USA"
        ),
        "test_id2" to UserData(
            id = "test_id2",
            firstName = "Name2",
            lastName = "LastName2",
            age = 25,
            gender = "Female",
            country = "Canada"
        )
    )
    var getUserIdsResult = true

    override suspend fun getUsersIds(): Resource<UserId> {
        return if(getUserIdsResult) {
            Resource.Success(
                data = UserId(
                    status = "success",
                    ids = profiles.keys.toList()
                )
            )
        }
        else {
            Resource.Error(
                message = "Error occurred"
            )
        }
    }

    override suspend fun getUserProfileById(id: String): Resource<UserProfile> {
        return try {
            Resource.Success(
                data = UserProfile(
                    status = "success",
                    profileData = profiles[id]!!
                )
            )
        }
        catch (error: Exception) {
            Resource.Error(
                message = error.message ?: "Error occurred"
            )
        }
    }
}