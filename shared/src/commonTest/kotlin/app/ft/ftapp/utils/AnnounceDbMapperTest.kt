package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.Announce
import db.AnnounceSQ
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Testing [AnnounceDbMapper] class.
 */
internal class AnnounceDbMapperTest {
    private val announceDbMapper = AnnounceDbMapper()

    @Test
    fun testing_mapper_from_db_model() {
        val dbAnnounce = AnnounceSQ(
            id = 2,
            chatId = 2,
            authorEmail = "authorEmail",
            placeFrom = "placeFrom",
            placeTo = "placeTo",
            startTime = "startTime",
            createTime = "createTime",
            countOfParticipants = 2,
            nowParticipants = 1,
            comment = "comment"
        )

        val announce = announceDbMapper.fromDbToModel(dbAnnounce)

        assertEquals(2, announce.id)
        assertEquals(2, announce.chatId)
        assertEquals("authorEmail", announce.authorEmail)
        assertEquals("placeFrom", announce.placeFrom)
        assertEquals("placeTo", announce.placeTo)
        assertEquals("startTime", announce.startTime)
        assertEquals("createTime", announce.createTime)
        assertEquals(2, announce.countOfParticipants)
        assertEquals(1, announce.participants?.size)
        assertEquals("comment", announce.comment)
    }

    @Test
    fun testing_mapper_to_db_model() {
        val announce = Announce(
            id = 2,
            chatId = 2,
            authorEmail = "authorEmail",
            placeFrom = "placeFrom",
            placeTo = "placeTo",
            startTime = "startTime",
            createTime = "createTime",
            countOfParticipants = 2,
            comment = "comment"
        )

        val dbAnnounce = announceDbMapper.fromModelToDb(announce)

        assertEquals(2, dbAnnounce.id)
        assertEquals(2, dbAnnounce.chatId)
        assertEquals("authorEmail", dbAnnounce.authorEmail)
        assertEquals("placeFrom", dbAnnounce.placeFrom)
        assertEquals("placeTo", dbAnnounce.placeTo)
        assertEquals("startTime", dbAnnounce.startTime)
        assertEquals("createTime", dbAnnounce.createTime)
        assertEquals(2, dbAnnounce.countOfParticipants)
        assertEquals("comment", dbAnnounce.comment)
    }
}