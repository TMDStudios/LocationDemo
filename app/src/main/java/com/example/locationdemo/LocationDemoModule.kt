package com.example.locationdemo

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationDemoModule {
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, POIDatabase::class.java, "poi_database")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providePOIDao(db: POIDatabase) = db.poiDao()
}