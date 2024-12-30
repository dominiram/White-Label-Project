package repos.config

import models.MainConfig
import networking.ApiService

class ConfigRepositoryImpl(private val apiService: ApiService) : ConfigRepository {

    private var config: MainConfig? = null

    override suspend fun getAppConfig() = apiService.getAppConfig()

    override fun saveApplicationConfig(config: MainConfig) {
        this.config = config
    }

    override fun getBottomMenu() = config?.languageList?.find { it.isDefault }?.bottomMenu

    override fun getTopBarBackgroundColor() =
        config?.options?.topBarBackgroundColor ?: DEFAULT_BACKGROUND_COLOR

    override fun getTopBarTextIconColor(): String =
        config?.options?.topBarTextIconColor ?: DEFAULT_TEXT_ICON_COLOR

    override fun getBottomNavigationBackgroundColor() =
        config?.options?.mainNavigationBackgroundColor ?: DEFAULT_BACKGROUND_COLOR

    override fun getBottomNavigationSelectedTextIconColor(): String =
        config?.options?.mainNavigationSelectedTextIconColor ?: DEFAULT_TEXT_ICON_COLOR

    override fun getBottomNavigationUnselectedTextIconColor(): String =
        config?.options?.mainNavigationUnselectedTextIconColor ?: DEFAULT_TEXT_ICON_COLOR

    override fun getSideNavigationBackgroundColor(): String =
        config?.options?.sideBarBackgroundColor ?: DEFAULT_BACKGROUND_COLOR

    override fun getSideNavigationSelectedTextIconColor(): String =
        config?.options?.sideBarSelectedBarTextIconColor ?: DEFAULT_BACKGROUND_COLOR

    override fun getSideNavigationUnselectedTextIconColor(): String =
        config?.options?.sideBarUnselectedBarTextIconColor ?: DEFAULT_BACKGROUND_COLOR

    override fun getLogoUrl(): String = config?.options?.logo ?: ""

    companion object {
        private const val DEFAULT_BACKGROUND_COLOR = "#183354"
        private const val DEFAULT_TEXT_ICON_COLOR = "#FFF"
    }
}
