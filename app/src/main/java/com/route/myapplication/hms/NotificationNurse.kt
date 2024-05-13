package com.route.myapplication.hms

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.route.myapplication.hms.ui.DoctorUserFragments.DoctorUserAppointmentsFragment
import com.route.myapplication.hms.ui.UserDoctorActivity
import com.route.myapplication.hms.ui.UserNurseActivity

const val scheduleNurseNotificationId = 3
const val scheduleNurseChannelId = "channel1"
const val scheduleNurseTitleExtra = "titleExtra"
const val scheduleNurseMessageExtra = "messageExtra"

//        val intent = Intent(DoctorUserAppointmentsFragment().requireContext(), UserDoctorActivity::class.java)
//        val pendingIntent = TaskStackBuilder.create(DoctorUserAppointmentsFragment().requireContext()).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        }

class NotificationNurse : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val intentt = Intent(context,UserNurseActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intentt)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val notification  = NotificationCompat.Builder(context, scheduleNurseChannelId)
            .setSmallIcon(R.drawable.hospital_notification_logo)
            .setContentTitle(intent.getStringExtra(scheduleNurseTitleExtra))
            .setContentText(intent.getStringExtra(scheduleNurseMessageExtra))
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(scheduleNurseNotificationId,notification)
    }
}
