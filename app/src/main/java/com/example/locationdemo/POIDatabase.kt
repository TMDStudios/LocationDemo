package com.example.locationdemo

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [POI::class], version = 1, exportSchema = false)
abstract class POIDatabase: RoomDatabase() {
    abstract fun poiDao(): POIDao
}