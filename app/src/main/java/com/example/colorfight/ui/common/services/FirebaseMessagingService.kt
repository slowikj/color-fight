package com.example.colorfight.ui.common.services

import com.example.colorfight.ui.common.notifications.showColorUpdateNotification
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class AppFirebaseMessagingService : FirebaseMessagingService() {

	override fun onMessageReceived(msg: RemoteMessage?) {
		super.onMessageReceived(msg)
		msg ?: return
		showColorUpdateNotification(
			title = msg.notification?.title ?: "",
			body = msg.notification?.body ?: ""
		)
	}
}