package com.cm.leadapp.util

import android.content.Context
import com.cm.kbslead.R

object ResourceUtil {
    fun getRandomColor(context: Context): Int {
        val colors: IntArray = context.resources.getIntArray(R.array.stat_colors)
        return colors[(Math.random() * colors.size).toInt()]
    }

    fun getColor(context: Context, resId: Int): Int {
        return context.resources.getColor(resId, null)
    }
}