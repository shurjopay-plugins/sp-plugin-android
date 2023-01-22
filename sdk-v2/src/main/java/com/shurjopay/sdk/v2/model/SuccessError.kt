package com.shurjopay.sdk.v2.model

/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
class SuccessError(esType: ESType?,
                   transactionInfo: VerifyResponse?,
                   message: String?, debugMessage: String? = null) {
    var esType: ESType? = null
    var transactionInfo: VerifyResponse? = null
    var message: String? = null
    var debugMessage: String? = null
    init {
        this.esType = esType
        this.transactionInfo = transactionInfo
        this.message = message
        this.debugMessage = debugMessage
    }

    enum class ESType {
        SUCCESS, ERROR,
        INTERNET_SUCCESS, INTERNET_ERROR,
        INPUT_ERROR,
        HTTP_SUCCESS, HTTP_CANCEL, HTTP_URL_LOAD_ERROR, HTTP_ERROR,
        PAYMENT_CANCEL,
        BACK_BUTTON_PRESSED,
        NONE,
    }
}