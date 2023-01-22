package sp.plugin.android.v2.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import sp.plugin.android.v2.model.*

/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
interface ApiInterface {

  //////////////////// POST ///////////////////

  @POST("get_token")
  fun getToken(
    @Body token: AuthenticationRequest
  ): Call<AuthenticationResponse>

  @POST("secret-pay")
  fun checkout(
    @Header("Authorization") token: String,
    @Body checkoutRequest: CheckoutRequest
  ): Call<CheckoutResponse>

  @POST("verification")
  fun verify(
    @Header("Authorization") token: String,
    @Body verifyRequest: VerifyRequest
  ): Call<List<VerifyResponse>>
}