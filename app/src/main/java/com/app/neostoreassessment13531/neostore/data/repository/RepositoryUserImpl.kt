package com.app.neostoreassessment13531.neostore.data.repository

import com.app.neostoreassessment13531.neostore.data.local.repository.LocalDataSourceUser
import com.app.neostoreassessment13531.neostore.data.toAddressModel
import com.app.neostoreassessment13531.neostore.data.toEducationModel
import com.app.neostoreassessment13531.neostore.data.toProfessionalModel
import com.app.neostoreassessment13531.neostore.data.toUserDataModel
import com.app.neostoreassessment13531.neostore.data.toUserTable
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import com.app.neostoreassessment13531.neostore.domain.repository.RepositoryUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryUserImpl @Inject constructor(
    private val localDataSourceUser: LocalDataSourceUser
) : RepositoryUser {
    override suspend fun getUserInfo(userId: String): Flow<UserDataModel> {
        return localDataSourceUser.getUserInfo(userId).map {
            it.user.toUserDataModel().copy(
                addressModel = it.address.toAddressModel(),
                educationModel = it.education.toEducationModel(),
                professional = it.professionalInfo.toProfessionalModel()
            )
        }
    }

    override fun getAllUsers(): Flow<List<UserDataModel>> {
        return localDataSourceUser.getAllUserInfo().map {
            it.map { it.user.toUserDataModel().copy( professional = it.professionalInfo.toProfessionalModel()) }
        }
    }

    override suspend fun isUserPresent(user: UserDataModel): Boolean {
        return localDataSourceUser.isUserPresent(user.toUserTable())
    }

    override suspend fun saveUser(model: RegisterUserModel): Boolean {
        localDataSourceUser.saveUserDetails(model)
        return localDataSourceUser.isUserPresent(model.user.toUserTable())
    }
}