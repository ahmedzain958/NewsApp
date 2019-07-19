package com.zainco.newsapp.internal

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.util.*

object DrawableUtils {
    var vibrantLightColorList = arrayOf(
        ColorDrawable(Color.parseColor("#ffeead")),
        ColorDrawable(Color.parseColor("#93cfb3")),
        ColorDrawable(Color.parseColor("#fd7a7a")),
        ColorDrawable(Color.parseColor("#faca5f")),
        ColorDrawable(Color.parseColor("#1ba798")),
        ColorDrawable(Color.parseColor("#6aa9ae")),
        ColorDrawable(Color.parseColor("#ffbf27")),
        ColorDrawable(Color.parseColor("#d93947"))
    )

    val randomDrawbleColor: ColorDrawable
        get() {
            val idx = Random().nextInt(vibrantLightColorList.size)
            return vibrantLightColorList[idx]
        }

}