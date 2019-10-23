package com.remember.password.repository

import androidx.lifecycle.LiveData
import com.remember.password.database.AppDatabase
import com.remember.password.database.entity.MasterPasswordEntity
import com.remember.password.database.entity.RecordEntity

class Repository(private val appDatabase: AppDatabase) {

    fun getListingRecord(): LiveData<List<RecordEntity>> {
        return appDatabase.getRecordDao().getAllRecords()
    }

    fun insertRecordEntity(recordEntity: RecordEntity) {
        appDatabase.getRecordDao().insertRecord(recordEntity)
    }

    fun isMasterPasswordSet(): Boolean {
        return appDatabase.getMasterPasswordDao().getAllRecords() != null
    }

    fun saveMasterPassword(password: String) {
        val masterPassword = MasterPasswordEntity(password = password)
        appDatabase.getMasterPasswordDao().insertRecord(masterPassword)
    }

    fun isMasterPasswordMatched(password: String): Boolean {
        val masterPassword = appDatabase.getMasterPasswordDao().getAllRecords()
        return masterPassword != null && password.contentEquals(masterPassword.password)
    }

}