package com.example.locationdemo

import androidx.room.*

@Dao
interface POIDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPOI(poi: POI)

    @Query("SELECT * FROM POITable ORDER BY id ASC")
    fun getPOIs(): List<POI>

    @Update
    suspend fun updatePOI(poi: POI)

    @Delete
    suspend fun deletePOI(poi: POI)

    @Query("DELETE FROM POITable")
    fun deleteAll()

}