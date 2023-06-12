package app.ft.ftapp.domain.repository.db

import app.ft.ftapp.data.db.DatabaseDriverFactory
import app.ft.ftapp.data.repository.IAnnounceSQRepository
import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.utils.AnnounceDbMapper
import db.FTAppDatabase

class AnnounceSQRepository(databaseDriverFactory: DatabaseDriverFactory) : IAnnounceSQRepository {
    private val database = FTAppDatabase(databaseDriverFactory.createDriver())
    private val query = database.announceSQQueries
    private val announceDbMapper = AnnounceDbMapper()

    override suspend fun insertAnnounceToDb(announce: Announce) {
        with(announce) {
            query.insertAnnounce(
                id = id,
                chatId = chatId ?: 0,
                authorEmail = authorEmail ?: "",
                placeFrom = placeFrom,
                placeTo = placeTo,
                startTime = startTime ?: "",
                createTime = createTime ?: "",
                countOfParticipants = countOfParticipants,
                nowParticipants = participants?.size ?: 0,
                price = price ?: 0,
                comment = comment,
            )
        }
    }

    override suspend fun getAllAnnouncesFromDb(): List<Announce> {
        val listOfSq = query.getAllAnnounces().executeAsList()

        return listOfSq.map {
            announceDbMapper.fromDbToModel(it)
        }
    }

    override suspend fun getAnnounceByIdFromDb(id: Int): Announce {
        val result = query.getAnnounceById(id).executeAsOne()
        println("tag_of_part $result")
        return announceDbMapper.fromDbToModel(result)
    }

    override suspend fun deleteAnnounceById(id: Int) {
        query.deleteAnnounceById(id)
    }

    override suspend fun deleteAllAnnouncesFromDb() {
        query.getAllAnnounces()
    }
}