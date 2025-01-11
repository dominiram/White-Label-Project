import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.launch
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
        pushNotificationsRepository.storePushNotification(pushNotificationPayload)
    }
}
