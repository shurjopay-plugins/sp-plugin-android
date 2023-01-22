package com.shurjopay.sdk.v2.payment

import com.shurjopay.sdk.v2.model.SuccessError

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
interface PaymentResultListener {
    fun onSuccess(successError: SuccessError)
    fun onFailed(successError: SuccessError)
    fun onBackButtonListener(successError: SuccessError): Boolean
}