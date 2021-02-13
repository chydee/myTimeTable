package com.chydee.mytimetable.ui.preferences

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.chydee.mytimetable.R
import com.chydee.mytimetable.utils.removeNavIcon

class AboutFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        removeNavIcon()
    }
}