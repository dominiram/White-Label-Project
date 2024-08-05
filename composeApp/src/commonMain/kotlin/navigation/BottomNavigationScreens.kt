package navigation

import org.jetbrains.compose.resources.DrawableResource
import whitelabelproject.composeapp.generated.resources.Res

sealed class BottomBarScreen(
    val route: String,
    var title: String,
    val defaultIcon: DrawableResource
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "Home",
        defaultIcon = Res.drawable.ic_home,
    )

    data object Reels : BottomBarScreen(
        route = "REELS",
        title = "Reels",
        defaultIcon = Res.drawable.ic_camera_reels_fill,
    )

    data object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "Profile",
        defaultIcon = Res.drawable.ic_profile_circle,
    )
}
