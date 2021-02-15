@file:Suppress("DEPRECATION")

package com.chydee.mytimetable.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Lesson
import com.chydee.mytimetable.data.models.Timetable
import com.chydee.mytimetable.databinding.FragmentNewLessonBinding
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewLessonFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private var binding: FragmentNewLessonBinding by autoCleared()

    private lateinit var days: Array<String>

    private var startTime: String = "00:00"
    private var endTime: String = "00:00"

    private var isBatchMode: Boolean = false

    private lateinit var timetable: Timetable

    private lateinit var lessons: ArrayList<Lesson>

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewLessonBinding.inflate(inflater)
        /**
         *  Inset the top view-group item so it doesn't go all the way up
         */
        ViewCompat.setOnApplyWindowInsetsListener(
                binding.root.findViewById(
                        R.id.newLessonScreen
                )
        ) { _, insets ->
            binding.root.findViewById<MaterialButton>(R.id.btnUp)
                    .setMarginTop(insets.systemWindowInsetTop)
            binding.root.findViewById<MaterialButton>(R.id.btnSaveLesson)
                    .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.model = viewModel
        lessons = arrayListOf()
        days = arrayOf<String>(getString(R.string.monday), getString(R.string.tuesday), getString(R.string.wednesday), getString(R.string.thursday), getString(R.string.friday), getString(R.string.saturday))
        handleOnClickEvents()
        observeChanges()

    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = android.graphics.Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.statusBarColor = android.graphics.Color.WHITE
    }

    /**
     * All Click events are handled here
     */
    @SuppressLint("ClickableViewAccessibility")
    private fun handleOnClickEvents() {
        binding.btnUp.setOnClickListener {
            pop(R.id.newTimetableFragment, true)
        }

        binding.btnSaveLesson.setOnClickListener {
            if (isValidated()) {
                val lesson = Lesson(
                        tableId = timetable.id,
                        tableName = timetable.tableName,
                        courseCode = binding.codeInput.takeText(),
                        courseTitle = binding.titleInput.takeText(),
                        courseDescription = binding.descriptionInput.takeText(),
                        courseTutor = binding.teacherInput.takeText(),
                        place = binding.placeInput.takeText(),
                        dayOfWeek = convertDayToNumber(binding.dayInput.takeText()),
                        startTime = binding.fromInput.takeText(),
                        endTime = binding.toInput.takeText(),
                        illusID = getSubjectAvatar(binding.titleInput.takeText())
                )
                if (isBatchMode) {
                    addLessonToList(lesson)
                } else {
                    saveSingleLesson(lesson)
                }
            }
        }

        binding.switchEnableBatchMode.setOnCheckedChangeListener { _, isChecked ->
            isBatchMode = isChecked
        }

        binding.dayInput.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dayOfWeek()
                }
                MotionEvent.ACTION_UP -> v.performClick()
                else -> {
                }
            }
            true
        })

        binding.fromInput.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    timePicker("Select Start Time", "From").show(childFragmentManager, "FromTimePicker")
                }
                MotionEvent.ACTION_UP -> v.performClick()
                else -> {
                }
            }
            true
        })

        binding.toInput.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    timePicker("Select End Time", "To").show(childFragmentManager, "ToTimePicker")
                }
                MotionEvent.ACTION_UP -> v.performClick()
                else -> {
                }
            }
            true
        })
    }


    private fun saveSingleLesson(lesson: Lesson) {
        viewModel.addLesson(lesson)
    }


    private fun addLessonToList(lesson: Lesson) {
        lessons.add(lesson)
        saveMultipleLessons()
    }

    private fun saveMultipleLessons() {
        if (lessons.size > 1) {
            viewModel.addLessons(lessons)
        }
    }


    private fun observeChanges() {
        viewModel.timetable.observe(requireActivity(), {
            if (it != null) {
                timetable = it
                binding.btnUp.text = it.tableName
            }
        })

        viewModel.lesson.observe(requireActivity(), {
            if (it != null) {
                binding.root.snackBarWithAction("Lesson Saved!", "Add More Lessons") {
                    binding.switchEnableBatchMode.isChecked = true
                    clearFields()
                }
            }
        })
    }

    private fun isValidated(): Boolean {
        if (binding.codeInput.takeText().isEmpty()) {
            binding.codeTextInputLayout.error = getString(R.string.code_input_error)
            return false
        }
        if (binding.titleInput.takeText().isEmpty()) {
            binding.titleTextInputLayout.error = getString(R.string.title_input_error)
            return false
        }

        if (binding.dayInput.takeText().isEmpty()) {
            binding.dayTextInputLayout.error = getString(R.string.day_input_error)
            return false
        }

        if (binding.fromInput.takeText().isEmpty()) {
            binding.fromTextInputLayout.error = getString(R.string.from_input_error)
            return false
        }

        if (binding.toInput.takeText().isEmpty()) {
            binding.toTextInputLayout.error = getString(R.string.to_input_error)
            return false
        }

        return true
    }

    private fun clearFields() {
        with(binding) {
            codeInput.text?.clear()
            titleInput.text?.clear()
            descriptionInput.text?.clear()
            teacherInput.text?.clear()
            placeInput.text?.clear()
            dayInput.text?.clear()
            fromInput.text?.clear()
            toInput.text?.clear()
        }
    }

    private fun timePicker(title: String, timeType: String): MaterialTimePicker {

        val isSystem24Hour = is24HourFormat(requireActivity())
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
        val picker = MaterialTimePicker.Builder()
                .setTitleText(title)
                .setTimeFormat(clockFormat)
                .setHour(12)
                .setMinute(10)
                .build()

        picker.addOnPositiveButtonClickListener {
            if (timeType == "From") {
                startTime = "${picker.hour}:${picker.minute}"
                binding.fromInput.setText(startTime)
            } else {
                endTime = "${picker.hour}:${picker.minute}"
                binding.toInput.setText(endTime)
            }
            picker.dismiss()
        }
        picker.addOnNegativeButtonClickListener {
            picker.dismiss()
        }

        return picker
    }

    private fun dayOfWeek() {
        MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Select Day")
                .setItems(days) { dialog, which ->
                    val ddd = days[which]
                    binding.dayInput.setText(formatDayOfWeek(ddd))
                    dialog.dismiss()
                }
                .show()
    }

    private fun formatDayOfWeek(day: String): String? {
        return when (day) {
            days[0] -> "Sun"
            days[1] -> "Mon"
            days[2] -> "Tue"
            days[3] -> "Wed"
            days[4] -> "Thur"
            days[5] -> "Fri"
            days[6] -> "Sat"
            else -> null
        }
    }
}