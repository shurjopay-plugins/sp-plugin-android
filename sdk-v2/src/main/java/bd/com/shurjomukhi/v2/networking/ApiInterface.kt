package bd.com.shurjomukhi.v2.networking

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import bd.com.shurjomukhi.v2.model.*


/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
interface ApiInterface {

  //////////////////// POST ///////////////////

  @POST("/api/get_token")
  fun getToken(
    @Body token: AuthenticationRequest
  ): Call<AuthenticationResponse>

  @POST("/api/secret-pay")
  fun checkout(
    @Header("Authorization") token: String,
    @Body checkoutRequest: CheckoutRequest
  ): Call<CheckoutResponse>

  @POST("/api/verification")
  fun verify(
    @Header("Authorization") token: String,
    @Body verifyRequest: VerifyRequest
  ): Call<List<VerifyResponse>>

}