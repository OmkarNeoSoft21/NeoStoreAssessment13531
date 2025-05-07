package com.app.neostoreassessment13531.neostore.data.local.repository

import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.data.local.intermediary.UserWithAddress
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceUser {

    fun getAllUserInfo(): Flow<List<UserWithAddress>>

    suspend fun getUserInfo(userId: String): Flow<UserWithAddress>

    suspend fun saveUserInfo(user: UserTable) : Int

    suspend fun isUserPresent(user: UserTable) : Boolean

    suspend fun saveUserDetails(user: RegisterUserModel)
}