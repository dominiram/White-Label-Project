package org.nsquare.whitelabelproject

import android.app.Application
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_notification,
                notificationIconColorResId = R.color.ic_notification_background,
                showPushNotification = true,
            )
        )

        NotifierManager.getPermissionUtil().askNotificationPermission()
    }
}
