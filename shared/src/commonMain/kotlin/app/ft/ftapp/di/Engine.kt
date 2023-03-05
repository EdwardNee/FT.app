package app.ft.ftapp.di

import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.repository.ServerAnnouncementRepository
import app.ft.ftapp.domain.usecase.GetAnnouncementsUseCase
import app.ft.ftapp.utils.KMMContext
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

    val kodein = DI {
        fullDescriptionOnError = true

        importAll(
            ktorModule,
            repositoryModule,
            viewModelsModule
        )
        bindConstant(tag = "base_url") { "//TDOD" }
    }

    val ktorModule = DI.Module("ktor_module") {
        bindSingleton {
            HttpClient(CIO) {
                defaultRequest {
                    host = instance<String>("base_url").replace("http://", "")
                    url {
                        protocol = URLProtocol.HTTP
                        port = 89
                    }
                }

                install(Logging) {
                    logger = Logger.DEFAULT
                    level = LogLevel.BODY
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

        bindSingleton { Api(instance()) }
    }

    val repositoryModule = DI.Module("repository_module") {
        bindSingleton<IAnnouncementRepository>("serv_ann_r") { ServerAnnouncementRepository(instance()) }
    }

    val useCaseModule = DI.Module("usecases") {
        bindSingleton { GetAnnouncementsUseCase(instance(tag = "serv_ann_r")) }
    }

    val viewModelsModule = DI.Module("viewmodel") {
//        bindSingleton<DatabaseDriverFactory>("man_mess") {  }
//        bindSingleton<ManualMeasurementViewModel>("man_mess") { ManualMeasurementViewModel() }
//        bindMultiton("details_pole") { driver:DatabaseDriverFactory -> PoleDetailsViewModel(instance(), driver) }
//        bindMultiton("wlk_lst") { args: VMArgs -> WalkListViewModel(instance(), args.driverFactory!!, args.fileManager!!) }
    }
}

/**
 * Factory to instantiate DI module.
 */
object DIFactory {
    val di = Engine().kodein

    var initCtx: KMMContext? = null

    val direct = di.direct

    inline fun <reified T : Any> resolve(tag: String? = null): T {
        return if (tag == null)
            direct.instance<T>()
        else
            return direct.instance<T>(tag)
    }
}