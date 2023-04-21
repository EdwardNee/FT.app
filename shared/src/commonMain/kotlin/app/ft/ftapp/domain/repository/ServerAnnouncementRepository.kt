package app.ft.ftapp.domain.repository

import app.ft.ftapp.data.converters.await
import app.ft.ftapp.data.ktor.Api
import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.PagingAnnounce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.models.TravelerUser
import kotlinx.serialization.json.JsonObject

class ServerAnnouncementRepository constructor(private val api: Api): IAnnouncementRepository {
    override suspend fun getAvailableAnnouncements(offset: Int, limit: Int): ServerResult<PagingAnnounce> {
        return api.getAnnouncements(offset, limit).await<PagingAnnounce>()
    }

    override suspend fun createAnnounce(announce: Announce): ServerResult<Announce> {
        return api.createAnnounce(announce).await()
    }

    override suspend fun updateAnnounce(announce: Announce): ServerResult<JsonObject> {
        return api.updateAnnounce(announce).await<JsonObject>()
    }

    override suspend fun deleteAnnounce(travelId: Long): ServerResult<Int> {
        return api.deleteAnnounce(travelId).await<Int>()
    }

    override suspend fun becomeTraveler(travelerUser: TravelerUser): ServerResult<Announce> {
        return api.becomeTraveler(travelerUser).await<Announce>()
    }

    override suspend fun getAnnounceByEmail(email: String): ServerResult<Announce> {
        return api.getTravelByUserEmail(email).await()
    }
}