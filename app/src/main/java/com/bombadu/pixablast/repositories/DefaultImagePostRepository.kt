package com.bombadu.pixablast.repositories

import android.util.Log
import com.bombadu.pixablast.data.local.LocalDao
import com.bombadu.pixablast.data.local.LocalData
import com.bombadu.pixablast.data.remote.PixabayApi
import com.bombadu.pixablast.data.remote.PixabayData
import com.bombadu.pixablast.other.Resource
import javax.inject.Inject


class DefaultImagePostRepository @Inject constructor(
    private val localDao: LocalDao,
    private val pixabayApi: PixabayApi
) : ImagePostRepository {
    override suspend fun searchForImage(imageQuery: String): Resource<PixabayData> {
        return try{
            val response = pixabayApi.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            } else {
                Resource.error("An unknown error occurred", null)
            }

        }catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun insertEntry(localData: LocalData) {
        localDao.insertData(localData)
    }



}