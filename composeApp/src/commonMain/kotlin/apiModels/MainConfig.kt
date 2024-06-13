package apiModels
import kotlinx.serialization.SerialName

data class MainConfig(
    @SerialName("app_main_config") val appMainConfiguration: String? = null
) {
    fun getAppMainConfig() = when(appMainConfiguration) {
        APP_CONFIG_BOTTOM_MAIN_NAVIGATION -> AppMainConfig.BottomNavigationConfig
        else -> AppMainConfig.TopNavigationConfig
    }

    companion object {
        private const val APP_CONFIG_TOP_MAIN_NAVIGATION = "top"
        private const val APP_CONFIG_BOTTOM_MAIN_NAVIGATION = "bottom"
    }

}

sealed class AppMainConfig(val config: String) {
    data object BottomNavigationConfig: AppMainConfig("bottom")
    data object TopNavigationConfig: AppMainConfig("top")
}
