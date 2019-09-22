package com.remember.password.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Int
    , var name: String, var password: String
)