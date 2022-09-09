package com.example.locationdemo

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    Scaffold() {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            onMapLongClick = {
                // Display Latitude and Longitude on long click
                Toast.makeText(context, "Lat: ${it.latitude}\n Long: ${it.longitude}", Toast.LENGTH_LONG).show()
            }
        )
    }
}