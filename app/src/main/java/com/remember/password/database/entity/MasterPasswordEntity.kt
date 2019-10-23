package com.remember.password.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MasterPasswordEntity(
    @PrimaryKey val primaryKey: Int = 1,

    val password: String
)