package com.remember.password.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.remember.password.database.entity.RecordEntity

@Dao
interface IRecordEntityDao {
    @Query("Select * from RecordEntity where title LIKE :filterText")
    fun filterRecord(filterText: String): LiveData<List<RecordEntity>>

    @Query("SELECT * FROM RecordEntity")
    fun getAllRecords(): LiveData<List<RecordEntity>>

    @Insert
    fun insertRecord(recordEntity: RecordEntity): Long

    @Delete
    fun deleteRecord(recordEntity: List<RecordEntity>)

    @Update
    fun updateRecord(recordEntity: RecordEntity)
}