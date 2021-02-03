package com.chydee.mytimetable.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chydee.mytimetable.R
import com.chydee.mytimetable.utils.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarColor(android.R.attr.colorBackground)
    }
}