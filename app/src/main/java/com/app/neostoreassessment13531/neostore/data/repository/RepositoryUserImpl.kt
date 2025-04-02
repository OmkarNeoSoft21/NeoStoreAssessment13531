package com.app.neostoreassessment13531.neostore.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.data.local.intermediary.UserWithAddress
import com.app.neostoreassessment13531.neostore.data.local.repository.LocalDataSourceUser
import com.app.neostoreassessment13531.neostore.data.toAddressModel
import com.app.neostoreassessment13531.neostore.data.toAddressTable
import com.app.neostoreassessment13531.neostore.data.toEducationInfoTable
import com.app.neostoreassessment13531.neostore.data.toEducationModel
import com.app.neostoreassessment13531.neostore.data.toProfessionalModel
import com.app.neostoreassessment13531.neostore.data.toProfessionalTable
import com.app.neostoreassessment13531.neostore.data.toUserDataModel
import com.app.neostoreassessment13531.neostore.data.toUserTable
import com.app.neostoreassessment13531.neostore.domain.model.RegisterUserModel
import com.app.neostoreassessment13531.neostore.domain.model.UserDataModel
import com.app.neostoreassessment13531.neostore.domain.repo.RepositoryUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryUserImpl @Inject constructor(
    private val localDataSourceUser: LocalDataSourceUser
) : RepositoryUser {
    override fun getUserInfo(userId: String): Flow<UserDataModel> {
        return localDataSourceUser.getUserInfo(userId).map {
            it.user.toUserDataModel()
                .copy(
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

    override suspend fun saveUser(model: RegisterUserModel) : Boolean {
        val id = localDataSourceUser.saveUserInfo(model.user.toUserTable())
        if (id == -1){
            return false
        }else{
            localDataSourceUser.saveUserAddress(model.address.toAddressTable(id))
            localDataSourceUser.saveUserProfessional(model.professional.toProfessionalTable(id))
            localDataSourceUser.saveUserEducation(model.education.toEducationInfoTable(id))
            return true
        }
    }
}