package com.chydee.mytimetable.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chydee.mytimetable.R
import com.chydee.mytimetable.databinding.FragmentNewLessonBinding
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.autoCleared
import com.chydee.mytimetable.utils.setMarginTop
import com.chydee.mytimetable.utils.takeText
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewLessonFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: FragmentNewLessonBinding by autoCleared()

    private var startTime: String = "00:00"
    private var endTime: String = "00:00"

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

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.model = viewModel

        binding.fromTextInputLayout.setOnTouchListener { _, _ ->
            timePicker("Select Start Time", "From").show(childFragmentManager, "FromTimePicker")
            true
        }
        binding.toTextInputLayout.setOnTouchListener { _, _ ->
            timePicker("Select End Time", "To").show(childFragmentManager, "ToTimePicker")
            true
        }
        viewModel.timetableName.observe(viewLifecycleOwner, {
            if (it.isNotEmpty()) {
                binding.btnUp.text = it
            }
        })
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = android.graphics.Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.statusBarColor = android.graphics.Color.WHITE
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

    private fun timePicker(title: String, timeType: String): MaterialTimePicker {

        val isSystem24Hour = is24HourFormat(requireContext())
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


}