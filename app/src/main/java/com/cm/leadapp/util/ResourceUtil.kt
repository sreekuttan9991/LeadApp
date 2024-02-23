package com.cm.leadapp.util

import android.content.Context
import com.cm.kbslead.R


class ResourceUtil {

    companion object {
        fun getRandomColor(context: Context): Int {
            val colors: IntArray = context.resources.getIntArray(R.array.stat_colors)
            return colors[(Math.random() * colors.size).toInt()]
        }
    }
}