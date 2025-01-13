package navigation.bottomNavigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import repos.config.ConfigRepository
import repos.pushNotifications.PushNotificationsRepository

class BottomNavigationViewModel(
    private val configRepository: ConfigRepository,
    private val pushNotificationsRepository: PushNotificationsRepository
) : ViewModel() {
    private val _pushNotification: MutableStateFlow<String?> = MutableStateFlow(null)
    val pushNotification =
        _pushNotification.asStateFlow().distinctUntilChanged { old, new -> old != new }

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

    private fun collectLatestPushNotificationData() = viewModelScope.launch {
        pushNotificationsRepository.getLastPushNotificationUrl()?.collectLatest {
            _pushNotification.value = it
        }
    }
}
