package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.converters.await
import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.PagingAnnounce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser
import io.ktor.client.plugins.*
import io.ktor.util.network.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException

class ServerAnnouncementRepository constructor(private val api: Api) : IAnnouncementRepository {

    override suspend fun getAvailableAnnouncements(
        offset: Int,
        limit: Int
    ): ServerResult<PagingAnnounce> {
        var result: ServerResult<PagingAnnounce>
        try {
            val response = api.getAnnouncements(offset, limit)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }

        return result
    }

    override suspend fun getHistoryAnnouncements(
        offset: Int,
        limit: Int,
        authorMail: String
    ): ServerResult<PagingAnnounce> {
        var result: ServerResult<PagingAnnounce>
        try {
            val response = api.getTravelHistory(offset, limit, authorMail)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }

        return result
    }

    override suspend fun createAnnounce(announce: Announce): ServerResult<Announce> {
        var result: ServerResult<Announce>
        try {
            val response = api.createAnnounce(announce)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }

        return result
    }

    override suspend fun updateAnnounce(announce: Announce): ServerResult<Announce> {
        var result: ServerResult<Announce>
        try {
            val response = api.updateAnnounce(announce)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }

        return result
    }

    override suspend fun deleteAnnounce(travelId: Long): ServerResult<Int> {
        var result: ServerResult<Int>
        try {
            val response = api.deleteAnnounce(travelId)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }

        return result
    }

    override suspend fun becomeTraveler(travelerUser: TravelerUser): ServerResult<Announce> {
        var result: ServerResult<Announce>
        try {
            val response = api.becomeTraveler(travelerUser)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }
        return result
    }

    override suspend fun getAnnounceByEmail(email: String): ServerResult<Announce> {
        var result: ServerResult<Announce>
        try {
            val response = api.getTravelByUserEmail(email)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }
        return result
    }

    override suspend fun getOutOfTravel(data: TravelerUser): ServerResult<Announce> {
        var result: ServerResult<Announce>
        try {
            val response = api.getOutOfTravel(data)
            result = response.await()
        } catch (ex: Exception) {
            when (ex) {
                is UnresolvedAddressException -> {
                    result = ServerResult.ResultException("Ошибка подключения к сети.", ex)
                }
                is ClientRequestException,
                is ServerResponseException,
                is IOException,
                is SerializationException -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
                else -> {
                    result = ServerResult.ResultException(ex.message, ex)
                }
            }
        }
        return result
    }
}