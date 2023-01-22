package sp.plugin.android.v2.networking

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Api Client class
 *
 * @author  Rz Rasel
 * @since   2021-08-07
 */
class ApiClient {
  private var retrofit: Retrofit? = null

  fun getApiClient(sdkType: String): Retrofit? {
    if (retrofit == null) {
      val gson = GsonBuilder().setLenient().create()

//      var baseUrl = Constants.BASE_URL_SANDBOX
//      when {
//        sdkType.equals(Constants.SDK_TYPE_SANDBOX, ignoreCase = true) -> {
//          baseUrl = Constants.BASE_URL_SANDBOX
//        }
//        sdkType.equals(Constants.SDK_TYPE_LIVE, ignoreCase = true) -> {
//          baseUrl = Constants.BASE_URL_LIVE
//        }
//        sdkType.equals(Constants.SDK_TYPE_IPN_SANDBOX, ignoreCase = true) -> {
//          baseUrl = Constants.BASE_URL_IPN_SANDBOX
//        }
//        sdkType.equals(Constants.SDK_TYPE_IPN_LIVE, ignoreCase = true) -> {
//          baseUrl = Constants.BASE_URL_IPN_LIVE
//        }
//      }

      retrofit = Retrofit.Builder()
        .baseUrl(sdkType)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    }
    return retrofit
  }
}
