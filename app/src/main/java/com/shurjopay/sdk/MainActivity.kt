package com.shurjopay.sdk

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.shurjopay.sdk.databinding.ActivityMainBinding
import com.shurjopay.sdk.v2.model.SuccessError
import com.shurjopay.sdk.v2.model.ShurjopayConfigs
import com.shurjopay.sdk.v2.model.ShurjopayRequestModel
import com.shurjopay.sdk.v2.payment.PaymentResultListener
import com.shurjopay.sdk.v2.payment.Shurjopay
import com.shurjopay.sdk.v2.utils.Constants.Companion.SDK_TYPE_SANDBOX
import java.util.*

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

pay()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun pay() {
        val data = ShurjopayRequestModel(
            "BDT",
            10.0,
            "NOK" + Random().nextInt(1000000),
            null,
            null,
            "binding.nameLayout.editText?.text.toString()",
            "binding.phoneLayout.editText?.text.toString()",
            null,
            "binding.addressLayout.editText?.text.toString()",
            "binding.cityLayout.editText?.text.toString()",
            null,
            null,
            null,
            "https://www.sandbox.shurjopayment.com/return_url",
            "https://www.sandbox.shurjopayment.com/cancel_url",
            "127.0.0.1",
            "value-of-1",
            "value-of-2",
            "value-of-3",
            "value-of-4"
        )
//        var s = ShurjoPay("dd")

        var config = ShurjopayConfigs(
            username = "sp_sandbox",
            password =  "pyyk97hu&6u6",
            prefix= "sp",
            enviornment = SDK_TYPE_SANDBOX
        )
        val s = Shurjopay(config)
        s.makePayment(
            this,
            data,
            object : PaymentResultListener {
                override fun onSuccess(successError: SuccessError) {
                    Log.d(TAG, "onSuccess: debugMessage = ${successError.debugMessage}")
                }

                override fun onFailed(successError: SuccessError) {
                    Log.d(TAG, "onFailed: debugMessage = ${successError.debugMessage}")
                }

                override fun onBackButtonListener(successError: SuccessError): Boolean {
                    Log.d(TAG, "onBackButton: debugMessage = ${successError.debugMessage}")
                    return true
                }
            })



    }

}
