package com.shurjopay.sdk.v2.utils

import android.content.Context

/**
 * Created by @author Moniruzzaman on 17/1/23. github: filelucker
 */
class AppResourse {
    fun getString(type: String, context: Context): String {
        try {
            var string = context.resources.getString(
                context.resources.getIdentifier(
                    type,
                    Constants.DEF_TYPE, context.getPackageName()
                )
            )
            return string
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }
}