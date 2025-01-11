package repos.pushNotifications

import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.flow.Flow

interface PushNotificationsRepository {
    suspend fun initPushNotificationToken()

    fun getLastPushNotificationUrl(): Flow<String?>

    suspend fun storePushNotification(pushNotificationPayload: PayloadData)

    suspend fun clearPushNotification()
}
