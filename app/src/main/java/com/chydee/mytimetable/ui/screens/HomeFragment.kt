package com.chydee.mytimetable.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.data.preference.PreferenceStorage
import com.chydee.mytimetable.databinding.FragmentHomeBinding
import com.chydee.mytimetable.ui.adapters.DayEventsAdapter
import com.chydee.mytimetable.ui.adapters.OnLessonClickListener
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.*
import com.google.android.material.appbar.MaterialToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var binding: FragmentHomeBinding by autoCleared()

    private lateinit var adapter: DayEventsAdapter

    @Inject
    lateinit var prefStorage: PreferenceStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        insetView()
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        lifecycleScope.launchWhenStarted {
            val timeTableName: String? = prefStorage.defaultTimetableName.firstOrNull()
            timeTableName?.let {
                Timber.d("Timetable name: $it")
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
        setupRecyclerView()
        // getCurrentDayLessons(getDayOfTheWeek())
        handleClickEvents()
    }

    private fun handleClickEvents() {
        binding.btnDismiss.setOnClickListener {
            binding.defaultWelcomeCard.hide() //This hides or removes the view when users hit the dismiss button
        }

        binding.textBtnClickToSeeDetails.setOnClickListener {
        }

        binding.btnCreateNewTimetable.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewTimetableFragment())
        }

        binding.btnSeeAll.setOnClickListener {

        }
    }

    private fun insetView() {
        requireActivity().makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root.findViewById(R.id.homeContentContainer)) { _, insets ->
            binding.root.findViewById<LinearLayout>(R.id.homeLayoutGroup)
                .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }

        requireActivity().findViewById<MaterialToolbar>(R.id.topAppBar).navigationIcon = null
    }


    /**
     *  Query the database and get the lessons available for the...
     *  ...day
     *  @param tableName is the default timetable name
     */
    @ExperimentalCoroutinesApi
    private fun getCurrentDayLessons(tableName: String) {
        viewModel.getLessonsForCurrentDay(getDayOfTheWeek(), tableName)
        observeCurrentDayLessons()
    }

    private fun observeCurrentDayLessons() {
        viewModel.currentDayLessons.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                val text = getString(R.string.class_in, it[0].courseTitle, it[0].startTime)
                binding.classInTextView.text = text
                adapter.submitList(it)
                showAndHideViewsWhenLessonListIsNotEmpty()
            } else {
                hideAndShowViewsWhenLessonListIsNotEmpty()
            }
        })
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(context)

        if (!::adapter.isInitialized) {
            adapter = DayEventsAdapter()
        }
        binding.apply {
            todayLessonsRecyclerView.adapter = adapter
            todayLessonsRecyclerView.layoutManager = manager
            todayLessonsRecyclerView.isNestedScrollingEnabled = false
            todayLessonsRecyclerView.addItemDecoration(
                MarginItemDecoration(
                    resources.getDimension(R.dimen.dp_8).toInt()
                )
            )
        }
        adapter.setOnClickListener(object : OnLessonClickListener {
            override fun onItemClick(lesson: Lesson) {
                //Item Clicked
            }
        })
    }

    /**
     * Show the RecyclerView and hide the emptyStateView when the...
     * ...List of lessons available for the day is not empty
     */
    private fun showAndHideViewsWhenLessonListIsNotEmpty() {
        binding.apply {
            todayLessonsRecyclerView.show()
            emptyStateText.hide()
        }
    }

    /**
     * Hide the RecyclerView and hide the emptyStateView when the...
     * ...List of lessons available for the day is  empty
     */
    private fun hideAndShowViewsWhenLessonListIsNotEmpty() {
        binding.apply {
            todayLessonsRecyclerView.hide()
            emptyStateText.show()
        }
    }

}