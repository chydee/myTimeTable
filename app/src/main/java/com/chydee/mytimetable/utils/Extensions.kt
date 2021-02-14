package com.chydee.mytimetable.utils

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.chydee.mytimetable.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar


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

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

fun Activity.navigateTo(
        navController: NavController,
        @IdRes destination: Int,
        popCurrentDes: Boolean = false
) {
    val builder = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.fade_out)

    if (popCurrentDes) { // removes CurrentDes from back stack
        builder.setLaunchSingleTop(true)
        navController.currentDestination?.id?.let { builder.setPopUpTo(it, true) }
    }

    navController.navigate(destination, null, builder.build())
}

fun Fragment.pop() {
    findNavController().popBackStack()
}

fun Fragment.pop(@IdRes destination: Int, inclusive: Boolean) {
    findNavController().popBackStack(destination, inclusive)
}

/**
 * Remove the default Navigation Icon
 */
fun Fragment.removeNavIcon() {
    requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar).navigationIcon = null
}

fun View.showSnackBar(message: String) {
    this.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
    }
}

fun View.snackBarWithAction(message: String, actionName: String, action: () -> Unit) {
    this.let {
        Snackbar.make(it, message, Snackbar.LENGTH_LONG)
                .setAction(actionName) {
                    action()
                }
                .show()
    }
}

