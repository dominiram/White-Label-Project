package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.ic_home
import whitelabelproject.composeapp.generated.resources.ic_news
import whitelabelproject.composeapp.generated.resources.ic_search

@Serializable
data class MainConfigWrapper(
    @SerialName("result") val result: MainConfig,
)

@Serializable
data class MainConfig(
    @SerialName("languages") val languageList: List<LanguageResponse>? = arrayListOf(),
    @SerialName("options") val options: MainConfigOptions? = null,

    )

@Serializable
data class LanguageResponse(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("code") val code: String? = null,
    @SerialName("default") val default: Int? = null,
    @SerialName("top_menu") val topMenu: List<MainNavigationItem>? = null,
    @SerialName("bottom_menu") val bottomMenu: List<MainNavigationItem>? = null,
) {
    val isDefault = default == 1
}

@Serializable
data class MainConfigOptions(
    @SerialName("multi_language") val multiLanguage: Boolean? = null,
    @SerialName("logo") val logo: String? = null,
    @SerialName("top_bar_background") val topBarBackgroundColor: String? = null,
    @SerialName("top_bar_text_icon_color") val topBarTextIconColor: String? = null,
    @SerialName("main_navigation_background") val mainNavigationBackgroundColor: String? = null,
    @SerialName("icons_text_color") val mainNavigationUnselectedTextIconColor: String? = null,
    @SerialName("selected_icons_text_color") val mainNavigationSelectedTextIconColor: String? = null,
    @SerialName("sidebar_background") val sideBarBackgroundColor: String? = null,
    @SerialName("icons_text_color_sidebar") val sideBarUnselectedBarTextIconColor: String? = null,
    @SerialName("selected_icons_text_color_sidebar") val sideBarSelectedBarTextIconColor: String? = null,
)

sealed class AppMainNavigationConfig(val config: String) {
    data object BottomNavigationConfig : AppMainNavigationConfig("bottom")
    data object TopNavigationConfig : AppMainNavigationConfig("top")
}

@Serializable
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
