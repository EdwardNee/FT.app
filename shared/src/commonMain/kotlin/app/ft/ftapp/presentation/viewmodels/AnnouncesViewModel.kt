package app.ft.ftapp.presentation.viewmodels

import app.ft.ftapp.domain.models.Announce
import app.ft.ftapp.domain.models.ServerResult
import app.ft.ftapp.domain.usecase.GetAnnouncementsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.kodein.di.instance

class AnnouncesViewModel: BaseViewModel() {
    val getAnnouncements : GetAnnouncementsUseCase by kodein.instance()

    val announcesList = getAnnounces().flowOn(Dispatchers.Unconfined)

    private fun getAnnounces(): Flow<List<Announce>> = flow {
        getAnnouncements().let { result ->
            when(result) {
                is ServerResult.SuccessfulResult -> emit(result.model)
                is ServerResult.UnsuccessfulResult -> TODO()
            }
        }
    }
}