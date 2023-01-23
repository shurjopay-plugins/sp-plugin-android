package sp.plugin.android

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import sp.plugin.android.databinding.ActivityMainBinding
import sp.plugin.android.v2.model.ShurjopayConfigs
import sp.plugin.android.v2.model.ShurjopayException
import sp.plugin.android.v2.model.ShurjopayRequestModel
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
