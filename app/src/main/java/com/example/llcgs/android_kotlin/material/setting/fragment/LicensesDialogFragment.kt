package com.example.llcgs.android_kotlin.material.setting.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.utils.ViewUtils

import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.NoticesXmlParser
import de.psdev.licensesdialog.model.Notices

class LicensesDialogFragment
@Deprecated("Use {@link #newInstance()} instead.")
constructor() : AppCompatDialogFragment() {

    private var mNotices: Notices? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            mNotices = savedInstanceState.getParcelable(STATE_NOTICES)
        } else {
            try {
                mNotices = NoticesXmlParser.parse(activity!!.resources.openRawResource(
                        R.raw.licenses))
            } catch (e: Exception) {
                throw IllegalStateException(e)
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(STATE_NOTICES, mNotices)
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        val htmlStyleRes = if (ViewUtils.isLightTheme(activity))
            R.string.settings_open_source_licenses_html_style_light
        else
            R.string.settings_open_source_licenses_html_style_dark
        return LicensesDialog.Builder(activity!!)
                .setThemeResourceId(theme)
                .setTitle(R.string.settings_open_source_licenses_title)
                .setCloseText(R.string.close)
                .setNotices(mNotices)
                .setIncludeOwnLicense(true)
                .setNoticesCssStyle(htmlStyleRes)
                .build()
                .createAppCompat()
    }

    companion object {

        private val KEY_PREFIX = LicensesDialogFragment::class.java.name + '.'

        private val STATE_NOTICES = KEY_PREFIX + "NOTICES"

        fun newInstance(): LicensesDialogFragment {
            return LicensesDialogFragment()
        }
    }
}
