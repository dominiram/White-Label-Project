package navigation.bottomNavigation

import androidx.lifecycle.ViewModel
import repos.config.ConfigRepository

class BottomNavigationViewModel(
    private val configRepository: ConfigRepository
): ViewModel() {
    fun getBottomNavigationItems() = configRepository.getBottomMenu() ?: arrayListOf()
}
