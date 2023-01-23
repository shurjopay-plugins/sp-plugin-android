# ![image](https://user-images.githubusercontent.com/57352037/155895117-523cfb9e-d895-47bf-a962-2bcdda49ad66.png) Android plugin

Official shurjoPay Android plugin for merchants or service providers to connect with [**_shurjoPay_**](https://shurjopay.com.bd) Payment Gateway developed and maintained by [_**ShurjoMukhi Limited**_](https://shurjomukhi.com.bd).

This plugin can be used with any Android application (e.g. Kotlin, Java).

1. **makePayment**: create and send payment request
2. **getToken**: to get shurjoPay authorization token for future use

Also reduces many of the things that you had to do manually:

- Handles http requests and errors.
- JSON serialization and deserialization.
- Authentication during initiating and verifying of payments.

## Audience
This document is intended for the technical personnel of merchants and service providers who wants to integrate our online payment gateway for Android application using kotlin plugin provided by _**shurjoMukhi Limited**_.

# How to configure and use this shurjoPay plugin
To integrate the shurjoPay Payment Gateway in your Android project do the following tasks sequentially.

```gradle
android {
    ...
    compileSdk 33

    defaultConfig {
        ...
        targetSdk 33
        ...
    }
```
```compileSdk 33``` and ```targetSdk 33``` required api 33

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```gradel
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
 add this to ```build.gradle (Module: app)``` or if you are using "Android Studio Arctic Fox" or heigher add this to ```settings.gradle (Project Settings)```
  
  Step 2. Add the dependency
```gradel
	dependencies {
		...
	        implementation 'com.github.filelucker:test-plugin:v1.2.1'     // TODO: Need to change this
		...
	}
  ```
  
  Step 3. Add these constraints(SP_USER, SP_PASS and SHURJOPAY_API) to configure Shurjopay instance
  
  ```kotlin
        shurjopay = Shurjopay(
            ShurjopayConfigs(
                username = SP_USER,
                password = SP_PASS,
                baseUrl = SHURJOPAY_API
            )
        )    
  ```
  
# Android AndroidManifest

```git_android_manifest_xml
<uses-permission android:name="android.permission.INTERNET"/>
```

# Request Data Model Setup:

```kotlin
// TODO request data model setup
val data = ShurjopayRequestModel(
    prefix,
    currency,
    amount,
    orderId,
    discountAmount,
    discPercent,
    customerName,
    customerPhone,
    customerEmail,
    customerAddress,
    customerCity,
    customerState,
    customerPostcode,
    customerCountry,
    returnUrl,
    cancelUrl,
    clientIp,
    value1,
    value2,
    value3,
    value4
)
```

# Payment Request Setup:

```kotlin
// TODO payment request setup
      shurjopay?.makePayment(this, data,
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
```


### That's all! Now you are ready to use the python plugin to seamlessly integrate with shurjoPay to make your payment system easy and smooth.

# References

1. [Sample applications and projects](https://github.com/shurjopay-plugins/sp-plugin-usage-examples) in many different languages and frameworks showing 
2. [shurjopay Plugins](https://github.com/shurjopay-plugins) home page on github

## License
This code is under the [MIT open source License](LICENSE).
#### Please [contact](https://shurjopay.com.bd/#contacts) with shurjoPay team for more detail.
### Copyright ©️2023 [ShurjoMukhi Limited](https://shurjopay.com.bd/)
  
