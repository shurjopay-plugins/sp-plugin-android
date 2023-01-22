package com.shurjopay.sdk.v2.payment

import com.shurjopay.sdk.v2.model.ShurjopayException
import com.shurjopay.sdk.v2.model.ShurjopaySuccess

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
interface PaymentResultListener {
    fun onSuccess(success: ShurjopaySuccess)
    fun onFailed(exception: ShurjopayException)
    fun onBackButtonListener(exception: ShurjopayException): Boolean
}