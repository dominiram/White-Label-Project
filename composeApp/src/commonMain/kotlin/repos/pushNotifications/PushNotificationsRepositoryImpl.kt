package repos.pushNotifications

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PushNotificationsRepositoryImpl(
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) : PushNotificationsRepository {
    private val lastPushNotification = MutableStateFlow<PayloadData?>(null)

    override suspend fun initPushNotificationToken() = withContext(ioCoroutineContext) {
        val firebasePushToken = NotifierManager.getPushNotifier().getToken()
        println("FirebasePushToken = $firebasePushToken")
    }

    override fun getLastPushNotification() = lastPushNotification

    override suspend fun storePushNotification(pushNotificationPayload: PayloadData) =
        lastPushNotification.emit(pushNotificationPayload)

    override fun clearPushNotification() = lastPushNotification.update {
        it?.toMutableMap()?.apply {
            clear()
        }
    }
}
