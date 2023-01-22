package app.ft.ftapp.data.repository

interface IAnnouncementRepository {
    suspend fun getAvailableAnnouncements()
}