package com.example.e_library.ui.theme.screens.map

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.e_library.MainActivity
import com.example.e_library.ui.theme.ELibraryTheme
import com.example.e_library.ui.theme.PurpleGrey40
import com.example.e_library.ui.theme.screens.dashboard.DashTopBar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.StrokeStyle
import com.google.android.gms.maps.model.StyleSpan
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.DragState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

val cbd = LatLng(-1.286389, 36.817223)
val westlands = LatLng(-1.2635, 36.8021)
val buruburu = LatLng(-1.2870, 36.8697)
val kileleshwa = LatLng(-1.2921, 36.7684)
val langata = LatLng(-1.3395, 36.7682)
val karen = LatLng(-1.3267, 36.7111)
val lavington = LatLng(-1.2725, 36.7789)
val parklands = LatLng(-1.2623, 36.8116)
val southB = LatLng(-1.3121, 36.8226)
val eastleigh = LatLng(-1.2734, 36.8422)
val ngongRoad = LatLng(-1.3019, 36.7474)
val rongai = LatLng(-1.3951, 36.7447)
val kasarani = LatLng(-1.2244, 36.9053)
val donholm = LatLng(-1.2977, 36.8914)
val thikaRoad = LatLng(-1.1958, 36.9318)
val mombasaRoad = LatLng(-1.3601, 36.8982)
val ruaka = LatLng(-1.1974, 36.7672)
val juja = LatLng(-1.1082, 37.0158)
val kahawa = LatLng(-1.1807, 36.9279)
val kiambuRoad = LatLng(-1.2322, 36.8349)
val githurai = LatLng(-1.2082, 36.9024)// Another example location in Nairobi
// Define more Nairobi locations as needed

val defaultCameraPosition = CameraPosition.fromLatLngZoom(cbd, 14f)

val styleSpan = StyleSpan(
    StrokeStyle.gradientBuilder(
        Color.Red.toArgb(),
        Color.Green.toArgb(),
    ).build(),
)
@Composable
fun GoogleMapView(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMapLoaded: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    // Create FusedLocationProviderClient
    val fusedLocationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Remember the user's current location
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Coroutine scope for launching location updates
    val coroutineScope = rememberCoroutineScope()


    val locationPermissionRequestCode = 1

    fun getLastKnownLocation(
        fusedLocationClient: FusedLocationProviderClient,
        onLocationResult: (android.location.Location?) -> Unit
    ) {
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onLocationResult(task.result)
            } else {
                Log.w("GoogleMapView", "Failed to get location.")
                onLocationResult(null)
            }
        }
    }

    val updateLocationAndCamera: () -> Unit = {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as MainActivity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionRequestCode
            )
        } else {
            // Permission is granted, get the last known location
            getLastKnownLocation(fusedLocationClient) { location ->
                location?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    // Move the camera to the user's location
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(userLocation!!, 15f)
                        )
                    }
                }
            }
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            updateLocationAndCamera()
            Toast.makeText(context, "\uD83D\uDE0A", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Permission Denied \uD83D\uDE2D", Toast.LENGTH_LONG).show()
            Log.d("GoogleMapView", "Location permission denied")
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            updateLocationAndCamera()
            Toast.makeText(context, "\uD83D\uDE0A", Toast.LENGTH_LONG).show()
        }
    }


    // Rest of your existing code...
    val cbdState = rememberMarkerState(position = cbd)
    val westlandsState = rememberMarkerState(position = westlands)
    val buruburuState = rememberMarkerState(position = buruburu)
    val kileleshwaState = rememberMarkerState(position = kileleshwa)
    val langataState = rememberMarkerState(position = langata)
    val karenState = rememberMarkerState(position = karen)
    val lavingtonState = rememberMarkerState(position = lavington)
    val parklandsState = rememberMarkerState(position = parklands)
    val southBState = rememberMarkerState(position = southB)
    val eastleighState = rememberMarkerState(position = eastleigh)
    val ngongRoadState = rememberMarkerState(position = ngongRoad)
    val rongaiState = rememberMarkerState(position = rongai)
    val kasaraniState = rememberMarkerState(position = kasarani)
    val donholmState = rememberMarkerState(position = donholm)
    val thikaRoadState = rememberMarkerState(position = thikaRoad)
    val mombasaRoadState = rememberMarkerState(position = mombasaRoad)
    val ruakaState = rememberMarkerState(position = ruaka)
    val jujaState = rememberMarkerState(position = juja)
    val kahawaState = rememberMarkerState(position = kahawa)
    val kiambuRoadState = rememberMarkerState(position = kiambuRoad)
    val githuraiState = rememberMarkerState(position = githurai)

    var circleCenter by remember { mutableStateOf(cbd) }
    if (cbdState.dragState == DragState.END) {
        circleCenter = cbdState.position
    }

    val polylinePoints = remember { listOf(westlands, buruburu) }
    val polylineSpanPoints = remember { listOf(juja, mombasaRoad, rongai, karen, ruaka, juja) }
    val styleSpanList = remember { listOf(styleSpan) }

    val polygonPoints = remember { listOf(juja, mombasaRoad, rongai, karen, ruaka, juja) }

    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    var shouldAnimateZoom by remember { mutableStateOf(true) }
//    val ticker by remember { mutableIntStateOf(0) }
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val mapVisible by remember { mutableStateOf(true) }
    val customColor = BitmapDescriptorFactory.HUE_CYAN

    if (mapVisible) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Blue)
        ){
            Box(
                modifier = Modifier.padding(
                    bottom = if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        48.dp else 48.dp
                )
            ) {
                GoogleMap(
                    modifier = modifier,
                    cameraPositionState = cameraPositionState,
                    properties = mapProperties,
                    uiSettings = uiSettings,
                    onMapLoaded = onMapLoaded,
                    onPOIClick = {
                        Log.d("E-LibraryMap", "POI clicked: ${it.name}")
                    }
                ) {
                    userLocation?.let {
                        Marker(
                            state = MarkerState(position = it),
                            title = "You are here",
                            icon = BitmapDescriptorFactory.defaultMarker(customColor)
                        )
                    }
                    // Drawing on the map is accomplished with a child-based API
                    val markerClick: (Marker) -> Boolean = {
                        Log.d("E-LibraryMap", "${it.title} was clicked")
                        cameraPositionState.projection?.let { projection ->
                            Log.d("E-LibraryMap", "The current projection is: $projection")
                        }
                        false
                    }
                    Marker(
                        state = cbdState,
                        title = "CBD, Library",
                        onClick = markerClick
                    )
                    Marker(
                        state = westlandsState,
                        title = "Westlands",
                        onClick = markerClick
                    )
                    Marker(
                        state = buruburuState,
                        title = "Buruburu",
                        onClick = markerClick
                    )
                    Marker(
                        state = kileleshwaState,
                        title = "Kileleshwa",
                        onClick = markerClick
                    )
                    Marker(
                        state = langataState,
                        title = "Lang'ata",
                        onClick = markerClick
                    )
                    Marker(
                        state = karenState,
                        title = "Karen",
                        onClick = markerClick
                    )
                    Marker(
                        state = lavingtonState,
                        title = "Lavington",
                        onClick = markerClick
                    )
                    Marker(
                        state = parklandsState,
                        title = "Parklands",
                        onClick = markerClick
                    )
                    Marker(
                        state = southBState,
                        title = "South B",
                        onClick = markerClick
                    )
                    Marker(
                        state = eastleighState,
                        title = "Eastleigh",
                        onClick = markerClick
                    )
                    Marker(
                        state = ngongRoadState,
                        title = "Ngong Road",
                        onClick = markerClick
                    )
                    Marker(
                        state = rongaiState,
                        title = "Rongai",
                        onClick = markerClick
                    )
                    Marker(
                        state = kasaraniState,
                        title = "Kasarani",
                        onClick = markerClick
                    )
                    Marker(
                        state = donholmState,
                        title = "Donholm",
                        onClick = markerClick
                    )
                    Marker(
                        state = thikaRoadState,
                        title = "Thika Road",
                        onClick = markerClick
                    )
                    Marker(
                        state = mombasaRoadState,
                        title = "Mombasa Road",
                        onClick = markerClick
                    )
                    Marker(
                        state = ruakaState,
                        title = "Ruaka",
                        onClick = markerClick
                    )
                    Marker(
                        state = jujaState,
                        title = "Juja",
                        onClick = markerClick
                    )
                    Marker(
                        state = kahawaState,
                        title = "Kahawa",
                        onClick = markerClick
                    )
                    Marker(
                        state = kiambuRoadState,
                        title = "Kiambu Road",
                        onClick = markerClick
                    )
                    Marker(
                        state = githuraiState,
                        title = "Githurai",
                        onClick = markerClick
                    )

                    Circle(
                        center = circleCenter,
                        fillColor = MaterialTheme.colorScheme.secondary,
                        strokeColor = MaterialTheme.colorScheme.primary,
                        radius = 1000.0,
                    )

                    Polyline(
                        points = polylinePoints,
                        tag = "Polyline A",
                    )

                    Polyline(
                        points = polylineSpanPoints,
                        spans = styleSpanList,
                        tag = "Polyline B",
                    )

                    Polygon(
                        points = polygonPoints,
                        fillColor = Color.Cyan.copy(alpha = 0.5f)
                    )
                    content()
                }
            }
        }
    }
    Column {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        ) {
            DashTopBar(navController)
        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                ZoomControls(
                    shouldAnimateZoom,
                    uiSettings.zoomControlsEnabled,
                    onCameraAnimationCheckedChange = {
                        shouldAnimateZoom = it
                    },
                    onZoomControlsCheckedChange = {
                        uiSettings = uiSettings.copy(zoomControlsEnabled = it)
                    }
                )
                IconButton(onClick = {
                    updateLocationAndCamera()
                },
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Cyan)
                ) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = "Recenter",
                        tint = Color.Magenta
                    )

                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.background(color = PurpleGrey40)
                ) {
                    IconButton(
                        onClick = {
                            cameraPositionState.position = defaultCameraPosition
                            cbdState.position = cbd
                            cbdState.hideInfoWindow()
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Cyan)
                    ) {
                        Icon(
                            Icons.Filled.Place,
                            contentDescription = "Recenter",
                            tint = Color.Magenta
                        )

                    }
                    Text(text = "Library Location")
                }
                MapTypeControls(onMapTypeClick = {
                    Log.d("GoogleMap", "Selected map type $it")
                    mapProperties = mapProperties.copy(mapType = it)
                })

            }
//            DebugView(cameraPositionState, singaporeState)
        }
    }
}

@Composable
private fun MapTypeControls(
    onMapTypeClick: (MapType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = { expanded = true },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black)
        ) {
            Icon(
                Icons.Filled.LocationOn,
                contentDescription = "Map Type",
                tint = Color.Green
            )
        }

        DropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            MapType.entries.forEach { mapType ->
                DropdownMenuItem(
                    text = { Text(text = mapType.name) },
                    onClick = {
                        onMapTypeClick(mapType)
                        expanded = false
                    }
                )
            }
        }
    }
}



//@Composable
//private fun MapTypeButton(type: MapType, onClick: () -> Unit) =
//    MapButton(text = type.toString(), onClick = onClick)

@Composable
private fun ZoomControls(
    isCameraAnimationChecked: Boolean,
    isZoomControlsEnabledChecked: Boolean,
    onCameraAnimationCheckedChange: (Boolean) -> Unit,
    onZoomControlsCheckedChange: (Boolean) -> Unit,
) {
    Row(horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var expanded by remember { mutableStateOf(false) }
        IconButton(
            onClick = { expanded = true },
            colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black)
        ) {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Settings",
                tint = Color.Yellow
            )
        }
        DropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            SwitchDropdownMenuItemCameraAnimations(
                text = "Camera Animations:",
                isCameraAnimationChecked = isCameraAnimationChecked,
                onCameraAnimationCheckedChange = onCameraAnimationCheckedChange
            )
            SwitchDropdownMenuItemZoomControls(
                text = "Zoom Controls:",
                isZoomControlsEnabledChecked = isZoomControlsEnabledChecked,
                onZoomControlsCheckedChange = onZoomControlsCheckedChange
            )

        }
    }
}

@Composable
fun SwitchDropdownMenuItemCameraAnimations(
    text: String,
    isCameraAnimationChecked: Boolean,
    onCameraAnimationCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onCameraAnimationCheckedChange(!isCameraAnimationChecked) }
    ) {
        Text(text = text, modifier = Modifier.weight(1f))
        Switch(
            checked = isCameraAnimationChecked,
            onCheckedChange = onCameraAnimationCheckedChange
        )
    }
}

@Composable
fun SwitchDropdownMenuItemZoomControls(
    text: String,
    isZoomControlsEnabledChecked: Boolean,
    onZoomControlsCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onZoomControlsCheckedChange(!isZoomControlsEnabledChecked) }
    ) {
        Text(text = text, modifier = Modifier.weight(1f))
        Switch(
            checked = isZoomControlsEnabledChecked,
            onCheckedChange = onZoomControlsCheckedChange
        )
    }
}
//@Composable
//private fun MapButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
//    Button(
//        modifier = modifier.padding(4.dp),
//        colors = ButtonDefaults.buttonColors(
//            containerColor = MaterialTheme.colorScheme.onPrimary,
//            contentColor = MaterialTheme.colorScheme.primary
//        ),
//        onClick = onClick
//    ) {
//        Text(text = text, style = MaterialTheme.typography.bodyMedium)
//    }
//}

//@Composable
//private fun DebugView(
//    cameraPositionState: CameraPositionState,
//    markerState: MarkerState
//) {
//    Column(
//        Modifier
//            .fillMaxWidth(),
//        verticalArrangement = Arrangement.Center
//    ) {
//        val moving =
//            if (cameraPositionState.isMoving) "moving" else "not moving"
//        Text(text = "Camera is $moving")
//        Text(text = "Camera position is ${cameraPositionState.position}")
//        Spacer(modifier = Modifier.height(4.dp))
//        val dragging =
//            if (markerState.dragState == DragState.DRAG) "dragging" else "not dragging"
//        Text(text = "Marker is $dragging")
//        Text(text = "Marker position is ${markerState.position}")
//    }
//}
@Preview
@Composable
fun GoogleMapViewPreview() {
    ELibraryTheme {
        GoogleMapView(navController = rememberNavController(),Modifier.fillMaxSize())
    }
}
