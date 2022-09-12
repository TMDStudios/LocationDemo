package com.example.locationdemo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "POITable")
data class POI(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    var name: String)