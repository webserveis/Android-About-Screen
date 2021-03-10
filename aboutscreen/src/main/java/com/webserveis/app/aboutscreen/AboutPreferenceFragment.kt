package com.webserveis.app.aboutscreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.util.*

class AboutPreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences_about, rootKey)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.findPreference<Preference>("pref_app_name")?.apply {
            title = AboutUtils.getAppName((requireContext()))
            summary = AboutUtils.getAppVersionName(requireContext()) + " (" + AboutUtils.getAppVersionCode(requireContext()) + ")"
        }

        val str = String.format(
            getString(R.string.about_copyright),
            Calendar.getInstance().get(Calendar.YEAR)
        )
        this.findPreference<Preference>("pref_copyright")?.title = str

        // feedback preference click listener
        val prefSendFeedback = this.findPreference<Preference>("pref_send_feedback")
        prefSendFeedback?.onPreferenceClickListener = Preference.OnPreferenceClickListener {

            AboutUtils.composeEmail(
                requireContext(),
                getString(R.string.about_email_feedback),
                "Feedback: " + AboutUtils.getAppName(requireContext())
            )

            true
        }

        val prefOpenSourceLicense = this.findPreference<Preference>("pref_open_source_license")
        prefOpenSourceLicense?.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            AboutUtils.showFileTextDialog(requireActivity(), getString(R.string.pref_about_open_license_title), "other_license.txt", "open_source_license")
            true
        }

        val prefRateApp = this.findPreference<Preference>("pref_rate_app")
        prefRateApp?.setOnPreferenceClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + requireContext().packageName))
            startActivity(browserIntent)
            true
        }

        val easterEgg = this.findPreference<Preference>("pref_app_name")
        easterEgg?.onPreferenceClickListener = CountClickListener(
            object : CountClickListener.CountClickListener {
                override fun onCountClick() {
                    Toast.makeText(requireContext(), "\"¯\\\\_(ツ)_/¯", Toast.LENGTH_SHORT).show()
                }
            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_about, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_app_info -> {
                AboutUtils.openDetailAppSettings(requireActivity())
                true
            }
            R.id.action_share -> {
                val google_play_link = "https://play.google.com/store/apps/details?id=" + requireContext().packageName
                ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/plain")
                    .setText(String.format(getString(R.string.action_share_description), google_play_link)) //.setHtmlText(body) //If you are using HTML in your body text
                    .setChooserTitle(R.string.pref_about_send_feedback_header)
                    .startChooser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
