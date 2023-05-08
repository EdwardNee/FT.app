package app.ft.ftapp.android.presentation.creation

import android.Manifest
import android.content.res.Resources
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import app.ft.ftapp.android.R
import app.ft.ftapp.android.presentation.creation.components.FromToComposable
import app.ft.ftapp.android.presentation.viewmodels.factory.ArgsViewModelFactory
import app.ft.ftapp.android.presentation.viewmodels.factory.FactoryArgs
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.navigation.AppDestination
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.appBackground
import app.ft.ftapp.android.ui.theme.blueCircle
import app.ft.ftapp.android.utils.SingletonHelper
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.MapBoundingBoxes.MOSCOW_CAMERA
import app.ft.ftapp.presentation.viewmodels.CreationEvent
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.FocusPosition
import app.ft.ftapp.presentation.viewmodels.MainActivityViewModel
import app.ft.ftapp.utils.toLatLng
import app.ft.ftapp.utils.toPoint
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.kodein.di.instance


val Int.toDp get() = (this / Resources.getSystem().displayMetrics.density).toInt()


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreationWithMap() {
    val kodein = DIFactory.di
    val mainViewModel: MainActivityViewModel by kodein.instance(tag = "mainact_vm")
    val viewModel: CreationViewModel by kodein.instance(tag = "announce_cr")
//    val viewModel = setupViewModel<CreationViewModel>()
    val viewModelScreen: CreationScreenViewModel = setupViewModel<CreationScreenViewModel>(
        ArgsViewModelFactory(FactoryArgs(viewModel))
    )

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, do something
            DIFactory.locationListener?.getPermissionForLocation()
        } else {
            // Permission denied, show error message
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    val editTextTaps by viewModel.editTextTap.collectAsState()

    val mapView = remember { mutableStateOf<MapView?>(null) }

    BackHandler(enabled = true) {
        println("TAG_OF_BACK")
        scope.launch {
            scaffoldState.bottomSheetState.collapse()
        }
    }

    scope.launch {
        mainViewModel.userLocation.onEach {
            mapView.value?.map?.move(
                CameraPosition(it.toPoint(), 17.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 1F),
                null
            )
        }.collect()
    }

    println("TAG_FOR_FOCUS craw $editTextTaps")

    var size = remember { mutableStateOf(IntSize.Zero) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {

            OutlinedButton(
                onClick = {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(50.dp)
                    .align(Alignment.End)
                    .background(Color.Transparent),
                shape = CircleShape,
                border = BorderStroke(0.dp, Color.Transparent),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = blueCircle,
                    backgroundColor = Color.Transparent
                ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.my_location),
                    contentDescription = "",
                    Modifier.size(50.dp),
                    tint = blueCircle
                )
            }

            Card(
                Modifier
                    .padding(4.dp)
                    .height(6.dp)
                    .width(40.dp)
                    .align(Alignment.CenterHorizontally),
                backgroundColor = Color.Gray.copy(alpha = 0.6f)
            ) {}
            BottomSheetCreate(size, viewModelScreen) {

                mapView.value?.map?.move(
                    CameraPosition(it, 16.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1F),
                    null
                )
            }
        },
        sheetPeekHeight = (size.value.height.toDp + 30 + 58).dp,
//        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp
    ) {
        it
        MapBody(mapView, viewModelScreen)
//
//        Body(viewModelScreen, siteListItems, viewModelKmm, cameraPositionState) { id ->
        scope.launch {
            if (editTextTaps == FocusPosition.None) {
                scaffoldState.bottomSheetState.collapse()
            } else {
                scaffoldState.bottomSheetState.expand()
            }
        }
//            viewModelScreen.onEvent(WalkListEvent.NavigateToDetails(id.toString()))
//        }
    }
}

@Composable
fun MapBody(mv: MutableState<MapView?>, viewModelScreen: CreationScreenViewModel) {
    val addressText by viewModelScreen.addressText.collectAsState()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, do something
            DIFactory.locationListener?.getPermissionForLocation()
        } else {
            // Permission denied, show error message
        }
    }


    mv.value?.map?.logo?.setAlignment(
        com.yandex.mapkit.logo.Alignment(
            HorizontalAlignment.RIGHT,
            VerticalAlignment.TOP
        )
    )

    mv.value?.map?.addCameraListener(viewModelScreen.cameraListener)

//    mapView.map.

    Box() {
//        AndroidView(factory = { mapView })
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                MapView(context).apply {
                    MapKitFactory.initialize(context)
                    mv.value = this
                    mv.value?.map?.move(
                        CameraPosition(MOSCOW_CAMERA.toPoint(), 17.0f, 0.0f, 0.0f),
                        Animation(Animation.Type.SMOOTH, 0F),
                        null
                    )
                }
            }
        )
        Text(
            text = addressText,
            Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Image(
            modifier = Modifier
                .size(72.dp)
                .align(Alignment.Center)
                .padding(bottom = 40.dp),
            painter = painterResource(id = R.drawable.map_pin),
            contentDescription = "pin"
        )
    }
}

@Composable
fun BottomSheetCreate(
    size: MutableState<IntSize>,
    viewModel: CreationScreenViewModel,
    onAnimateTo: (Point) -> Unit
) {

    val viewModelScreen: CreationScreenViewModel = setupViewModel<CreationScreenViewModel>(
        ArgsViewModelFactory(FactoryArgs(viewModel))
    )

    val source by viewModel.viewModel.sourceDestination.collectAsState()
    val endDest by viewModel.viewModel.endDestination.collectAsState()
    val locationsSource by viewModelScreen.sourceDestCombine.collectAsState()
    val locationsEnd by viewModelScreen.endDestCombine.collectAsState()
    val editTextTap by viewModel.viewModel.editTextTap.collectAsState()


    val listener = remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        listState.interactionSource.interactions.collect {
            //at the top of the list so allow sheet scrolling
            listener.value = (listState.firstVisibleItemScrollOffset == 0)
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                return super.onPreScroll(available, source)
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {

                if (available.y > 0.0 && consumed.y == 0.0f) {
                    //scolling down up but we're already at the top - kick over to sheet scrolling
                    listener.value = true
//                    listener.allowSheetDrag(true)
                }
                return super.onPostScroll(consumed, available, source)
            }
        }
    }

    Column(
        Modifier
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .background(appBackground)
            .padding(horizontal = 4.dp)
            .padding(top = 4.dp)
    ) {
        FromToComposable(source, endDest, viewModelScreen, size)

        Box(
            Modifier
                .nestedScroll(nestedScrollConnection)
                .fillMaxHeight()
                .padding(top = 3.dp),
            contentAlignment = if (locationsSource.isNotEmpty() || locationsEnd.isNotEmpty()) Alignment.TopCenter else Alignment.CenterEnd
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
//                                    .background(editTextBackground)
            ) {
                if (locationsSource.isNotEmpty()) {
                    items(if (editTextTap == FocusPosition.SourceField) locationsSource else locationsEnd) {

//                    locations.forEach {
                        SearchLocationItem(it.name, it.address) {
                            viewModel.viewModel.onEvent(
                                CreationEvent.Action.OnAddressClicked(
                                    it.address, it.latLng
                                )
                            )

                            viewModelScreen.shouldFind = false
                            onAnimateTo(it.latLng.toPoint())
                        }

                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                } else {
                    item {
                        Text(
                            text = "Адреса не найдены",
                            fontSize = 22.sp,
                            fontFamily = Montserrat,
//                            modifier = Modifier.align(Alignment.CenterStart),
                            color = Color.Gray
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    SingletonHelper.appNavigator.tryNavigateTo(
                        AppDestination.Creation(),
//                        inclusive = false,
//                        isSingleTop = false,
                        popUpToRoute = AppDestination.CreationMap()
                    )
                },
                modifier = Modifier
                    .size(55.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(blueCircle)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .scale(scaleX = -1f, scaleY = 1f)
                )
            }
        }
    }
}

//}