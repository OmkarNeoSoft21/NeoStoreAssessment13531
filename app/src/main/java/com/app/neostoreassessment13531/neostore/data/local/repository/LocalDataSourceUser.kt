package com.app.neostoreassessment13531.neostore.data.local.repository

import androidx.lifecycle.LiveData
import com.app.neostoreassessment13531.neostore.data.local.entities.AddressTable
import com.app.neostoreassessment13531.neostore.data.local.entities.EducationInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.ProfessionalInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.data.local.intermediary.UserWithAddress
import kotlinx.coroutines.flow.Flow

interface LocalDataSourceUser {

    fun getAllUserInfo(): Flow<List<UserWithAddress>>

    fun getUserInfo(userId: String): Flow<UserWithAddress>

    suspend fun saveUserInfo(user: UserTable) : Int

    suspend fun saveUserAddress(user: AddressTable)

    suspend fun saveUserProfessional(user: ProfessionalInfoTable)

    suspend fun saveUserEducation(user: EducationInfoTable)
}