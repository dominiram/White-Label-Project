package repos.pushNotifications

import com.mmk.kmpnotifier.notification.NotifierManager

class PushNotificationsRepositoryImpl: PushNotificationsRepository {

    override suspend fun initPushNotificationToken() {
        val firebasePushToken = NotifierManager.getPushNotifier().getToken()
        println("FirebasePushToken = $firebasePushToken")
    }
}
