package pushNotifications

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData

fun initPushNotifications() {
    NotifierManager.addListener(object : NotifierManager.Listener {
        override fun onNewToken(token: String) {
            println("onNewToken: $token")
        //Update user token in the server if needed
        }

        override fun onPushNotification(title: String?, body: String?) {
            super.onPushNotification(title, body)
            println("Push Notification notification title: $title")
        }

        override fun onPayloadData(data: PayloadData) {
            println("Push Notification payloadData: $data")
        //PayloadData is just typeAlias for Map<String,*>.
        }
    })
}
