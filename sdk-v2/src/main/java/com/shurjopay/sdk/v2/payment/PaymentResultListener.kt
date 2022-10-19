package com.shurjopay.sdk.v2.payment

import com.shurjopay.sdk.v2.model.ErrorSuccess

/**
 * Payment Result Listener interface
 *
 * @author  Rz Rasel
 * @since   2021-08-07
 */
interface PaymentResultListener {
    fun onSuccess(errorSuccess: ErrorSuccess)
    fun onFailed(errorSuccess: ErrorSuccess)
    fun onBackButtonListener(errorSuccess: ErrorSuccess): Boolean
}