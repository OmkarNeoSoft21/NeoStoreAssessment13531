package com.app.neostoreassessment13531.neostore.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.app.neostoreassessment13531.neostore.data.local.entities.AddressTable
import com.app.neostoreassessment13531.neostore.data.local.entities.EducationInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.ProfessionalInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable
import com.app.neostoreassessment13531.neostore.data.local.intermediary.UserWithAddress

@Dao
interface DaoUser {

    @Query("Select * from UserTable where user_id=:userId")
    fun getUserInfo(userId:String):UserWithAddress

    @Query("Select * from UserTable")
    fun getAllUsers():LiveData<List<UserWithAddress>>

    @Upsert
    suspend fun insertUser(user:UserTable) : Long

    @Upsert
    suspend fun insertAddress(address: AddressTable)

    @Upsert
    suspend fun insertEducation(education: EducationInfoTable)

    @Upsert
    suspend fun insertProfessional(professional: ProfessionalInfoTable)

    @Transaction
    suspend fun insertUserInfo(userInfo: UserWithAddress) {
        // Insert user and get generated user_id
        val userId = insertUser(userInfo.user).toInt()

        // Insert related tables with the obtained userId
        insertAddress(userInfo.address.copy(userId = userId))
        insertEducation(userInfo.education.copy(userId = userId))
        insertProfessional(userInfo.professionalInfo.copy(userId = userId))
    }

}