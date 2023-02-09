package bd.com.shurjomukhi.v2.payment

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
interface PaymentResultListener {
    fun onSuccess(success: bd.com.shurjomukhi.v2.model.ShurjopaySuccess)
    fun onFailed(exception: bd.com.shurjomukhi.v2.model.ShurjopayException)
    fun onBackButtonListener(exception: bd.com.shurjomukhi.v2.model.ShurjopayException): Boolean
}