package com.astro.mynewsapp.utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.astro.mynewsapp.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object NewsAppUtils {

    private var customDialog: AlertDialog? = null

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    fun showCustomDialog(context: Context, title: String, message: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog, null)

        val titleTextView = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val parentLayout = dialogView.findViewById<LinearLayout>(R.id.parentLayout)
        titleTextView.text = title
        messageTextView.text = message

        parentLayout.setOnClickListener {
            customDialog?.dismiss()
        }

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .create()
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()
        customDialog = dialog
    }

    fun dismissCustomDialog() {
        customDialog?.dismiss()
        customDialog = null
    }

    fun getTimeAgo(isoDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return try {
            val past = sdf.parse(isoDate)?.time ?: return "Invalid date"
            val now = System.currentTimeMillis()
            val diff = now - past

            val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val days = TimeUnit.MILLISECONDS.toDays(diff)

            when {
                seconds < 60 -> "Just now"
                minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
                hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
                days == 1L -> "Yesterday"
                days < 7 -> "$days days ago"
                days < 30 -> "${days / 7} week${if (days / 7 > 1) "s" else ""} ago"
                else -> {
                    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                    dateFormat.format(Date(past))
                }
            }
        } catch (e: Exception) {
            "Invalid date"
        }
    }
    fun extractName(trimmedRaw: String): String {
        // If the string contains '/', try to get the last segment
        if (trimmedRaw.contains("/")) {
            val lastSegment = trimmedRaw.substringAfterLast("/").trim()
            if (lastSegment.isNotEmpty()) return lastSegment
        }

        // Try to extract from brackets: (something)
        val bracketRegex = "\\((.*?)\\)".toRegex()
        val bracketMatch = bracketRegex.find(trimmedRaw)
        if (bracketMatch != null) {
            return bracketMatch.groupValues[1].trim().split(" ").firstOrNull() ?: ""
        }

        // Try to split by comma and get first word
        val commaSplit = trimmedRaw.split(",").firstOrNull()?.trim()
        if (!commaSplit.isNullOrEmpty()) {
            return commaSplit.split(" ").firstOrNull() ?: ""
        }

        // Default: get first word
        return trimmedRaw.split(" ").firstOrNull() ?: ""
    }

    fun getShortenedTitle(title: String): String {
        val words = title.split(" ")
        return if (words.size > 4) {
            words.take(4).joinToString(" ") + "..."
        } else {
            title
        }
    }

}