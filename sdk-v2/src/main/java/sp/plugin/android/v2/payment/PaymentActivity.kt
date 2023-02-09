package sp.plugin.android.v2.payment

import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sp.plugin.android.v2.databinding.ActivityPaymentBinding
import sp.plugin.android.v2.model.*
import sp.plugin.android.v2.networking.ApiClient
import sp.plugin.android.v2.networking.ApiInterface
import sp.plugin.android.v2.payment.Shurjopay.Companion.listener
import sp.plugin.android.v2.utils.Constants
import sp.plugin.android.v2.utils.IndeterminateProgressDialog
import sp.plugin.android.v2.utils.NetworkManager

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding

    private lateinit var progressDialog: IndeterminateProgressDialog

    private lateinit var sdkType: String
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var data: PaymentReq
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
            data = intent.getParcelableExtra(Constants.DATA, PaymentReq::class.java)!!
            config = intent.getParcelableExtra(Constants.CONFIGS, ShurjopayConfigs::class.java)!!
        } else {
            data = intent.getParcelableExtra(Constants.DATA)!!
            config = intent.getParcelableExtra(Constants.CONFIGS)!!
        }

        sdkType = config.baseUrl
        username = config.username
        password = config.password


        try {
            getToken(username, password, sdkType, false)
        } catch (e: Exception) {
            e.printStackTrace()
            hideProgress()
            listener?.onFailed(
                ShurjopayException(
                    Constants.ResponseType.PAYMENT_CANCEL, null,
                    Constants.USER_INPUT_ERROR,
                )
            )
        }

    }

    fun getToken(username: String, password: String, sdkType: String, onlyToken: Boolean) {
        if (!onlyToken) {
            showProgress()
        }

        val authenticationRequest = AuthenticationRequest(username, password)

        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)
            ?.getToken(authenticationRequest)?.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {

                        if (onlyToken) {
                            listener?.onSuccess(
                                ShurjopaySuccess(
                                    Constants.ResponseType.SUCCESS, null,
                                    null,
                                    Constants.USER_CREDENTIAL_DELIVERED, response.body()
                                )
                            )
                        } else {
                            hideProgress()
                            tokenResponse = response.body()
                            getExecuteUrl()
                        }
                    }
                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    hideProgress()
                    listener?.onFailed(
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
        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)
            ?.checkout(
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
                    listener?.onFailed(
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
//                binding.webView.setVisibility(View.GONE);
                if (url.contains(checkoutRequest?.cancel_url.toString())) {
                    hideProgress()
                    listener?.onFailed(
                        ShurjopayException(
                            Constants.ResponseType.HTTP_ERROR, null,
                            Constants.PAYMENT_CANCELLED,
                        )
                    )
                    finish()
                }

                if (url.contains(checkoutRequest?.return_url.toString()) && url.contains("order_id")) {
                    verifyPayment()
                }

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

        val transactionInfo = VerifyRequest(checkoutResponse!!.sp_order_id)

        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.verify(
            tokenResponse?.token_type + " " + tokenResponse?.token,
            transactionInfo
        )?.enqueue(object : Callback<VerifyResponse> {
            override fun onResponse(
                call: Call<VerifyResponse>,
                response: Response<VerifyResponse>
            ) {

                if (response.isSuccessful) {
                    hideProgress()
                    if (response.body()?.sp_code == 1000) {

                        listener?.onSuccess(
                            ShurjopaySuccess(
                                Constants.ResponseType.SUCCESS,
                                response.body()!!,
                                Constants.PAYMENT_SUCCESSFUL, null, null
                            )
                        )

                    } else {
                        listener?.onFailed(
                            ShurjopayException(
                                Constants.ResponseType.PAYMENT_CANCEL, null,
                                Constants.PAYMENT_CANCELLED_BY_USER,
                            )
                        )
                    }

                }
                else{
                    listener?.onFailed(
                        ShurjopayException(
                            Constants.ResponseType.PAYMENT_CANCEL, null,
                            Constants.SERVER_ERROR,
                        )
                    )
                }
                finish()
            }

            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                hideProgress()
                listener?.onFailed(
                    ShurjopayException(
                        Constants.ResponseType.HTTP_ERROR, null,
                        Constants.PLEASE_CHECK_YOUR_PAYMENT,
                    )
                )
                finish()
            }
        })
    }

    fun verifyPayment(username:String, password:String, sdkType:String, sp_order_id: String) {


        val authenticationRequest = AuthenticationRequest(username, password)

        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)
            ?.getToken(authenticationRequest)?.enqueue(object : Callback<AuthenticationResponse> {
                override fun onResponse(
                    call: Call<AuthenticationResponse>,
                    response: Response<AuthenticationResponse>
                ) {
                    if (response.isSuccessful) {
                        val transactionInfo = VerifyRequest(sp_order_id)
                        // verify payment start
                        ApiClient().getApiClient(sdkType)?.create(ApiInterface::class.java)?.verify(
                            response.body()?.token_type + " " + response.body()?.token,
                            transactionInfo
                        )?.enqueue(object : Callback<VerifyResponse> {
                            override fun onResponse(
                                call2: Call<VerifyResponse>,
                                response2: Response<VerifyResponse>
                            ) {

                                if (response2.isSuccessful) {
                                    if (response2.body()?.sp_code == 1000) {

                                        listener?.onSuccess(
                                            ShurjopaySuccess(
                                                Constants.ResponseType.SUCCESS,
                                                response2.body()!!,
                                                Constants.PAYMENT_SUCCESSFUL, null, null
                                            )
                                        )

                                    } else {
                                        listener?.onFailed(
                                            ShurjopayException(
                                                Constants.ResponseType.ERROR, null,
                                                response2.body()?.message,
                                            )
                                        )
                                    }

                                } else {
                                    listener?.onFailed(
                                        ShurjopayException(
                                            Constants.ResponseType.ERROR, null,
                                            response2.body()?.message,
                                        )
                                    )
                                }
                            }

                            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {

                                listener?.onFailed(
                                    ShurjopayException(
                                        Constants.ResponseType.HTTP_ERROR, null,
                                        Constants.PLEASE_CHECK_YOUR_PAYMENT,
                                    )
                                )

                            }
                        })
                        //////// end


                    } else {
                        listener?.onFailed(
                            ShurjopayException(
                                Constants.ResponseType.HTTP_ERROR, null,
                                response.message(),
                            )
                        )
                    }

                }

                override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                    listener?.onFailed(
                        ShurjopayException(
                            Constants.ResponseType.HTTP_ERROR, null,
                            Constants.PLEASE_CHECK_YOUR_PAYMENT,
                        )
                    )

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


    override fun onBackPressed() {
        listener?.onBackButtonListener(
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
        data: PaymentReq
    ): CheckoutRequest {
        return CheckoutRequest(
            tokenResponse?.token.toString(),
            tokenResponse?.store_id!!,
            data.prefix,
            data.currency,
            sdkType+"/response",
            sdkType+"/response",
            data.amount,
            data.orderId,
            0.0,
            0.0,
            "127.0.0.1",
//            NetworkManager.getLocalIpAddress(),
            data.customerName,
            data.customerPhone,
            data.customerEmail,
            data.customerAddress,
            data.customerCity,
            "",
            data.customerPostcode,
            "",
            "",
            "",
            "",
            ""
        )
    }

}
