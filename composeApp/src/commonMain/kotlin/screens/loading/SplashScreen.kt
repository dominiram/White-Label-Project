package screens.loading

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import utils.ConfigNameConstants.CONFIG_NAME_RTV_PANCEVO
import utils.ConfigNameConstants.CONFIG_NAME_TV_DUNAV
import whitelabelproject.buildKonfig.BuildKonfig
import whitelabelproject.composeapp.generated.resources.Res
import whitelabelproject.composeapp.generated.resources.ic_logo_rtv_pancevo
import whitelabelproject.composeapp.generated.resources.ic_logo_tv_dunav
import whitelabelproject.composeapp.generated.resources.ic_n2_apps_logo

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(vertical = 24.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.fillMaxWidth().weight(5f),
            painter = painterResource(getLogoForFlavour()),
            contentDescription = null
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.width(80.dp).height(80.dp),
                painter = painterResource(Res.drawable.ic_n2_apps_logo),
                contentDescription = null
            )

            Text(
                text = "Powered by N2 Apps",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(400)
                )
            )

            Text(
                text = "nsquaredapps.com",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(400)
                )
            )
        }
    }
}

fun getLogoForFlavour(): DrawableResource = when (BuildKonfig.CONFIG_NAME) {
    CONFIG_NAME_RTV_PANCEVO -> Res.drawable.ic_logo_rtv_pancevo
    CONFIG_NAME_TV_DUNAV -> Res.drawable.ic_logo_tv_dunav
    else -> Res.drawable.ic_logo_rtv_pancevo
}
