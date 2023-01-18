package com.shurjopay.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shurjopay.sdk.databinding.ActivityMainBinding

/**
 * Created by @author Moniruzzaman on 10/1/23. github: filelucker
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}
