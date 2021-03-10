package com.webserveis.app.aboutscreen

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.preference.Preference
import androidx.preference.PreferenceViewHolder

/*
https://github.com/TwidereProject/Twidere-Android/blob/master/twidere/src/main/kotlin/org/mariotaku/twidere/preference/ColorPickerPreference.kt
 https://stackoverflow.com/questions/53834600/custom-preference-android-kotlin
 */

class PreferenceAboutHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : Preference(context, attrs, defStyleAttr) {

    init {
        widgetLayoutResource = R.layout.preference_about_header
    }

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)
        val aboutAppIcon = holder.findViewById(R.id.about_app_icon) as AppCompatImageView
        layoutResource = R.layout.preference_about_header
        aboutAppIcon.setImageResource(R.drawable.ic_launcher_round)
    }
}
