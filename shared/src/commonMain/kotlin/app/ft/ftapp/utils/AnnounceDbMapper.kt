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
                timeRemained = 0L,
                author = author,
                email = email,
                placeFrom = placeFrom,
                placeTo = placeTo,
//                participants = participants,
                countOfParticipants = countOfParticipants,
                comment = COMMENT ?: "",
            )
        }
    }

    /**
     * Maps [Announce] to [AnnounceSQ]
     */
    fun fromModelToDb(announce: Announce) {
        return with(announce) {
            AnnounceSQ(
                id = id,
                author = author,
                email = email ?: "",
                placeFrom = placeFrom,
                placeTo = placeTo,
//                participants = participants,
                countOfParticipants = countOfParticipants,
                COMMENT = comment,
            )
        }
    }
}