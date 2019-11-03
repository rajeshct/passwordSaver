package com.remember.password.repository

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.remember.password.data.UiRecord
import com.remember.password.database.AppDatabase
import com.remember.password.database.entity.MasterPasswordEntity
import com.remember.password.database.entity.RecordEntity

class Repository(private val appDatabase: AppDatabase) {

    fun getPasswordRecordForUi(): LiveData<List<UiRecord>> {
        return Transformations.map(getListingRecord(), Function {
            val allRecords = mutableListOf<UiRecord>()
            it.forEach { recordEntity ->
                with(recordEntity) {
                    allRecords.add(
                        UiRecord(
                            id = id ?: 0,
                            userName = userName,
                            title = title,
                            pwd = password
                        )
                    )
                }
            }
            return@Function allRecords
        })
    }

    fun getRecordBasedOnUserSearch(filterText: String): LiveData<List<UiRecord>> {
        return Transformations.map(getFilterRecord(filterText)) {
            val allRecords = mutableListOf<UiRecord>()
            it.forEach { recordEntity ->
                with(recordEntity) {
                    allRecords.add(
                        UiRecord(
                            id = id ?: 0,
                            userName = userName,
                            title = title,
                            pwd = password
                        )
                    )
                }
            }
            return@map allRecords
        }
    }

    private fun getListingRecord(): LiveData<List<RecordEntity>> {
        return appDatabase.getRecordDao().getAllRecords()
    }

    private fun getFilterRecord(filterText: String): LiveData<List<RecordEntity>> {
        return appDatabase.getRecordDao().filterRecord("%$filterText%")
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
        return masterPassword != null && masterPassword.password.contentEquals(password)
    }

    fun removeRecords(deletedItems: MutableList<UiRecord>?) {
        if (!deletedItems.isNullOrEmpty()) {
            appDatabase.getRecordDao().deleteRecord(deletedItems.map {
                return@map RecordEntity(id = it.id)
            })
            deletedItems.clear()
        }
    }

    fun updateRecordEntity(it: RecordEntity) {
        appDatabase.getRecordDao().updateRecord(it)
    }

}