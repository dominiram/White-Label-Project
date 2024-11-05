package repos.pushNotifications

interface PushNotificationsRepository {
    suspend fun initPushNotificationToken()
}
