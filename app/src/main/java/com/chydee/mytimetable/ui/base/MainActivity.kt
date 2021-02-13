package com.chydee.mytimetable.ui.base

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.work.WorkManager
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.preference.PreferenceStorage
import com.chydee.mytimetable.databinding.ActivityMainBinding
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.getDayOfTheWeek
import com.chydee.mytimetable.utils.hide
import com.chydee.mytimetable.utils.navigateTo
import com.chydee.mytimetable.utils.show
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private var drawerLayout: DrawerLayout? = null
    private lateinit var toggle: ActionBarDrawerToggle

    private var appBarConfiguration: AppBarConfiguration? = null

    private var toolBar: MaterialToolbar? = null

    private lateinit var workManager: WorkManager

    @Inject
    lateinit var prefStorage: PreferenceStorage

    private val viewModel: MainViewModel by viewModels()

    private var defaultTimetableName: String? = null

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.tableFragment,
            R.id.timetablesFragment,
            R.id.settingsFragment,
            R.id.aboutFragment,
        )
            .setOpenableLayout(binding.mainDrawer)
            .build()
        setUpAppBar()
        setUpNavController()
        initNavDrawer()

        lifecycleScope.launchWhenStarted {
            defaultTimetableName = prefStorage.defaultTimetableName.firstOrNull()
            defaultTimetableName.let {
                Timber.d("Default Timetable Name: $it")

            }
        }
        defaultTimetableName?.let { getCurrentDayLessons(it) }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration!!)
    }


    @SuppressLint("NonConstantResourceId")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when (item.itemId) {
            R.id.homeFragment -> navigateTo(navController, R.id.homeFragment)
            R.id.timetablesFragment -> navigateTo(
                navController,
                R.id.action_global_timetablesFragment
            )
            R.id.settingsFragment -> navigateTo(navController, R.id.action_global_settingsFragment)
            R.id.aboutFragment -> navigateTo(navController, R.id.action_global_aboutFragment)
            else -> navigateTo(navController, R.id.homeFragment)
        }
        closeDrawer()
        return true
    }

    /**
     * Sets up NevController with ActionBar
     */
    private fun setUpNavController() {
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration!!)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    showBar()
                }

                R.id.newTimetableFragment, R.id.newLessonFragment, R.id.lessonDetailsFragment -> {
                    //setStatusBarColor(android.R.color.transparent)
                    hideBar()
                }
                else -> {
                    Timber.d("NoSuchID")
                    showBar()
                }
            }
        }
    }

    /**
     * Initialise and set up NavDrawer
     */
    private fun initNavDrawer() {
        drawerLayout = findViewById(R.id.mainDrawer)
        toggle = object : ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerClosed(drawerView: View) {
                closeDrawer()
            }

            override fun onDrawerOpened(drawerView: View) {
                openDrawer()
            }
        }
        toggle.isDrawerSlideAnimationEnabled = true
        drawerLayout?.addDrawerListener(toggle)
        toggle.syncState()
        toggle.isDrawerIndicatorEnabled = false
        toolBar?.navigationIcon = null
    }

    /**
     * Set up AppBar
     */
    private fun setUpAppBar() {
        toolBar = binding.root.findViewById(R.id.topAppBar)
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = ""
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.setDisplayShowHomeEnabled(false)
            toolBar?.navigationIcon = null
        }
        binding.btnSideMenu.setOnClickListener {
            openDrawer()
        }
    }

    /**
     *   Closes NavDrawer
     */
    private fun closeDrawer() {
        drawerLayout!!.closeDrawer(GravityCompat.START)
    }

    /**
     * Opens NavDrawer
     */
    private fun openDrawer() {
        drawerLayout!!.openDrawer(GravityCompat.START)
    }


    /**
     * Show AppBar or ToolBar
     */
    private fun showBar() {
        toolBar?.show()
    }

    /**
     * Hide AppBar or ToolBar
     */
    private fun hideBar() {
        toolBar?.hide()
    }


    /**
     *  Query the database and get the lessons available for the...
     *  ...day
     *  @param currentDayName is the day of the week
     */
    @ExperimentalCoroutinesApi
    private fun getCurrentDayLessons(currentDayName: String) {
        viewModel.getLessonsForCurrentDay(getDayOfTheWeek(), currentDayName)
        observeCurrentDayLessons()
    }

    private fun observeCurrentDayLessons() {
        viewModel.currentDayLessons.observe(this, {
            if (it.isNotEmpty()) {
                val text = getString(R.string.class_in, it[0].courseTitle, it[0].startTime)

            } else {
            }
        })
    }
}