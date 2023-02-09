package sp.plugin.android

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import sp.plugin.android.databinding.ActivityMainBinding
import sp.plugin.android.v2.model.ShurjopayConfigs
import sp.plugin.android.v2.model.ShurjopayException
import sp.plugin.android.v2.model.PaymentReq
import sp.plugin.android.v2.model.ShurjopaySuccess
import sp.plugin.android.v2.payment.PaymentResultListener
import sp.plugin.android.v2.payment.Shurjopay
import java.util.*

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    var shurjopay: Shurjopay? = null

//     val SP_USER = "sp_sandbox"
//     val SP_PASS = "pyyk97hu&6u6"
//     val SHURJOPAY_API = "https://sandbox.shurjopayment.com"

    val SP_USER = "shurjopay"
    val SP_PASS = "pyyk97hu&6u625"
    val SHURJOPAY_API = "https://www.dev.engine.shurjopayment.com"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shurjopay = Shurjopay(
            ShurjopayConfigs(
                username = SP_USER,
                password = SP_PASS,
                baseUrl = SHURJOPAY_API
            )
        )


        pay()
//        checkStatus("sp63e4cbf38abab")

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun pay() {

        val data = PaymentReq(
            "sp",
            1.00,
            "NOK" + Random().nextInt(1000000),
            "BDT",
            "moniruzzaman",
            "Dhaka",
            "01721915013",
            "Dhaka", "1200",
            "m.zaman000@gmail.com",
        )

        shurjopay?.makePayment(
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

    // this method only needed if user choose to use token for other shurjopay operation
    @RequiresApi(Build.VERSION_CODES.M)
    private fun token() {

        shurjopay?.getToken(this, object : PaymentResultListener {
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkStatus(oorder_id: String) {

        shurjopay?.verifyPayment(this,
            oorder_id,
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
