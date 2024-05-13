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

const val scheduleDoctorNotificationId = 1
const val scheduleDoctorChannelId = "channel1"
const val scheduleDoctorTitleExtra = "titleExtra"
const val scheduleDoctorMessageExtra = "messageExtra"

//        val intent = Intent(DoctorUserAppointmentsFragment().requireContext(), UserDoctorActivity::class.java)
//        val pendingIntent = TaskStackBuilder.create(DoctorUserAppointmentsFragment().requireContext()).run {
//            addNextIntentWithParentStack(intent)
//            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//        }

class NotificationDoctor : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent) {
        val intentt = Intent(context,UserDoctorActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intentt)
            getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        val notification  = NotificationCompat.Builder(context, scheduleDoctorChannelId)
            .setSmallIcon(R.drawable.hospital_notification_logo)
            .setContentTitle(intent.getStringExtra(scheduleDoctorTitleExtra))
            .setContentText(intent.getStringExtra(scheduleDoctorMessageExtra))
            .setContentIntent(pendingIntent)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(scheduleDoctorNotificationId,notification)
    }
}
