package app.ft.ftapp.android.presentation.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ft.ftapp.android.utils.getAddress
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.domain.models.AddressesItems
import app.ft.ftapp.domain.models.LatLng
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.ModelsState
import app.ft.ftapp.utils.YandexMapSearch
import app.ft.ftapp.utils.toLatLng
import com.yandex.mapkit.GeoObject
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Viewmodel for creation screen.
 */
class CreationScreenViewModel(val viewModel: CreationViewModel) : ViewModel() {
    private val _uiMapState = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val loadMapState: MutableStateFlow<ModelsState>
        get() = _uiMapState


    val addressText = MutableStateFlow("Определяем адрес...")

    private val listener = object : Session.SearchListener {
        override fun onSearchResponse(p0: Response) {
            val objects = mutableListOf<GeoObject>()

            p0.collection.children.forEach { item ->
                objects.add(item.obj!!)

                val city = item?.obj
                    ?.metadataContainer
                    ?.getItem(ToponymObjectMetadata::class.java)
                    ?.address


                val c2 = item?.obj
                    ?.metadataContainer
                    ?.getItem(ToponymObjectMetadata::class.java)
                    ?.formerName
                println(
                    "TAG_OF_Ya $city ${city?.formattedAddress} $c2  ]]]" +
                            "${item.obj?.name ?: "null"} .. ${item.obj?.descriptionText}.. " +
                            "${item.obj?.aref}" +
                            "${item.obj?.attributionMap} ,, ${item.obj?.geometry}"
                )
            }
            /*
            * 2 НЕнул - 2
            * 2 нул   - 5
            * \\
            *
            * 1 и 2 и 3 нулл -> 4 назв, 5 адрес
            * 3 нул, 1 и 2 НЕнул -> 4 назв, 5 адрес
            * 3 нул, 1 и 2 НЕнул ->  4 назв, 2 адрес
            * */

            //Если название, то 1 2 3 нул, а в 4 название, 5 адрес
            //Если улица, то 1 2 НЕ нул, 3 нул 4 название, 5 точный адрес
            //Если адрес, то 1 2 НЕ нул, 2 и 4 (не всегда) адрес норм, 5 просто НСК РОССИЯ
            _uiMapState.value = ModelsState.Success(objects)
        }

        override fun onSearchError(p0: Error) {
            println("TAG_OF_Ya $p0")
            _uiMapState.value = ModelsState.Error("Ошибка во время получения адресов.")
        }
    }

    val cameraListener = object : CameraListener {
        override fun onCameraPositionChanged(
            p0: Map,
            cameraPosition: CameraPosition,
            p2: CameraUpdateReason,
            finished: Boolean
        ) {
            viewModelScope.launch {
                if (finished) {
                    addressText.value =
                        DIFactory.initCtx!!.getAddress(cameraPosition.target.toLatLng())
                            ?: addressText.value
                } else {
                    addressText.value = "Определяем адрес..."
                }
            }

        }
    }

    private val _locations = MutableStateFlow(
        listOf(
            AddressesItems(
                name = "Дубки 1 корпус",
                address = "ул. Дениса Давыдова, 1",
                latLng = LatLng(55.660409, 37.228898)
            ),
            AddressesItems(
                name = "Дубки 2 корпус",
                address = "ул. Дениса Давыдова, 3",
                latLng = LatLng(55.659668, 37.228386)
            ),
            AddressesItems(
                name = "Дубки 3 корпус",
                address = "ул. Дениса Давыдова, 9",
                latLng = LatLng(55.659622, 37.226078)
            ),
            AddressesItems(
                name = "Станция Одинцово",
                address = "Привокзальная площадь, 1",
                latLng = LatLng(55.672264, 37.281410)
            ),
        )
    )

    val sourceDestCombine = viewModel.sourceDestination
        .debounce(1000L)
        .onEach {
            println("TAG_OF_RES on earch")
            if (it.isNotEmpty() && viewModel.shouldFind) {
                mapSearch.searchByQuery(it)
            }
        }
        .combine(_uiMapState) { trip, ui ->
            when (ui) {
                is ModelsState.Error, is ModelsState.Loading, is ModelsState.NoData -> {
                    _locations.value
                }
                is ModelsState.Success<*> -> {
                    val res = ui.dataResult as List<GeoObject>
                    println("TAG_OF_RES $res")
                    res.map {
                        AddressesItems(
                            name = it.name ?: "",
                            address = it.descriptionText ?: "",
                            latLng = it.geometry[0]?.point?.toLatLng() ?: LatLng()
                        )
                    }
                }
            }
        }.stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L),
            _locations.value
        )

    val endDestCombine = viewModel.endDestination
        .debounce(1000L)
        .onEach {
            println("TAG_OF_RES on earch")
            if (it.isNotEmpty() && viewModel.shouldFind) {
                mapSearch.searchByQuery(it)
            }
        }
        .combine(_uiMapState) { trip, ui ->
            when (ui) {
                is ModelsState.Error, is ModelsState.Loading, is ModelsState.NoData -> {
                    _locations.value
                }
                is ModelsState.Success<*> -> {
                    val res = ui.dataResult as List<GeoObject>
                    println("TAG_OF_RES $res")
                    res.map {
                        AddressesItems(
                            name = it.name ?: "",
                            address = it.descriptionText ?: "",
                            latLng = it.geometry[0]?.point?.toLatLng() ?: LatLng()
                        )
                    }
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), _locations.value)

    val mapSearch = YandexMapSearch(listener)

}