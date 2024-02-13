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

class GenUtils {

    companion object {
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
            when (status?.lowercase()) {
                "active" -> tv.setBackgroundColor(Color.parseColor("#ff4d4d"))
                "closed" -> tv.setBackgroundColor(Color.parseColor("#ddcc13"))
                "hot lead" -> tv.setBackgroundColor(Color.parseColor("#ff9933"))
                "warm lead" -> tv.setBackgroundColor(Color.parseColor("#009933"))
                "cold" -> tv.setBackgroundColor(Color.parseColor("#ddcc13"))
                "following up" -> tv.setBackgroundColor(Color.parseColor("#000099"))
                "pending" -> tv.setBackgroundColor(Color.parseColor("#e6b800"))
                "quote" -> tv.setBackgroundColor(Color.parseColor("#00cc00"))
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

        fun hasPermissions(context: Context, permissions: Array<String>): Boolean = permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}