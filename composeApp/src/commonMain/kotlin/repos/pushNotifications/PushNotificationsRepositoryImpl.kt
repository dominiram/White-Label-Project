package repos.pushNotifications

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import repos.dataStore.PushNotificationsDataStore
import kotlin.coroutines.CoroutineContext

class PushNotificationsRepositoryImpl(
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) : PushNotificationsRepository {
    private var dataStore: PushNotificationsDataStore? = null
    private val lastPushNotification = MutableStateFlow<PayloadData?>(null)
    private val lastArticlePushNotification = MutableStateFlow<String?>(null)

    override fun initDataStore(dataStore: DataStore<Preferences>) {
        this.dataStore = PushNotificationsDataStore(dataStore)
    }

    override suspend fun initPushNotificationToken() = withContext(ioCoroutineContext) {
        val firebasePushToken = NotifierManager.getPushNotifier().getToken()
        println("FirebasePushToken = $firebasePushToken")
    }

    override fun getLastPushNotificationUrl() =
//        lastArticlePushNotification
        dataStore?.getPushNotificationArticleUrl() ?: lastArticlePushNotification

    override suspend fun storePushNotification(pushNotificationPayload: PayloadData) {
        val articleUrl = (pushNotificationPayload[PUSH_NOTIFICATION_URL_KEY] as? String)
        dataStore?.storePushNotificationArticleUrl(articleUrl)
        lastPushNotification.emit(pushNotificationPayload)
        articleUrl?.let { lastArticlePushNotification.emit(it) }
    }

    override suspend fun clearPushNotification() = lastArticlePushNotification.emit(null)

    companion object {
        private const val PUSH_NOTIFICATION_URL_KEY = "url"
    }
}
