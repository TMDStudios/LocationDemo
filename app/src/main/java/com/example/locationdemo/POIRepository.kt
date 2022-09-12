package com.example.locationdemo

class POIRepository(private val poiDao: POIDao) {
    val getPOIs: List<POI> = poiDao.getPOIs()

    suspend fun addPOI(poi: POI){
        poiDao.addPOI(poi)
    }

    suspend fun updatePOI(poi: POI){
        poiDao.updatePOI(poi)
    }

    suspend fun deletePOI(poi: POI){
        poiDao.deletePOI(poi)
    }

    suspend fun deleteAll(){
        poiDao.deleteAll()
    }

}