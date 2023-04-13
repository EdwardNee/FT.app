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
                author = author,
                email = email,
                placeFrom = placeFrom,
                placeTo = placeTo,
                countOfParticipants = countOfParticipants,
                COMMENT = comment,
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
        return announceDbMapper.fromDbToModel(query.getAnnounceById(id).executeAsOne())
    }

    override suspend fun deleteAnnounceById(id: Int) {
        query.deleteAnnounceById(id)
    }

    override suspend fun deleteAllAnnouncesFromDb() {
        query.getAllAnnounces()
    }
}