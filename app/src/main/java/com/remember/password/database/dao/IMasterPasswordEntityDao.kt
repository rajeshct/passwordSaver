package com.remember.password.database.dao

import androidx.room.*
import com.remember.password.database.entity.MasterPasswordEntity

@Dao
interface IMasterPasswordEntityDao {

    @Query("SELECT * FROM MasterPasswordEntity where primaryKey=1 limit 1")
    fun getAllRecords(): MasterPasswordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(masterPasswordEntity: MasterPasswordEntity): Long

    @Delete
    fun deleteRecord(masterPasswordEntity: MasterPasswordEntity)

    @Update
    fun updateRecord(masterPasswordEntity: MasterPasswordEntity)
}