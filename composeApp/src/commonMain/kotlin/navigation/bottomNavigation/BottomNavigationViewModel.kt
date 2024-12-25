package navigation.bottomNavigation

import androidx.lifecycle.ViewModel
import repos.config.ConfigRepository

class BottomNavigationViewModel(
    private val configRepository: ConfigRepository
): ViewModel() {
    fun getBottomNavigationItems() = configRepository.getBottomMenu() ?: arrayListOf()
    fun getTopBarBackgroundColor() = configRepository.getTopBarBackgroundColor()
    fun getTopBarTextIconColor() = configRepository.getTopBarTextIconColor()
    fun getMainNavigationBackgroundColor() = configRepository.getBottomNavigationBackgroundColor()
    fun getMainNavigationSelectedTextIconColor() = configRepository.getBottomNavigationSelectedTextIconColor()
    fun getMainNavigationUnselectedTextIconColor() = configRepository.getBottomNavigationUnselectedTextIconColor()
    fun getSideNavigationBackgroundColor() = configRepository.getSideNavigationBackgroundColor()
    fun getSideNavigationSelectedTextIconColor() = configRepository.getSideNavigationSelectedTextIconColor()
    fun getSideNavigationUnselectedTextIconColor() = configRepository.getSideNavigationUnselectedTextIconColor()
}
