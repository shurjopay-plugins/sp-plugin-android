package sp.plugin.android.v2.payment

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import sp.plugin.android.v2.utils.Constants
import sp.plugin.android.v2.utils.NetworkManager.isInternetAvailable

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class Shurjopay constructor(configs: sp.plugin.android.v2.model.ShurjopayConfigs) {

    var configuration = configs;

    @RequiresApi(Build.VERSION_CODES.M)
    fun makePayment(
        context: Context,
        data: sp.plugin.android.v2.model.ShurjopayRequestModel?,
        resultListener: PaymentResultListener?
    ) {

        listener = resultListener

        if (configuration == null) {
            listener!!.onFailed(
                sp.plugin.android.v2.model.ShurjopayException(
                    Constants.ResponseType.ERROR, null,
                    Constants.NO_USER_CREDENTIAL
                )
            )
        } else {

            if (!isInternetAvailable(context)) {
                listener!!.onFailed(
                    sp.plugin.android.v2.model.ShurjopayException(
                        Constants.ResponseType.ERROR, null,
                        Constants.NO_INTERNET_MESSAGE
                    )
                )
                return
            }
            val intent = Intent(context, sp.plugin.android.v2.payment.PaymentActivity::class.java)
            intent.putExtra(Constants.DATA, data)
            intent.putExtra(Constants.CONFIGS, configuration);
            context.startActivity(intent)
        }
    }


    companion object {
        var listener: PaymentResultListener? = null
    }

}