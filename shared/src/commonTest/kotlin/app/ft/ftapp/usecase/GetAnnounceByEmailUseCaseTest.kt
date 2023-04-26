package app.ft.ftapp.usecase

import app.ft.ftapp.data.repository.AnnouncementFakeRepository
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.server.GetAnnounceByEmailUseCase
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests usecase [GetAnnounceByEmailUseCase].
 */
class GetAnnounceByEmailUseCaseTest {
    private val fakeRepository = AnnouncementFakeRepository()
    private lateinit var getAnnounceByEmail: GetAnnounceByEmailUseCase

    @BeforeTest
    fun setUp() {
        getAnnounceByEmail = GetAnnounceByEmailUseCase(fakeRepository)
    }

    @Test
    fun `testing usecase GetAnnounceByEmailUseCase`() {
        val expected = fakeRepository.announces[0]

        val result = runBlocking {
            getAnnounceByEmail("autho1")
        }

        assertEquals(expected, (result as ServerResult.SuccessfulResult).model)
    }
}