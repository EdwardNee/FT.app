package app.ft.ftapp.usecase

import app.ft.ftapp.data.repository.AnnouncementFakeRepository
import app.ft.ftapp.domain.models.PagingAnnounce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.server.GetAnnouncementsUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests usecase [GetAnnouncementsUseCase].
 */
internal class GetAnnouncementsUseCaseTest {
    private val fakeRepository = AnnouncementFakeRepository()
    private lateinit var getAnnouncementsUseCase: GetAnnouncementsUseCase

    @BeforeTest
    fun setUp() {
        getAnnouncementsUseCase = GetAnnouncementsUseCase(fakeRepository)
    }

    @Test
    fun testing_usecase_GetAnnouncementsUseCase() {
        val expected = PagingAnnounce(content = fakeRepository.announces)

        val result = runBlocking {
            getAnnouncementsUseCase(0, 5)
        }

        assertEquals(expected, (result as ServerResult.SuccessfulResult).model)
    }
}