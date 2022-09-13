package com.example.locationdemo

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MapScreen() {
    val context = LocalContext.current
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    val poiDao by lazy { POIDatabase.getDatabase(context).poiDao() }
    val repository by lazy { POIRepository(poiDao) }

    var pointsOfInterest by remember {
        mutableStateOf(mutableListOf<POI>())
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    var myPOI by remember {
        mutableStateOf("")
    }

    var currentPOI: POI? = null

    var loadPOIs by remember {
        mutableStateOf(true)
    }

    var showPOIs by remember {
        mutableStateOf(true)
    }

    Scaffold() {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            onMapLongClick = {
                openDialog=!openDialog
                // Display Latitude and Longitude on long click
                Toast.makeText(context, "Lat: ${it.latitude}\n Long: ${it.longitude}", Toast.LENGTH_LONG).show()

                currentPOI = POI(0, it.latitude, it.longitude, myPOI)
            }
        ){
            if(loadPOIs){
                CoroutineScope(IO).launch {
                    pointsOfInterest = repository.getPOIs as MutableList<POI>
                    loadPOIs=false
                }
            }
            for(poi:POI in pointsOfInterest){
                if(poi.latitude<=180){
                    Marker(
                        position = LatLng(poi.latitude, poi.longitude),
                        title = poi.name,
                        snippet = "${poi.latitude.toString().substring(0,7)}, ${poi.longitude.toString().substring(0,7)}",
                        icon = BitmapDescriptorFactory.defaultMarker(),
                        onClick = {
                            it.showInfoWindow()
                            true
                        }
                    )
                }
            }
            if(openDialog){
                AlertDialog(
                    onDismissRequest = {
                        myPOI = ""
                        openDialog = false
                    },
                    title = {
                        Text(text = "Enter POI Name:")
                    },
                    text = {
                        TextField(
                            value = myPOI,
                            onValueChange = { myPOI = it }
                        )
                    },
                    buttons = {
                        Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    if(currentPOI!=null){
                                        currentPOI!!.name = myPOI
                                        pointsOfInterest.add(POI(0, currentPOI!!.latitude, currentPOI!!.longitude, myPOI))
                                        CoroutineScope(IO).launch {
                                            repository.addPOI(currentPOI!!)
                                            loadPOIs=true
                                        }
                                    }
                                    myPOI = ""
                                    openDialog = false
                                }
                            ) {
                                Text("Save POI")
                            }
                        }
                    }
                )
            }
        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
            Button(onClick = {
                CoroutineScope(IO).launch {
                    repository.deleteAll()
                    pointsOfInterest.clear()
                    loadPOIs=true
                }
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = colorResource(id = R.color.purple_500)
                )) {
                Text(text = "Delete POIs")
            }
            Button(onClick = {
                showPOIs=!showPOIs
                if(showPOIs){
                    loadPOIs=true
                }else{
                    pointsOfInterest = mutableListOf()
                }
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = colorResource(id = R.color.purple_500)
                )) {
                Text(text = if(showPOIs){"Hide POIs"}else{"Show POIs"})
            }
        }
    }
}