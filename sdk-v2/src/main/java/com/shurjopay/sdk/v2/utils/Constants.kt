package com.shurjopay.sdk.v2.utils

import androidx.annotation.Keep

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
@Keep class Constants {
  @Keep companion object {
    const val USER_INPUT_ERROR = "User input error!"
    const val PAYMENT_CANCELLED = "Payment Cancelled!"
    const val PAYMENT_CANCELLED_BY_USER = "Payment Cancelled By User!"
    const val PAYMENT_DECLINED = "Payment has been declined from gateway!"
    const val PAYMENT_SUCCESSFUL = "Payment successfully done"
    const val PLEASE_CHECK_YOUR_PAYMENT = "Please Check Your Payment"
    const val BANK_TRANSACTION_FAILED = "Bank transaction failed!"
    const val NO_INTERNET_PERMISSION = "No internet permission is given!"
    const val NO_NETWORK_STATE_PERMISSION = "No network state permission is given!"
    const val NO_INTERNET_MESSAGE = "No internet connection! Please check your connection settings."
    const val NO_USER_CREDENTIAL = "User credentials not found"
    const val INVALID_AMOUNT = "Invalid amount!"
    const val DATA = "data"
    const val CONFIGS = "configs"
  }

  enum class ResponseType {
    SUCCESS, ERROR,
    INTERNET_SUCCESS, INTERNET_ERROR,
    INPUT_ERROR,
    HTTP_SUCCESS, HTTP_CANCEL, HTTP_URL_LOAD_ERROR, HTTP_ERROR,
    PAYMENT_CANCEL,
    BACK_BUTTON_PRESSED,
    NONE,
  }
}