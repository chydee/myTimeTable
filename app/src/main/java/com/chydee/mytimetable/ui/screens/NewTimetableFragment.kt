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
import com.chydee.mytimetable.data.models.Timetable
import com.chydee.mytimetable.databinding.FragmentNewTimetableBinding
import com.chydee.mytimetable.ui.adapters.DivLikeAdapter
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.autoCleared
import com.chydee.mytimetable.utils.makeStatusBarTransparent
import com.chydee.mytimetable.utils.setMarginTop
import com.chydee.mytimetable.utils.takeText
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
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
        setupTagList()
    }

    /**
     *  Listen and Handle click events
     */
    private fun handleOnClickEvents() {
        binding.btnContinue.setOnClickListener {
            validateAndContinue()
        }
        binding.btnUp.setOnClickListener { findNavController().popBackStack() }
    }

    /**
     *  Validate by verify that the name field is not empty and then continue
     */
    private fun validateAndContinue() {
        val name = binding.timetableNameInput.takeText()

        if (name.isEmpty()) {
            binding.timetableNameTextInputLayout.error = "Timetable name is empty"
        } else {
            saveTimetableName(name)
        }
    }

    /**
     *  Save Timetable to database
     */
    private fun saveTimetableName(tableName: String) {
        val timetable = Timetable(tableName = tableName)
        viewModel.saveTimetableInfo(timetable)
        observePropertyTimetable()
    }

    private fun observePropertyTimetable() {
        viewModel.timetableName.observe(viewLifecycleOwner, {
            if (it.isNotBlank()) {
                findNavController().navigate(NewTimetableFragmentDirections.actionNewTimetableFragmentToNewLessonFragment())
            } else {
                Toast.makeText(context, "Error creating Timetable", Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     *  Inset the top view-group item so it doesn't go all the way up
     */
    private fun insetView() {
        requireActivity().makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root.findViewById(R.id.newTableScreen)) { _, insets ->
            binding.root.findViewById<MaterialButton>(R.id.btnUp)
                .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }
    }

    /**
     * set up timetable tags list
     */
    private fun setupTagList() {
        val recyclerView = binding.timetableTagsRecyclerView
        val layoutManager = FlexboxLayoutManager(requireContext())
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        recyclerView.layoutManager = layoutManager

        val divLikeContent = ArrayList<String>()
        divLikeContent.add("Route")
        divLikeContent.add("No calls during the ride")
        divLikeContent.add("Smell")
        divLikeContent.add("Less talk")
        divLikeContent.add("Safety")

        val adapter = DivLikeAdapter(divLikeContent)

        recyclerView.adapter = adapter
    }

}