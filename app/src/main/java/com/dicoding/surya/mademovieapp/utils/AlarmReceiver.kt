package com.dicoding.surya.mademovieapp.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dicoding.surya.mademovieapp.R
import com.dicoding.surya.mademovieapp.data.models.Movie
import com.dicoding.surya.mademovieapp.data.network.MyApi
import com.dicoding.surya.mademovieapp.data.network.NetworkConnectionInterceptor
import com.dicoding.surya.mademovieapp.ui.main.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by suryamudti on 30/08/2019.
 */
class AlarmReceiver : BroadcastReceiver() {

    val TYPE_DAILY = "daily"
    val TYPE_RELEASE = "release"
    val EXTRA_MESSAGE = "message"
    val EXTRA_TYPE = "type"

    private val ID_DAILY = 101
    private val ID_RELEASE = 101

    private val TIME_FORMAT = "HH:mm"

    lateinit var title: String
    lateinit var message: String
    lateinit var type: String
    var notifId = 0

    override fun onReceive(context: Context, intent: Intent?) {
        type = intent?.getStringExtra(EXTRA_TYPE) as String
        message = intent.getStringExtra(EXTRA_MESSAGE) as String
        title = if (type.equals(TYPE_DAILY, ignoreCase = true)) TYPE_DAILY else TYPE_RELEASE
        notifId = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE

        if (type == TYPE_RELEASE){
            getReleaseTodayMovies(context)
        }
        else if (type == TYPE_DAILY){
            showAlarmNotification(context, title, message, notifId, type, arrayListOf())
        }
        Toast.makeText(context, "$title : $message", Toast.LENGTH_LONG).show()
    }

    fun getReleaseTodayMovies(context: Context){

        val listItemsMovie = ArrayList<Movie>()
        val todayDate = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        val interceptor = NetworkConnectionInterceptor(context)
        val api = MyApi(interceptor)

        Coroutines.main {
            val response = api.getReleaseToday(
                dateFormat.format(todayDate),
                dateFormat.format(todayDate)
            )

            response.body()?.results?.let { listItemsMovie.addAll(it) }

            if (listItemsMovie.size > 0)
                showAlarmNotification(context, title, message, notifId, type, listItemsMovie)

        }
    }

    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        if (isDateInvalid(time, TIME_FORMAT)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, if (type.equals(TYPE_DAILY,
                ignoreCase = true)) ID_DAILY else ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, context.getString(R.string.toast_reminder_enabled, type), Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(context: Context, title: String, message: String, notifId: Int, type: String,
                                      movieList: ArrayList<Movie>) {
        val CHANNEL_ID = context.getString(R.string.default_notification_channel_id)
        val CHANNEL_NAME = context.getString(R.string.default_notification_channel_name)

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("type", type)

        if (type == TYPE_RELEASE){
            intent.putExtra("movieList", movieList)
            Log.d("tes123", "movieList show = $movieList")
        }

        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm_black_24dp)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            notificationBuilder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = notificationBuilder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

    fun isDateInvalid(date: String, format: String): Boolean {
        return try {
            val df = SimpleDateFormat(format, Locale.getDefault())
            df.isLenient = false
            df.parse(date)
            false
        } catch (e: ParseException) {
            true
        }
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val requestCode = if (type.equals(TYPE_DAILY, ignoreCase = true)) ID_DAILY else ID_RELEASE
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)

        Toast.makeText(context, context.getString(R.string.toast_reminder_disabled, type), Toast.LENGTH_SHORT).show()

    }
}