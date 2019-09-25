package com.remember.password.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null
    , @ColumnInfo(name = "title") var title: String = ""
    , @ColumnInfo(name = "password") var password: String = ""
    , @ColumnInfo(name = "userName") var userName: String = ""
)