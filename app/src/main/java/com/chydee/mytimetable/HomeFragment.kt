package com.chydee.mytimetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.databinding.HomeFragmentBinding
import com.chydee.mytimetable.ui.adapters.DayEventsAdapter
import com.chydee.mytimetable.ui.adapters.OnLessonClickListener
import com.chydee.mytimetable.utils.CURRENT_DAY_FORMAT
import com.chydee.mytimetable.utils.MarginItemDecoration
import com.chydee.mytimetable.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        setupRecyclerView()

    }

    private fun getTodayClasses() {}


    private fun getDayOfTheWeek(): String {
        return SimpleDateFormat(CURRENT_DAY_FORMAT, Locale.getDefault()).format(Date())
    }

    private fun setupRecyclerView() {

        val manager = LinearLayoutManager(context)
        val lessons = arrayListOf<Lesson>()

        if (!::adapter.isInitialized) {
            adapter = DayEventsAdapter()
        }

        binding.todayLessonsRecyclerView.adapter = adapter
        binding.todayLessonsRecyclerView.layoutManager = manager
        binding.todayLessonsRecyclerView.addItemDecoration(
            MarginItemDecoration(
                resources.getDimension(R.dimen.dp_8).toInt()
            )
        )
        adapter.submitList(lessons)

        adapter.setOnClickListener(object : OnLessonClickListener {
            override fun onItemClick(lesson: Lesson) {
                //Item Clicked
            }
        })
    }

}