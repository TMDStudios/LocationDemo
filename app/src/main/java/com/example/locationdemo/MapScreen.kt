package com.example.locationdemo

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    var pointOfInterest by remember {
        mutableStateOf(mutableListOf(200.0,200.0))
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    var myPOI by remember {
        mutableStateOf("")
    }
    Scaffold() {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            onMapLongClick = {
                openDialog=!openDialog
                // Display Latitude and Longitude on long click
                Toast.makeText(context, "Lat: ${it.latitude}\n Long: ${it.longitude}", Toast.LENGTH_LONG).show()
                pointOfInterest = mutableListOf(it.latitude, it.longitude)
            }
        ){
            if(pointOfInterest[0]<=180){
                Marker(
                    position = LatLng(pointOfInterest[0], pointOfInterest[1]),
                    title = myPOI,
                    snippet = "${pointOfInterest[0].toString().substring(0,7)}, ${pointOfInterest[1].toString().substring(0,7)}",
                    icon = BitmapDescriptorFactory.defaultMarker(),
                    onClick = {
                        it.showInfoWindow()
                        true
                    }
                )
            }
            if(openDialog){
                AlertDialog(
                    onDismissRequest = {
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
                                onClick = { openDialog = false }
                            ) {
                                Text("Save POI")
                            }
                        }
                    }
                )
            }
        }
    }
}