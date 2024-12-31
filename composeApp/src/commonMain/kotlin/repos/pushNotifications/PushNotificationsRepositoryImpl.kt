package repos.pushNotifications

import com.mmk.kmpnotifier.notification.NotifierManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PushNotificationsRepositoryImpl(
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
): PushNotificationsRepository {

    override suspend fun initPushNotificationToken() = withContext(ioCoroutineContext) {
        val firebasePushToken = NotifierManager.getPushNotifier().getToken()
        println("FirebasePushToken = $firebasePushToken")
    }
}
