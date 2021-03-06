package com.bombadu.pixablast.data.local

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface LocalDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(localData: LocalData)


    @Delete
    suspend fun deleteData(localData: LocalData)


    @Query("SELECT * FROM data_table")
    fun observeAllData(): LiveData<List<LocalData>>
}