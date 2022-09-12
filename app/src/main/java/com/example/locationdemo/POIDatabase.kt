package com.example.locationdemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [POI::class], version = 1, exportSchema = false)
abstract class POIDatabase: RoomDatabase() {
    abstract fun poiDao(): POIDao

    companion object{
        @Volatile
        private var INSTANCE: POIDatabase? = null

        fun getDatabase(context: Context): POIDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    POIDatabase::class.java,
                    "poi_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}