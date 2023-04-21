package app.ft.ftapp.usecase

import app.ft.ftapp.data.repository.AnnouncementFakeRepository
import app.ft.ftapp.domain.usecase.CreateAnnouncementUseCase
import kotlin.test.BeforeTest

class CreateAnnouncementUseCaseTest {
    private val fakeRepository = AnnouncementFakeRepository()
    private lateinit var createAnnouncementUseCase: CreateAnnouncementUseCase

    @BeforeTest
    fun setUp() {
        createAnnouncementUseCase = CreateAnnouncementUseCase(fakeRepository)
    }
}