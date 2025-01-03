package navigation.bottomNavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.kmpnotifier.notification.PayloadData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repos.config.ConfigRepository
import repos.pushNotifications.PushNotificationsRepository

class BottomNavigationViewModel(
    private val configRepository: ConfigRepository,
    private val pushNotificationsRepository: PushNotificationsRepository
) : ViewModel() {
    private val _pushNotification: MutableStateFlow<String?> = MutableStateFlow(null)
    val pushNotification = _pushNotification.asStateFlow()

    init {
        collectLatestPushNotificationData()
    }

    fun getBottomNavigationItems() = configRepository.getBottomMenu() ?: arrayListOf()
    fun getTopBarBackgroundColor() = configRepository.getTopBarBackgroundColor()
    fun getTopBarTextIconColor() = configRepository.getTopBarTextIconColor()
    fun getMainNavigationBackgroundColor() = configRepository.getBottomNavigationBackgroundColor()
    fun getMainNavigationSelectedTextIconColor() =
        configRepository.getBottomNavigationSelectedTextIconColor()

    fun getMainNavigationUnselectedTextIconColor() =
        configRepository.getBottomNavigationUnselectedTextIconColor()

    fun getSideNavigationBackgroundColor() = configRepository.getSideNavigationBackgroundColor()
    fun getSideNavigationSelectedTextIconColor() =
        configRepository.getSideNavigationSelectedTextIconColor()

    fun getSideNavigationUnselectedTextIconColor() =
        configRepository.getSideNavigationUnselectedTextIconColor()

    fun getLogoUrl() = configRepository.getLogoUrl()

    fun storePushNotification(pushNotificationPayload: PayloadData) = viewModelScope.launch {
        pushNotificationsRepository.storePushNotification(pushNotificationPayload)
    }

    fun clearPushNotification() = pushNotificationsRepository.clearPushNotification()

    private fun collectLatestPushNotificationData() = viewModelScope.launch {
        pushNotificationsRepository.getLastPushNotificationUrl().collectLatest {
            _pushNotification.value = it
//            _pushNotification.update { pushNotificationData ->
//                pushNotificationData?.apply {
//                    entries.toMutableSet().let { set ->
//                        it?.entries?.takeIf { it.isNotEmpty() }
//                            ?.let { entries -> set.addAll(entries) }
//                    }
//                }
//            }
        }
    }
}
