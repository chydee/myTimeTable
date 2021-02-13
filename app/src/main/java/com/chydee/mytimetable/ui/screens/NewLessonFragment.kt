package com.chydee.mytimetable.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chydee.mytimetable.R
import com.chydee.mytimetable.data.models.Color
import com.chydee.mytimetable.databinding.FragmentNewLessonBinding
import com.chydee.mytimetable.ui.adapters.LabelsAdapter
import com.chydee.mytimetable.ui.viewmodel.MainViewModel
import com.chydee.mytimetable.utils.autoCleared
import com.chydee.mytimetable.utils.colors
import com.chydee.mytimetable.utils.setMarginTop
import com.chydee.mytimetable.utils.takeText
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewLessonFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var binding: FragmentNewLessonBinding by autoCleared()

    private lateinit var labelsAdapter: LabelsAdapter

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
            insets.consumeSystemWindowInsets()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.statusBarColor = android.graphics.Color.TRANSPARENT
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.window?.statusBarColor = android.graphics.Color.WHITE
    }

    private fun loadAndSetupLabelList() {
        val layoutManager = LinearLayoutManager(requireContext())

        binding.labelsRecyclerView.layoutManager = layoutManager

        labelsAdapter = LabelsAdapter()

        with(binding) {
            labelsRecyclerView.adapter = labelsAdapter
            labelsAdapter.submitList(colors)
            labelsRecyclerView.setHasFixedSize(true)
            labelsRecyclerView.isNestedScrollingEnabled = false
        }

        labelsAdapter.setOnLabelClickListener(object : LabelsAdapter.OnItemClickListener {
            override fun onLabelClicked(color: Color) {
                TODO("Not yet implemented")
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

    /**
     *  Inset the top view-group item so it doesn't go all the way up
     */
    private fun insetView() {
        /*ViewCompat.setOnApplyWindowInsetsListener(binding.root.findViewById(R.id.content_container)) { _, insets ->
            binding.root.findViewById<MaterialButton>(R.id.btnUp)
                .setMarginTop(insets.systemWindowInsetTop)
            insets.consumeSystemWindowInsets()
        }*/
    }


}