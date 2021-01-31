package com.bombadu.pixablast.di

import android.content.Context
import androidx.room.Room
import com.bombadu.pixablast.R
import com.bombadu.pixablast.data.local.LocalDao
import com.bombadu.pixablast.data.local.LocalDatabase
import com.bombadu.pixablast.data.remote.PixabayApi
import com.bombadu.pixablast.other.Constants.BASE_URL
import com.bombadu.pixablast.other.Constants.DATABASE_NAME
import com.bombadu.pixablast.repositories.DefaultImagePostRepository
import com.bombadu.pixablast.repositories.ImagePostRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideLocalDao(
        database: LocalDatabase
    ) = database.localDao()

    @Singleton
    @Provides
    fun provideDefaultImagePostRepository(
        dao: LocalDao,
        api: PixabayApi
    ) = DefaultImagePostRepository(dao, api) as ImagePostRepository

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayApi::class.java)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.placeholder_gradient)
            .error(R.drawable.placeholder_gradient)
    )

}