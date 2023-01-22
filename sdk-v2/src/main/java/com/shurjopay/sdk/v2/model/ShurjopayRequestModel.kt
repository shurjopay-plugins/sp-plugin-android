package com.shurjopay.sdk.v2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 17/1/23. github: filelucker
 */
@Parcelize
data class ShurjopayRequestModel(
    var currency: String,
    var amount: Double,
    var orderId: String,
    var discountAmount: Double?,
    var discPercent: Double?,
    var customerName: String,
    var customerPhone: String,
    var customerEmail: String?,
    var customerAddress: String,
    var customerCity: String,
    var customerState: String?,
    var customerPostcode: String?,
    var customerCountry: String?,
    var returnUrl: String?,
    var cancelUrl: String?,
    var clientIp: String?,
    var value1: String?,
    var value2: String?,
    var value3: String?,
    var value4: String?
) : Parcelable
