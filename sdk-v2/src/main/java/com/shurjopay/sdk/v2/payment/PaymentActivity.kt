package com.shurjopay.sdk.v2.payment

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.window.OnBackInvokedDispatcher
import androidx.appcompat.app.AppCompatActivity
import com.shurjopay.sdk.v2.databinding.ActivityPaymentBinding
import com.shurjopay.sdk.v2.model.*
import com.shurjopay.sdk.v2.networking.ApiClient
import com.shurjopay.sdk.v2.networking.ApiInterface
import com.shurjopay.sdk.v2.utils.Constants
import com.shurjopay.sdk.v2.utils.IndeterminateProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var progressDialog: IndeterminateProgressDialog

    private lateinit var sdkType: String
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var data: ShurjopayRequestModel
    private lateinit var config: ShurjopayConfigs
    private var tokenResponse: AuthenticationResponse? = null
    private var checkoutRequest: CheckoutRequest? = null
    private var checkoutResponse: CheckoutResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = IndeterminateProgressDialog(this)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.setCancelable(false)

        if (Build.VERSION.SDK_INT >= 33) {
            data = intent.getParcelableExtra(Constants.DATA, ShurjopayRequestModel::class.java)!!
            config = intent.getParcelableExtra(Constants.CONFIGS, ShurjopayConfigs::class.java)!!
        } else {
            data = intent.getParcelableExtra(Constants.DATA)!!
            config = intent.getParcelableExtra(Constants.CONFIGS)!!
        }

        sdkType = config.SHURJOPAY_API
        username = config.SP_USER
        password = config.SP_PASS


        try {
            getToken()
        } catch (e: Exception) {
            e.printStackTrace()
            hideProgress()
            Shurjopay.listener?.onFailed(
                ShurjopayException(
                    Constants.ResponseType.PAYMENT_CANCEL, null,
                    Constants.USER_INPUT_ERROR,
                )
            )
        }

    }

    private fun getToken() {
        showProgress()

        val authenticationRequest = AuthenticationRequest(username, password)

        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)
            ?.getToken(authenticationRequest)
            ?.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {
                        hideProgress()
                        tokenResponse = response.body()
                        getExecuteUrl()
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    hideProgress()
                    Shurjopay.listener?.onFailed(
                        ShurjopayException(
                            Constants.ResponseType.HTTP_ERROR, null,
                            Constants.PAYMENT_DECLINED,
                        )
                    )
                    finish()
                }
            })
    }


    private fun getExecuteUrl() {
        showProgress()
        checkoutRequest = onExecuteUrlDataBuilder(tokenResponse, data)
        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.checkout(
            tokenResponse?.token_type + " " + tokenResponse?.token,
            checkoutRequest!!
        )?.enqueue(object : Callback<CheckoutResponse> {
            override fun onResponse(
                call: Call<CheckoutResponse>,
                response: Response<CheckoutResponse>
            ) {
                hideProgress()
                if (response.isSuccessful) {
                    checkoutResponse = response.body()
                    setupWebView()
                }
            }

            override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
                hideProgress()
                Shurjopay.listener?.onFailed(
                    ShurjopayException(
                        Constants.ResponseType.HTTP_ERROR, null,
                        Constants.PAYMENT_DECLINED,
                    )
                )
                finish()
            }
        })
    }

    private fun setupWebView() {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.loadUrl(checkoutResponse?.checkout_url.toString())
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                binding.webView.setVisibility(View.GONE);
                if (url.contains(data.cancelUrl.toString())) {
                    Shurjopay.listener?.onFailed(
                        ShurjopayException(
                            Constants.ResponseType.HTTP_ERROR, null,
                            Constants.PAYMENT_CANCELLED,
                        )
                    )
                }

                if (url.contains(data.returnUrl.toString()) && url.contains("order_id")) {
                    verifyPayment()

                }
                finish()
                return false
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                handler?.proceed()
            }
        }
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
//                binding.progressBar.progress = newProgress
            }
        }
    }

    private fun verifyPayment() {
        showProgress()

        val transactionInfo = VerifyRequest(checkoutResponse!!.sp_order_id)

        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.verify(
            tokenResponse?.token_type + " " + tokenResponse?.token,
            transactionInfo
        )?.enqueue(object : Callback<List<VerifyResponse>> {
            override fun onResponse(
                call: Call<List<VerifyResponse>>,
                response: Response<List<VerifyResponse>>
            ) {
                hideProgress()
                if (response.isSuccessful) {
                    if (response.body()?.get(0)?.sp_code == 1000) {
                        Shurjopay.listener?.onSuccess(
                            ShurjopaySuccess(
                                Constants.ResponseType.SUCCESS,
                                response.body()!!.get(0),
                                Constants.PAYMENT_SUCCESSFUL,
                            )
                        )
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<List<VerifyResponse>>, t: Throwable) {
                hideProgress()
                Shurjopay.listener?.onFailed(
                    ShurjopayException(
                        Constants.ResponseType.HTTP_ERROR, null,
                        Constants.PLEASE_CHECK_YOUR_PAYMENT,
                    )
                )
                finish()
            }
        })
    }

    private fun showProgress() {
        progressDialog.show()
    }

    private fun hideProgress() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

//    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
//
//        return super.getOnBackInvokedDispatcher()
//    }

    override fun onBackPressed() {
        Shurjopay.listener?.onBackButtonListener(
            ShurjopayException(
                Constants.ResponseType.BACK_BUTTON_PRESSED, null,
                Constants.PAYMENT_CANCELLED_BY_USER,
            )
        )
        finish()
    }

    companion object {
        private const val TAG = "PaymentActivity"
    }

    private fun onExecuteUrlDataBuilder(
        tokenResponse: AuthenticationResponse?,
        data: ShurjopayRequestModel
    ): CheckoutRequest {
        return CheckoutRequest(
            tokenResponse?.token.toString(),
            tokenResponse?.store_id!!,
            data.prefix,
            data.currency,
            data.returnUrl,
            data.cancelUrl,
            data.amount,
            data.orderId,
            data.discountAmount,
            data.discPercent,
            data.clientIp,
            data.customerName,
            data.customerPhone,
            data.customerEmail,
            data.customerAddress,
            data.customerCity,
            data.customerState,
            data.customerPostcode,
            data.customerCountry,
            data.value1,
            data.value2,
            data.value3,
            data.value4
        )
    }

}
