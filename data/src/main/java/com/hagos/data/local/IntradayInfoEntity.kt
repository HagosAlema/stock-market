package com.hagos.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            childColumns = ["symbol"],
            parentColumns = ["symbol"],
            entity = CompanyListingEntity::class
        )
    ]
)
data class IntradayInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var symbol: String,
    var timestamp: String,
    var close: Double
)
