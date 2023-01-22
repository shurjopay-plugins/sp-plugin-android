package com.shurjopay.sdk.v2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
@Parcelize
data class ShurjopayConfigs(
    var username: String,
    var password: String,
    var prefix: String,
    var enviornment: String,
    ) : Parcelable
