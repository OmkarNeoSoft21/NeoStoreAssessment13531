package com.app.neostoreassessment13531.neostore.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ProfessionalInfoTable", foreignKeys = [
        ForeignKey(
            entity = UserTable::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProfessionalInfoTable(
    @ColumnInfo("user_id") val userId: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("pr_id") val prId: Int = 0,
    @ColumnInfo("experience") val experience: String,
    @ColumnInfo("designation") val designation: String,
    @ColumnInfo("domain") val domain: String
)