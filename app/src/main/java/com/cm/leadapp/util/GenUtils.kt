package com.cm.leadapp.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.collections.HashMap

object GenUtils {

    private val followupColorMap: HashMap<String, String> by lazy {
        HashMap<String, String>().apply {
            put("call", "#9DFAB3")
            put("whatsapp", "#9BE892")
            put("email", "#F0C58B")
            put("meeting", "#FCCACA")
            put("", "#9DFAB3")
            put("null", "#9DFAB3")
        }
    }

    fun callPhone(context: Context?, phone: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phone")
        context?.startActivity(intent)
    }

    fun callWhatsApp(context: Context?, phone: String?) {
        val phoneNumberWithCountryCode = "+91$phone"
        val message = ""
        context?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(
                        "https://api.whatsapp.com/send?phone=%s&text=%s",
                        phoneNumberWithCountryCode,
                        message
                    )
                )
            )
        )
    }

    fun setStatus(tv: TextView, status: String?) {
        tv.text = status
        when (status?.lowercase()?.trim()?.replace(" ", "")) {
            "applicationformsend" -> tv.setBackgroundColor(Color.parseColor("#FF8C00"))
            "justenquiry" -> tv.setBackgroundColor(Color.parseColor("#8fbbef"))
            "ring/callbusy" -> tv.setBackgroundColor(Color.parseColor("#9602e6"))
            "delay" -> tv.setBackgroundColor(Color.parseColor("#f1cd0f"))
            "partiallyinterested" -> tv.setBackgroundColor(Color.parseColor("#8fb97d"))
            "interested" -> tv.setBackgroundColor(Color.parseColor("#37e602"))
            "partiallyintrested" -> tv.setBackgroundColor(Color.parseColor("#8fb97d"))
            "intrested" -> tv.setBackgroundColor(Color.parseColor("#37e602"))
            "awaitingresponse" -> tv.setBackgroundColor(Color.parseColor("#404040"))
            "switchoff" -> tv.setBackgroundColor(Color.parseColor("#a8dced"))
            "notrelatedtomba" -> tv.setBackgroundColor(Color.parseColor("#027fe6"))
            "kmatintrested" -> tv.setBackgroundColor(Color.parseColor("#e68b02"))
            "kmatinterested" -> tv.setBackgroundColor(Color.parseColor("#e68b02"))
            else -> tv.setBackgroundColor(Color.parseColor("#404040"))
        }
    }

    fun getConvertedDate(
        calendar: Calendar,
        mYear: Int,
        mMonth: Int,
        mDay: Int,
        pattern: String = "MM/dd/yyyy"
    ): String {
        val dateParser = SimpleDateFormat(
            pattern,
            Locale.ENGLISH
        )
        calendar.set(Calendar.YEAR, mYear)
        calendar.set(Calendar.MONTH, mMonth)
        calendar.set(Calendar.DAY_OF_MONTH, mDay)
        return dateParser.format(calendar.time).toString()
    }

    fun getConvertedTime(calendar: Calendar, mHour: Int, mMinute: Int): String {
        val dateParser = SimpleDateFormat(
            "hh:mm a",
            Locale.ENGLISH
        )
        calendar.set(Calendar.HOUR_OF_DAY, mHour)
        calendar.set(Calendar.MINUTE, mMinute)
        return dateParser.format(calendar.time).toString()
    }

    fun getFollowupDate(
        calendar: Calendar,
        mYear: Int,
        mMonth: Int,
        mDay: Int,
        mHour: Int,
        mMinute: Int
    ): String {
        val dateParser = SimpleDateFormat(
            "MM/dd/yyyy HH:mm",
            Locale.ENGLISH
        )
        calendar.set(Calendar.YEAR, mYear)
        calendar.set(Calendar.MONTH, mMonth)
        calendar.set(Calendar.DAY_OF_MONTH, mDay)
        calendar.set(Calendar.HOUR_OF_DAY, mHour)
        calendar.set(Calendar.MINUTE, mMinute)
        return dateParser.format(calendar.time).toString()
    }

    fun getTimeInMillis(dateTime: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        var timeInMilliseconds = 0L
        try {
            val mDate: Date? = sdf.parse(dateTime)
            timeInMilliseconds = mDate?.time!!
            println("Date in milli :: $timeInMilliseconds")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    fun getDate(milliSeconds: Long, dateFormat: String?): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    fun getFollowupColor(followupType: String?): String? =
        followupColorMap[followupType?.lowercase()]
}