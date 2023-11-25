package com.hagos.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(
    foreignKeys = [
        ForeignKey(
            childColumns = ["symbol"],
            parentColumns = ["symbol"],
            entity = CompanyListingEntity::class
        )
    ]
)
data class CompanyInfoEntity(
    @PrimaryKey val symbol: String,
    val description: String?,
    val name: String?,
    val country: String?,
    val industry: String?,
) {
}