package com.webserveis.app.aboutscreen

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.FragmentActivity


//fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()
object AboutUtils {
    fun getAppName(context: Context): String {
        return context.applicationInfo.loadLabel(context.packageManager).toString()
    }

    fun getAppVersionName(context: Context): String {
        var version = ""
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return version
    }

    @Suppress("DEPRECATION")
    fun getAppVersionCode(context: Context): Int {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_META_DATA)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                pInfo.longVersionCode.toInt()
            } else {
                pInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            0
        }
    }

    fun showFileTextDialog(activity: FragmentActivity, title: String, fileName: String, tag: String) {
        if (activity.supportFragmentManager.findFragmentByTag(tag) != null) return

        val mDialog = FileTextDialog.newInstance(title, fileName)

        mDialog.show(activity.supportFragmentManager, tag)

    }

    fun openDetailAppSettings(activity: FragmentActivity) {
        val packageName = activity.packageName
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri: Uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }


    fun composeEmail(context: Context, addresses: String, subject: String? = null) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.type = "message/rfc822"
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }
}