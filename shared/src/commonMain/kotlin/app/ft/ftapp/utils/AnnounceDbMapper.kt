package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.Announce
import db.AnnounceSQ

/**
 * Announce data classes mapper between db and model classes.
 */
class AnnounceDbMapper {
    /**
     * Maps [AnnounceSQ] to [Announce]
     */
    fun fromDbToModel(announceSQ: AnnounceSQ): Announce {
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
//                participants = participants,
                countOfParticipants = countOfParticipants,
                comment = comment ?: "",
            )
        }
    }

    /**
     * Maps [Announce] to [AnnounceSQ]
     */
    fun fromModelToDb(announce: Announce): AnnounceSQ {
        return with(announce) {
            AnnounceSQ(
                id = id,
                chatId = chatId ?: 0,
                authorEmail = authorEmail ?: "",
                placeFrom = placeFrom,
                placeTo = placeTo,
                startTime = startTime,
                createTime = createTime,
//                participants = participants,
                countOfParticipants = countOfParticipants,
                comment = comment,
            )
        }
    }
}