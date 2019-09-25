package com.remember.password.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.remember.password.database.entity.RecordEntity

@Dao
interface IRecordEntityDao {
    @Query("SELECT * FROM RecordEntity")
    fun getAllRecords(): LiveData<List<RecordEntity>>

    @Insert
    fun insertRecord(recordEntity: RecordEntity): Long

    @Delete
    fun deleteRecord(recordEntity: RecordEntity)

    @Update
    fun updateRecord(recordEntity: RecordEntity)
}