package com.chydee.mytimetable.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chydee.mytimetable.R
import com.chydee.mytimetable.databinding.FragmentNewTimetableBinding
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.autoCleared
import com.chydee.mytimetable.utils.makeStatusBarTransparent
import com.chydee.mytimetable.utils.setMarginTop
import com.chydee.mytimetable.utils.takeText
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewTimetableFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var binding: FragmentNewTimetableBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewTimetableBinding.inflate(inflater)
        insetView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleOnClickEvents()
    }

    /**
     *  Listen and Handle click events
     */
    private fun handleOnClickEvents() {
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(NewTimetableFragmentDirections.actionNewTimetableFragmentToNewLessonFragment())
            // validateAndContinue()
        }
        binding.btnUp.setOnClickListener { findNavController().popBackStack() }
    }

    private fun validateAndContinue() {
        val name = binding.timetableNameInput.takeText()

        if (name.isEmpty()) {
            binding.timetableNameTextInputLayout.error = "Timetable name is empty"
        } else {
            saveTimetableName()
        }
    }

    private fun saveTimetableName() {
        Toast.makeText(context, "Saving...", Toast.LENGTH_SHORT).show()
    }

    private fun insetView() {
        requireActivity().makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root.findViewById(R.id.newTableScreen)) { _, insets ->
            binding.root.findViewById<MaterialButton>(R.id.btnUp)
                .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

}