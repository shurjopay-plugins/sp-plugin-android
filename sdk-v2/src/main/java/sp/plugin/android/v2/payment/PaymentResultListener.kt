package sp.plugin.android.v2.payment

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
interface PaymentResultListener {
    fun onSuccess(success: sp.plugin.android.v2.model.ShurjopaySuccess)
    fun onFailed(exception: sp.plugin.android.v2.model.ShurjopayException)
    fun onBackButtonListener(exception: sp.plugin.android.v2.model.ShurjopayException): Boolean
}