package app.ft.ftapp.domain.usecase.server

import app.ft.ftapp.data.repository.IAnnouncementRepository
import app.ft.ftapp.domain.models.RegisterUser
import app.ft.ftapp.domain.models.ServerResult

/**
 * UseCase that sends request to register user in the system.
 */
class RegisterUserUseCase(private val announcementRepository: IAnnouncementRepository) {
    suspend operator fun invoke(user: RegisterUser): ServerResult<RegisterUser> {
        return announcementRepository.registerUser(user)
    }
}
