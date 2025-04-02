package com.app.neostoreassessment13531.neostore.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "AddressTable", foreignKeys = [
        ForeignKey(
            entity = UserTable::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AddressTable(
    @ColumnInfo("user_id") val userId: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("address_id") val addressId: Int = 0,
    @ColumnInfo("address") val address: String,
    @ColumnInfo("landmark") val landmark: String,
    @ColumnInfo("city") val city: String,
    @ColumnInfo("state") val state: String,
)