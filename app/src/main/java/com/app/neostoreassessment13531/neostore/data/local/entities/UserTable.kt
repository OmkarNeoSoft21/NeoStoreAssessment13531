package com.app.neostoreassessment13531.neostore.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "UserTable")
data class UserTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("user_id") val userId: Int = 0,
    @ColumnInfo("first_name") val firstName: String,
    @ColumnInfo("last_name") val lastName: String,
    @ColumnInfo("phone_no") val phoneNumber: String,
    @ColumnInfo("email") val email: String,
    @ColumnInfo("gender") val gender: String,
)








