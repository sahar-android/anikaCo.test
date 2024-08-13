package com.application.aniktest.DI

import android.content.Context
import androidx.room.Room
import com.application.aniktest.data.database.AllDao
import com.application.aniktest.data.database.ResturantDatabase
import com.application.aniktest.repository.ResturantRepository
import com.application.aniktest.repository.ResturantRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RoomDI {

    @Provides
    @Singleton
    fun provideResturantDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(
            context = context,
            klass = ResturantDatabase::class.java,
            name = "resturant"
        )
        .allowMainThreadQueries()
        .build()


    @Provides
    @Singleton
    fun provideAllDao(resturantDatabase: ResturantDatabase)=
        resturantDatabase.allDao


    @Provides
    @Singleton
    fun provideResturantRepository(allDao: AllDao):ResturantRepository
    =ResturantRepositoryImpl(allDao)
}