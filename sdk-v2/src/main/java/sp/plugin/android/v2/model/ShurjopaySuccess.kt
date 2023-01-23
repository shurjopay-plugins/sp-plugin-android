package sp.plugin.android.v2.model

import sp.plugin.android.v2.utils.Constants


/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
class ShurjopaySuccess(resType: Constants.ResponseType?,
                       transactionInfo: VerifyResponse?,
                       message: String?, debugMessage: String? = null, authenticationResponse: AuthenticationResponse?) {
    var resType: Constants.ResponseType? = null
    var transactionInfo: VerifyResponse? = null
    var message: String? = null
    var debugMessage: String? = null
    var authenticationResponse: AuthenticationResponse? = null
    init {
        this.resType = resType
        this.transactionInfo = transactionInfo
        this.message = message
        this.debugMessage = debugMessage
        this.authenticationResponse = authenticationResponse
    }


}