package com.app.neostoreassessment13531.neostore.data.local.intermediary

import androidx.room.Embedded
import androidx.room.Relation
import com.app.neostoreassessment13531.neostore.data.local.entities.AddressTable
import com.app.neostoreassessment13531.neostore.data.local.entities.EducationInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.ProfessionalInfoTable
import com.app.neostoreassessment13531.neostore.data.local.entities.UserTable


data class UserWithAddress(
    @Embedded val user:UserTable,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val address:AddressTable,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val education:EducationInfoTable,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val professionalInfo:ProfessionalInfoTable,
)
