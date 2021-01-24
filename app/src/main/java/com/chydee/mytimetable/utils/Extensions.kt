package com.chydee.mytimetable.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber


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

fun Activity.transparentStatusBar(isTransparent: Boolean, fullscreen: Boolean) {

    var defaultStatusBarColor: Int = 0
    if (isTransparent) {
        this.window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        (this as AppCompatActivity).supportActionBar!!.hide()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            defaultStatusBarColor = this.window.statusBarColor
            this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // FOR TRANSPARENT NAVIGATION BAR
            //activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            this.window.statusBarColor = Color.TRANSPARENT
            Timber.d(
                "Setting Color Transparent " + Color.TRANSPARENT.toString() + " Default Color " + defaultStatusBarColor
            )
        } else {
            Timber.d("Setting Color Trans " + Color.TRANSPARENT)
            this.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    } else {
        if (fullscreen) {
            val decorView = this.window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        } else {
            (this as AppCompatActivity).supportActionBar!!.show()
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                this.window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                this.window.statusBarColor = defaultStatusBarColor
            } else {
                this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }
    }
}