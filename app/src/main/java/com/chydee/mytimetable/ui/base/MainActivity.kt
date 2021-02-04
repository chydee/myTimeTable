package com.chydee.mytimetable.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.chydee.mytimetable.R
import com.chydee.mytimetable.databinding.ActivityMainBinding
import com.chydee.mytimetable.utils.setStatusBarColor
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBarColor(android.R.attr.colorBackground)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            /* when (destination.id) {
                 R.id.homeFragment -> {
                     setStatusBarColor(android.R.attr.colorBackground)
                 }

                 R.id.newTimetableFragment, R.id.newLessonFragment -> {
                     makeStatusBarTransparent()
                 }
                 R.id.lessonDetailsFragment -> {
                     Toast.makeText(applicationContext, "Details", Toast.LENGTH_SHORT).show()
                 }
                 else -> {
                     Timber.d("NoSuchID")
                 }
             }*/
        }
    }
}