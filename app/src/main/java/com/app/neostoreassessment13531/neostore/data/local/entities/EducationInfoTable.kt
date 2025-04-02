package com.app.neostoreassessment13531.neostore.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "EducationInfoTable", foreignKeys = [
        ForeignKey(
            entity = UserTable::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class EducationInfoTable(
    @ColumnInfo("user_id") val userId: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("ed_id") val edId: Int = 0,
    @ColumnInfo("education") val education: String,
    @ColumnInfo("year_of_passing") val yearOfPassing: String,
    @ColumnInfo("grade") val grade: String
)