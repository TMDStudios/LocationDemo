package com.example.locationdemo

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker

@Composable
fun MapScreen() {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    var pointOfInterest by remember {
        mutableStateOf(mutableListOf(0.0,0.0))
    }
    Scaffold() {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            onMapLongClick = {
                // Display Latitude and Longitude on long click
                Toast.makeText(context, "Lat: ${it.latitude}\n Long: ${it.longitude}", Toast.LENGTH_LONG).show()
                pointOfInterest = mutableListOf(it.latitude, it.longitude)
            }
        ){
            Marker(
                position = LatLng(pointOfInterest[0], pointOfInterest[1]),
                title = "Your Point of Interest",
                snippet = "This is a test",
                icon = BitmapDescriptorFactory.defaultMarker(),
                onClick = {
                    it.showInfoWindow()
                    true
                }
            )
        }
    }
}