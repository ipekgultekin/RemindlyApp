package com.yazilimxyz.remindly.ui.theme

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

fun setAppLocal(context: Context, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = context.resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}