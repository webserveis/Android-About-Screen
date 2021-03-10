package com.webserveis.app.aboutscreen

import android.app.Dialog
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FileTextDialog : DialogFragment() {

    @Suppress("DEPRECATION")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {

            val args = requireArguments()
            val title = args.getString(ARG_TITLE)
            val fileName = args.getString(ARG_KEY_FILE, "")


            val plainContent = requireContext().assets.open(fileName).bufferedReader().use {
                it.readText()
            }

            val contentHTML = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                SpannableString(Html.fromHtml(plainContent, Html.FROM_HTML_MODE_LEGACY))
            } else {
                SpannableString(Html.fromHtml(plainContent))
            }
            Linkify.addLinks(contentHTML, Linkify.ALL);

            val dialog = MaterialAlertDialogBuilder(requireActivity())
                .setTitle(title)
                .setMessage(contentHTML)
                .setNegativeButton(android.R.string.yes) { _dialog, _ -> _dialog?.dismiss() }

            return dialog.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    override fun onStart() {
        super.onStart()
        // Make the dialog's TextView clickable
        val tMsg: TextView? = dialog?.findViewById(android.R.id.message)
        tMsg?.movementMethod = LinkMovementMethod.getInstance()

    }

    companion object {
        private val TAG = FileTextDialog::class.java.simpleName
        const val ARG_TITLE = "ARG_TITLE"
        const val ARG_KEY_FILE = "ARG_KEY_FILE"

        @JvmStatic
        fun newInstance(title: String, fileAsset: String): FileTextDialog {
            val dialog = FileTextDialog()

            val args = Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_KEY_FILE, fileAsset)
            }

            dialog.arguments = args
            return dialog

        }

    }

}