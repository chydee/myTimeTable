package com.chydee.mytimetable.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun EditText.takeText() = this.text.toString()

/**
 * change the status bar color programmatically (and provided the device has Android 5.0)
 * then you can use Window.setStatusBarColor().
 * It shouldn't make a difference whether the activity is derived from Activity or ActionBarActivity.
 */
fun Activity.setStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }
}