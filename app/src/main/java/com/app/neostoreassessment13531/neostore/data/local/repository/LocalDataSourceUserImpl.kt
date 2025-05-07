package com.app.neostoreassessment13531.neostore.data.local.repository

import androidx.lifecycle.asFlow
import com.app.neostoreassessment13531.neostore.data.local.dao.DaoUser
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.data.local.intermediary.UserWithAddress
import com.app.neostoreassessment13531.neostore.data.toAddressTable
import com.app.neostoreassessment13531.neostore.data.toEducationInfoTable
import com.app.neostoreassessment13531.neostore.data.toProfessionalTable
import com.app.neostoreassessment13531.neostore.data.toUserTable
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LocalDataSourceUserImpl @Inject constructor(private val daoUser: DaoUser) :
    LocalDataSourceUser {
    override fun getAllUserInfo(): Flow<List<UserWithAddress>> {
        return daoUser.getAllUsers().asFlow()
    }

    override suspend fun getUserInfo(userId: String): Flow<UserWithAddress> {
        val userTable = daoUser.getUserInfo(userId)
        return flowOf(userTable)
    }

    override suspend fun saveUserInfo(user: UserTable): Int {
       return daoUser.insertUser(user).toInt()
    }

    override suspend fun isUserPresent(user: UserTable): Boolean {
        return daoUser.isUserPresent(user.email , user.phoneNumber)
    }

    override suspend fun saveUserDetails(user: RegisterUserModel){
        daoUser.insertUserInfo(UserWithAddress(
            user = user.user.toUserTable(),
            address = user.address.toAddressTable(),
            education = user.education.toEducationInfoTable(),
            professionalInfo = user.professional.toProfessionalTable()
        ))
    }

}