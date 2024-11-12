import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.kmpnotifier.notification.NotifierManager
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
}
