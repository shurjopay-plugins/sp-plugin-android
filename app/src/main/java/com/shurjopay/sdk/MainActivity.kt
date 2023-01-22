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

    }

}
