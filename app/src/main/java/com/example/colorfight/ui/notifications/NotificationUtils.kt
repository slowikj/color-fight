package com.example.colorfight.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.colorfight.R

const val colorUpdateNotificationChannelId: String = "colors_notifications_channel_id"

fun AppCompatActivity.registerColorUpdateChannel() {
	val channelName = getString(R.string.colors_update_channel)
	val descriptionText = getString(R.string.colors_update_channel_description)

	registerNotificationChannel(
		channelId = colorUpdateNotificationChannelId,
		channelName = channelName,
		channelDescription = descriptionText
	)
}

fun ContextWrapper.showColorUpdateNotification(
	title: String,
	body: String
) {
	showNotification(
		channelId = colorUpdateNotificationChannelId,
		title = title,
		body = body,
		color = getColor(R.color.green)
	)
}

fun ContextWrapper.showNotification(
	channelId: String,
	title: String,
	body: String,
	color: Int
) {
	val notificationBuilder = NotificationCompat.Builder(this, channelId)
		.setContentTitle(title)
		.setContentText(body)
		.setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
		.setPriority(NotificationCompat.PRIORITY_HIGH)
		.setStyle(NotificationCompat.BigTextStyle())
		.setColor(color)
		.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
		.setSmallIcon(R.mipmap.ic_launcher)
		.setAutoCancel(true)

	notificationManager.notify(0, notificationBuilder.build())
}

fun AppCompatActivity.registerNotificationChannel(
	channelId: String,
	channelName: String,
	channelDescription: String = ""
) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply {
			description = channelDescription
			enableLights(true)
			lightColor = getColor(R.color.green)
		}
		notificationManager.createNotificationChannel(channel)
	}
}

val ContextWrapper.notificationManager: NotificationManager
	get() = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager