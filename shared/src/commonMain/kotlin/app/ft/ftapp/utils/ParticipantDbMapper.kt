package app.ft.ftapp.utils

import app.ft.ftapp.domain.models.Participant
import db.ParticipantSQ

/**
 * Participant data classes mapper between db and model classes.
 */
class ParticipantDbMapper {
    /**
     * Converts [ParticipantSQ] to [Participant]
     */
    fun fromDbToModel(participant: ParticipantSQ): Participant {
        return Participant(
            username = participant.name ?: "",
            email = participant.email ?: "",
            id = participant.announce_id.toLong()
        )
    }
}