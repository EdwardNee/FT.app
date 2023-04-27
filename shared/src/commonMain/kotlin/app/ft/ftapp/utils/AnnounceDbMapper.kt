package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.Participant
import db.AnnounceSQ

/**
 * Announce data classes mapper between db and model classes.
 */
class AnnounceDbMapper {
    /**
     * Maps [AnnounceSQ] to [Announce]
     */
    fun fromDbToModel(announceSQ: AnnounceSQ): Announce {
        println("TAG_OF_PART $announceSQ , ${MutableList(announceSQ.nowParticipants ?: 0) { Participant() }.size}")
        return with(announceSQ) {
            Announce(
                id = id,
                chatId = chatId,
                timeRemained = 0L,
                authorEmail = authorEmail,
                placeFrom = placeFrom,
                placeTo = placeTo,
                startTime = startTime,
                createTime = createTime,
                participants = MutableList(nowParticipants ?: 0) { Participant() },
                countOfParticipants = countOfParticipants,
                comment = comment ?: "",
            )
        }
    }

    /**
     * Maps [Announce] to [AnnounceSQ]
     */
    fun fromModelToDb(announce: Announce): AnnounceSQ {
        println("TAG_OF_PART $announce")
        return with(announce) {
            AnnounceSQ(
                id = id,
                chatId = chatId ?: 0,
                authorEmail = authorEmail ?: "",
                placeFrom = placeFrom,
                placeTo = placeTo,
                startTime = startTime,
                createTime = createTime,
                nowParticipants = participants?.size ?: 0,
//                participants = participants,
                countOfParticipants = countOfParticipants,
                comment = comment,
            )
        }
    }
}