package bd.com.shurjomukhi.v2.payment

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import bd.com.shurjomukhi.v2.model.ShurjopayConfigs
import bd.com.shurjomukhi.v2.model.ShurjopayException
import bd.com.shurjomukhi.v2.model.PaymentReq
import bd.com.shurjomukhi.v2.utils.Constants
import bd.com.shurjomukhi.v2.utils.NetworkManager.isInternetAvailable

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class Shurjopay constructor(configs: ShurjopayConfigs) {

    var configuration = configs;

    @RequiresApi(Build.VERSION_CODES.M)
    fun makePayment(
        context: Context,
        data: PaymentReq?,
        resultListener: PaymentResultListener?
    ) {

        listener = resultListener

        if (configuration == null) {
            listener!!.onFailed(
                ShurjopayException(
                    Constants.ResponseType.ERROR, null,
                    Constants.NO_USER_CREDENTIAL
                )
            )
        } else {

            if (!isInternetAvailable(context)) {
                listener!!.onFailed(
                    ShurjopayException(
                        Constants.ResponseType.ERROR, null,
                        Constants.NO_INTERNET_MESSAGE
                    )
                )
                return
            }
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(Constants.DATA, data)
            intent.putExtra(Constants.CONFIGS, configuration);
            context.startActivity(intent)
        }
    }


    companion object {
        var listener: PaymentResultListener? = null
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun getToken(
        context: Context,
        resultListener: PaymentResultListener?
    ) {

        listener = resultListener

        if (!isInternetAvailable(context)) {
            listener!!.onFailed(
                ShurjopayException(
                    Constants.ResponseType.ERROR, null,
                    Constants.NO_INTERNET_MESSAGE
                )
            )
            return
        }
        PaymentActivity().getToken(configuration.username, configuration.password, configuration.baseUrl, true)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun verifyPayment(
        context: Context,
        spOrderId: String,
        resultListener: PaymentResultListener?
    ) {

        listener = resultListener

        if (!isInternetAvailable(context)) {
            listener!!.onFailed(
                ShurjopayException(
                    Constants.ResponseType.ERROR, null,
                    Constants.NO_INTERNET_MESSAGE
                )
            )
            return
        }
        PaymentActivity().verifyPayment(configuration.username, configuration.password, configuration.baseUrl, spOrderId)

    }

}