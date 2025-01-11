package screens.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repos.pushNotifications.PushNotificationsRepository

class ArticleViewModel(
    private val pushNotificationsRepository: PushNotificationsRepository
) : ViewModel() {

    fun clearPushNotification() =
        viewModelScope.launch { pushNotificationsRepository.clearPushNotification() }
}
