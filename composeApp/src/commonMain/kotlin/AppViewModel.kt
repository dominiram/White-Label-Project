import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repos.pushNotifications.PushNotificationsRepository

class AppViewModel(
    private val pushNotificationsRepository: PushNotificationsRepository
) : ViewModel() {

    fun initPushNotificationToken() = viewModelScope.launch {
        pushNotificationsRepository.initPushNotificationToken()
    }
}
