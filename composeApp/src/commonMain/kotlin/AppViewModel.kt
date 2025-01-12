import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging
import repos.pushNotifications.PushNotificationsRepository

class AppViewModel(
    private val pushNotificationsRepository: PushNotificationsRepository
) : ViewModel() {

    fun initPushNotificationToken() = viewModelScope.launch {
        pushNotificationsRepository.initPushNotificationToken()
    }

    fun subscribeToNewsChannel() = viewModelScope.launch {
        NotifierManager.getPushNotifier().subscribeToTopic("android-news")
    }

    fun storePushNotification(pushNotificationPayload: PayloadData) = viewModelScope.launch {
        logging("AppViewModel").d { "storePushNotification" }
        pushNotificationsRepository.storePushNotification(pushNotificationPayload)
    }

    fun initDataStore(preferences: DataStore<Preferences>) {
        TODO("Not yet implemented")
    }
}
