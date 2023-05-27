package app.ft.ftapp.data.repository

import app.ft.ftapp.domain.models.*
import kotlinx.serialization.json.JsonObject

class AnnouncementFakeRepository : IAnnouncementRepository {

    val announces = mutableListOf<Announce>(
        Announce(
            id = 1,
            chatId = 1,
            authorEmail = "autho1",
            placeFrom = "placeFrom",
            placeTo = "placeTo",
            startTime = "startTime",
            createTime = "createTime",
            countOfParticipants = 2,
            comment = "comment"
        ),
        Announce(
            id = 2,
            chatId = 2,
            authorEmail = "autho2",
            placeFrom = "placeFrom",
            placeTo = "placeTo",
            startTime = "startTime",
            createTime = "createTime",
            countOfParticipants = 2,
            comment = "comment"
        ),
        Announce(
            id = 3,
            chatId = 3,
            authorEmail = "autho3",
            placeFrom = "placeFrom",
            placeTo = "placeTo",
            startTime = "startTime",
            createTime = "createTime",
            countOfParticipants = 3,
            comment = "comment"
        )
    )

    override suspend fun getAvailableAnnouncements(
        offset: Int,
        limit: Int
    ): ServerResult<PagingAnnounce> {
        return ServerResult.SuccessfulResult(PagingAnnounce(content = announces))
    }

    override suspend fun getHistoryAnnouncements(
        offset: Int,
        limit: Int,
        authorMail: String
    ): ServerResult<PagingAnnounce> {
        TODO("Not yet implemented")
    }

    override suspend fun createAnnounce(announce: Announce): ServerResult<Announce> {
        if (announces.add(announce)) {
            return ServerResult.SuccessfulResult(announce)
        }
        return ServerResult.UnsuccessfulResult(SOME_ERROR)
    }

    override suspend fun becomeTraveler(travelerUser: TravelerUser): ServerResult<Announce> {
        if (announces.size >= travelerUser.travelId) {
            var announce = announces[(travelerUser.travelId - 1).toInt()]
            val participant = Participant(username = USERNAME, email = EMAIL)
            announce = announce.copy(participants = listOf(participant))

            return ServerResult.SuccessfulResult(announce)
        }

        return ServerResult.UnsuccessfulResult(SOME_ERROR)
    }

    override suspend fun getAnnounceByEmail(email: String): ServerResult<Announce> {
        val announce = announces.filter { it.authorEmail == email }

        return if (announce.isEmpty()) {
            ServerResult.UnsuccessfulResult(SOME_ERROR)
        } else {
            ServerResult.SuccessfulResult(announce.first())
        }
    }

    override suspend fun updateAnnounce(announce: Announce): ServerResult<Announce> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAnnounce(travelId: Long): ServerResult<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getOutOfTravel(data: TravelerUser): ServerResult<Announce> {
        TODO("Not yet implemented")
    }

    override suspend fun registerUser(user: RegisterUser): ServerResult<RegisterUser> {
        TODO("Not yet implemented")
    }

    companion object {

        internal const val SOME_ERROR = "some error"
        internal const val USERNAME = "testuser"
        internal const val EMAIL = "testmail"
    }
}