package com.app.neostoreassessment13531.neostore.domain.repository

import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import kotlinx.coroutines.flow.Flow

interface RepositoryUser {
    fun getUserInfo(userId: String): Flow<UserDataModel>

    fun getAllUsers(): Flow<List<UserDataModel>>

    suspend fun saveUser(model: RegisterUserModel):Boolean
}