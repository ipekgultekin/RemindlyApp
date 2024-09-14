package com.yazilimxyz.remindly.ui.theme

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.Locale

fun setAppLocal(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    config.setLocale(locale)

    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.createConfigurationContext(config)
    }
}