package sp.plugin.android.v2.model

import sp.plugin.android.v2.utils.Constants


/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
class ShurjopayException(resType: Constants.ResponseType?,
                         transactionInfo: sp.plugin.android.v2.model.VerifyResponse?,
                         message: String?, debugMessage: String? = null) {
    var resType: Constants.ResponseType? = null
    var transactionInfo: sp.plugin.android.v2.model.VerifyResponse? = null
    var message: String? = null
    var debugMessage: String? = null
    init {
        this.resType = resType
        this.transactionInfo = transactionInfo
        this.message = message
        this.debugMessage = debugMessage
    }


}