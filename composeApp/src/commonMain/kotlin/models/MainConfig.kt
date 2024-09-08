package models

import kotlinx.serialization.SerialName
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.ic_home
import whitelabelproject.composeapp.generated.resources.ic_news
import whitelabelproject.composeapp.generated.resources.ic_search

data class MainConfig(
    @SerialName("app_main_config") val appMainConfiguration: String? = null,
    @SerialName("bottom_navigation_items") val bottomNavigationItems: List<BottomNavigationItem>?
) {
    fun getAppMainConfig() = when (appMainConfiguration) {
        APP_CONFIG_BOTTOM_MAIN_NAVIGATION -> AppMainNavigationConfig.BottomNavigationConfig
        else -> AppMainNavigationConfig.TopNavigationConfig
    }

    companion object {
        private const val APP_CONFIG_TOP_MAIN_NAVIGATION = "top"
        private const val APP_CONFIG_BOTTOM_MAIN_NAVIGATION = "bottom"
    }
}

sealed class AppMainNavigationConfig(val config: String) {
    data object BottomNavigationConfig : AppMainNavigationConfig("bottom")
    data object TopNavigationConfig : AppMainNavigationConfig("top")
}

data class BottomNavigationItem(
    @SerialName("icon") val icon: String?,
    @SerialName("title") val title: String?,
    @SerialName("content_type") val contentType: String,
    @SerialName("web_view_url") val webViewUrl: String? = null
) {
    fun getBottomNavigationIcon() = when (icon) {
        ICON_HOME -> Res.drawable.ic_home
        ICON_NEWS -> Res.drawable.ic_news
        ICON_SEARCH -> Res.drawable.ic_search
        else -> Res.drawable.ic_news //TODO: insert a fallback icon
    }

    fun getScreenContentType(): ContentType = when (contentType) {
        CONTENT_TYPE_WEB_VIEW -> webViewUrl?.let { ContentType.WebView(it) } ?: ContentType.Empty
        CONTENT_TYPE_MORE -> ContentType.More
        else -> ContentType.Empty //TODO: insert a fallback content type
    }

    companion object {
        private const val ICON_HOME = "home"
        private const val ICON_NEWS = "news"
        private const val ICON_SEARCH = "search"

        private const val CONTENT_TYPE_WEB_VIEW = "web_view"
        private const val CONTENT_TYPE_MORE = "more"
    }
}

sealed class ContentType(val contentType: String) {
    data class WebView(val webViewUrl: String) : ContentType("web_view")
    data object More : ContentType("more")
    data object Empty : ContentType("empty")
}
