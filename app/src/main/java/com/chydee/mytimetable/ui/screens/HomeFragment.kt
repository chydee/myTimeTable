package com.chydee.mytimetable.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.databinding.HomeFragmentBinding
import com.chydee.mytimetable.ui.adapters.DayEventsAdapter
import com.chydee.mytimetable.ui.adapters.OnLessonClickListener
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var binding: HomeFragmentBinding by autoCleared()

    private lateinit var adapter: DayEventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()
        getCurrentDayLessons(getDayOfTheWeek())
    }

    private fun handleClickEvents() {
        binding.btnDismiss.setOnClickListener {
            binding.defaultWelcomeCard.hide() //This hides or removes the view when users hit the dismiss button
        }

        binding.textBtnClickToSeeDetails.setOnClickListener {

        }
    }


    /**
     *  Query the database and get the lessons available for the...
     *  ...day
     *  @param currentDayName is the day of the week
     */
    @ExperimentalCoroutinesApi
    private fun getCurrentDayLessons(currentDayName: String) {
        viewModel.getLessonsForCurrentDay(currentDayName)
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

    private fun getDayOfTheWeek(): String {
        return SimpleDateFormat(CURRENT_DAY_FORMAT, Locale.getDefault()).format(Date())
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