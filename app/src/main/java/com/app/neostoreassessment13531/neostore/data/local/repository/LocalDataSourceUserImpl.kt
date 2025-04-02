package com.app.neostoreassessment13531.neostore.data.local.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.app.neostoreassessment13531.neostore.data.local.dao.DaoUser
import com.app.neostoreassessment13531.neostore.data.local.entities.AddressTable
import com.app.neostoreassessment13531.neostore.data.local.entities.EducationInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.ProfessionalInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.data.local.intermediary.UserWithAddress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceUserImpl @Inject constructor(private val daoUser: DaoUser) :
    LocalDataSourceUser {
    override fun getAllUserInfo(): Flow<List<UserWithAddress>> {
        return daoUser.getAllUsers().asFlow()
    }

    override fun getUserInfo(userId: String): Flow<UserWithAddress> {
        return flow {
            daoUser.getUserInfo(userId)
        }
    }

    override suspend fun saveUserInfo(user: UserTable): Int {
       return daoUser.insertUser(user).toInt()
    }

    override suspend fun saveUserAddress(user: AddressTable) {
        return daoUser.insertAddress(user)
    }

    override suspend fun saveUserProfessional(user: ProfessionalInfoTable) {
        return daoUser.insertProfessional(user)
    }

    override suspend fun saveUserEducation(user: EducationInfoTable) {
        return daoUser.insertEducation(user)
    }

}