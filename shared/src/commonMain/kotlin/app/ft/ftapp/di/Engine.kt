package app.ft.ftapp.di

import app.ft.ftapp.data.db.DatabaseDriverFactory
import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.ktor.TaxiApi
import app.ft.ftapp.data.repository.*
import app.ft.ftapp.domain.repository.ServerAnnouncementRepository
import app.ft.ftapp.domain.repository.ServerChatRepository
import app.ft.ftapp.domain.repository.TaxiRepository
import app.ft.ftapp.domain.repository.db.AnnounceSQRepository
import app.ft.ftapp.domain.repository.db.ParticipantSQRepository
import app.ft.ftapp.domain.usecase.chat.GetChatMessagesUseCase
import app.ft.ftapp.domain.usecase.chat.SendChatMessageUseCase
import app.ft.ftapp.domain.usecase.db.*
import app.ft.ftapp.domain.usecase.server.*
import app.ft.ftapp.domain.usecase.taxi.GetTripInfoUseCase
import app.ft.ftapp.presentation.viewmodels.CreationViewModel
import app.ft.ftapp.presentation.viewmodels.MainActivityViewModel
import app.ft.ftapp.utils.CustomJwtParser
import app.ft.ftapp.utils.KMMContext
import app.ft.ftapp.utils.OnGetUserLocation
import app.ft.ftapp.utils.PreferencesHelper
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.kodein.di.*

/**
 * Dependency injection Engine class.
 */
class Engine {
    private val ktorModule = DI.Module("ktor_module") {
        bindSingleton("server_bind") {
            HttpClient(CIO) {
                engine {
                    requestTimeout = 0 // 0 to disable, or a millisecond value to fit your needs
                }
//                install(HttpTimeout) {
//                    requestTimeoutMillis = 0
//                }

                defaultRequest {
                    host = instance<String>("base_url").replace("https://", "")
                    url {
                        protocol = URLProtocol.HTTPS
                    }
                }

                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Http Server: $message")
                        }

                    }
                    level = LogLevel.ALL
                }

                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = true
                        encodeDefaults = true
                    })
                }
            }
        }

        bindSingleton("taxi_bind") {
            HttpClient(CIO) {
                defaultRequest {
                    host = instance<String>("taxi_url").replace("https://", "")
                    url {
                        protocol = URLProtocol.HTTPS
                    }
                }

                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("HTTP Taxi API: $message")
                        }

                    }
                    level = LogLevel.ALL
                }

                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = true
                        encodeDefaults = true
                    })
                }
            }
        }

        bindSingleton { Api(instance("server_bind")) }
        bindSingleton { TaxiApi(instance("taxi_bind")) }
    }

    private val repositoryModule = DI.Module("repository_module") {
        bindSingleton<IAnnouncementRepository>("serv_ann_r") { ServerAnnouncementRepository(instance()) }
        bindSingleton<IServerChatRepository>("serv_ann_ch_r") { ServerChatRepository(instance()) }
        bindSingleton<ITaxiRepository>("taxi_ya_r") { TaxiRepository(instance()) }
        bindSingleton<IAnnounceSQRepository>("db_ann_r") { AnnounceSQRepository(DIFactory.driverFactory!!) }
        bindSingleton<IParticipantSQRepository>("db_part_r") { ParticipantSQRepository(DIFactory.driverFactory!!) }
    }

    private val useCaseModule = DI.Module("use-cases") {
        bindSingleton { GetAnnouncementsUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { GetHistoryAnnouncesUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { CreateAnnouncementUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { BecomeTravelerUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { GetAnnounceByEmailUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { DeleteAnnounceUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { GetOutOfTravelUseCase(instance(tag = "serv_ann_r")) }
        bindSingleton { UpdateAnnounceUseCase(instance(tag = "serv_ann_r")) }

        bindSingleton { GetTripInfoUseCase(instance(tag = "taxi_ya_r")) }

        bindSingleton { InsertAnnounceToDbUseCase(instance(tag = "db_ann_r")) }
        bindSingleton { GetAnnounceFromDbUseCase(instance(tag = "db_ann_r")) }
        bindSingleton { GetAllAnnouncesFromDb(instance(tag = "db_ann_r")) }

        bindSingleton { InsertParticipantToDbUseCase(instance(tag = "db_part_r")) }
        bindSingleton { GetParticipantsByAnnounceIdUseCase(instance(tag = "db_part_r")) }

        bindSingleton { GetChatMessagesUseCase(instance(tag = "serv_ann_ch_r")) }
        bindSingleton { SendChatMessageUseCase(instance(tag = "serv_ann_ch_r")) }

    }

    private val viewModelsModule = DI.Module("viewmodel") {
//        bindSingleton<BaseViewModel>("announce_vm") { AnnouncesViewModel() }
        bindSingleton("announce_cr") { CreationViewModel() }
        bindSingleton("mainact_vm") { MainActivityViewModel() }
    }

    private val utilsModule = DI.Module("utils") {
        bindSingleton { PreferencesHelper(DIFactory.initCtx!!) }
        bindSingleton { CustomJwtParser() }
    }

    val kodein = DI {
        fullDescriptionOnError = true

        importAll(
            ktorModule, useCaseModule, repositoryModule, viewModelsModule, utilsModule
        )
        bindConstant(tag = "base_url") { "https://ftapp-aapetropavlovskiy.b4a.run" }
        bindConstant(tag = "taxi_url") { "https://taxi-routeinfo.taxi.yandex.net" }
    }
}

/**
 * Factory to instantiate DI module.
 */
object DIFactory {
    val di = Engine().kodein

    var initCtx: KMMContext? = null
    var driverFactory: DatabaseDriverFactory? = null
    var locationListener: OnGetUserLocation? = null

    val direct = di.direct

    inline fun <reified T : Any> resolve(tag: String? = null): T {
        return if (tag == null) direct.instance<T>()
        else return direct.instance<T>(tag)
    }
}