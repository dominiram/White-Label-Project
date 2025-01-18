package repos.pushNotifications

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import repos.dataStore.PushNotificationsDataStore
import kotlin.coroutines.CoroutineContext

class PushNotificationsRepositoryImpl(
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) : PushNotificationsRepository {
    private var dataStore: PushNotificationsDataStore? = null

    override fun initDataStore(dataStore: DataStore<Preferences>, onInitialized: () -> Unit) {
        this.dataStore = PushNotificationsDataStore(dataStore)
        onInitialized()
    }

    override suspend fun initPushNotificationToken() = withContext(ioCoroutineContext) {
        val firebasePushToken = NotifierManager.getPushNotifier().getToken()
        println("FirebasePushToken = $firebasePushToken")
    }

    override fun getLastPushNotificationUrl() = dataStore?.getPushNotificationArticleUrl()

    override suspend fun storePushNotification(pushNotificationPayload: PayloadData) {
        val articleUrl = (pushNotificationPayload[PUSH_NOTIFICATION_URL_KEY] as? String)
        dataStore?.storePushNotificationArticleUrl(articleUrl)
    }

    override suspend fun clearPushNotification() {
        dataStore?.removePushNotificationArticleUrl()
    }

    companion object {
        private const val PUSH_NOTIFICATION_URL_KEY = "url"
    }
}
