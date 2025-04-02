package com.app.neostoreassessment13531.neostore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.neostoreassessment13531.neostore.data.local.dao.DaoUser
import com.app.neostoreassessment13531.neostore.data.local.entities.AddressTable
import com.app.neostoreassessment13531.neostore.data.local.entities.EducationInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.ProfessionalInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable


@Database(entities = [UserTable::class, AddressTable::class, EducationInfoTable::class, ProfessionalInfoTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDao() : DaoUser
}