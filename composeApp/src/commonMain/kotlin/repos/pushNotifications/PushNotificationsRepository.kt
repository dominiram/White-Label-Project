package repos.pushNotifications

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.flow.Flow

interface PushNotificationsRepository {
    fun initDataStore(dataStore: DataStore<Preferences>)

    suspend fun initPushNotificationToken()

    fun getLastPushNotificationUrl(): Flow<String?>?

    suspend fun storePushNotification(pushNotificationPayload: PayloadData)

    suspend fun clearPushNotification()
}
