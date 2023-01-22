package com.shurjopay.sdk

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.shurjopay.sdk.databinding.ActivityMainBinding
import com.shurjopay.sdk.v2.model.ShurjopaySuccess
import com.shurjopay.sdk.v2.model.ShurjopayConfigs
import com.shurjopay.sdk.v2.model.ShurjopayException
import com.shurjopay.sdk.v2.model.ShurjopayRequestModel
import com.shurjopay.sdk.v2.payment.PaymentResultListener
import com.shurjopay.sdk.v2.payment.Shurjopay
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
            "sp",
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
            SP_USER = "sp_sandbox",
            SP_PASS =  "pyyk97hu&6u6",
            SHURJOPAY_API = "https://sandbox.shurjopayment.com/api/"
        )
        val s = Shurjopay(config)
        s.makePayment(
            this,
            data,
            object : PaymentResultListener {
                override fun onSuccess(success: ShurjopaySuccess) {
                    Log.d(TAG, "onSuccess: debugMessage = ${success.debugMessage}")
                }

                override fun onFailed(exception: ShurjopayException) {
                    Log.d(TAG, "onFailed: debugMessage = ${exception.debugMessage}")
                }

                override fun onBackButtonListener(exception: ShurjopayException): Boolean {
                    Log.d(TAG, "onBackButton: debugMessage = ${exception.debugMessage}")
                    return true
                }

            })



    }

}
