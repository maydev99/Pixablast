package com.bombadu.pixablast.repositories

import com.bombadu.pixablast.data.local.LocalData
import com.bombadu.pixablast.data.remote.PixabayData
import com.bombadu.pixablast.other.Resource


interface ImagePostRepository{

    suspend fun searchForImage (imageQuery: String): Resource<PixabayData>

    suspend fun insertEntry(localData: LocalData)

   /* suspend fun deleteEntry(localData: LocalData)

    fun observeAllEntries(): LiveData<List<LocalData>>*/
}