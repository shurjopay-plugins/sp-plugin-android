package sp.plugin.android.v2.model

/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
data class AuthenticationResponse (
    var token: String?,
    var store_id: Int?,
    var execute_url: String?,
    var token_type: String?,
    var sp_code: Int?,
    var massage: String?,
    var tokenCreateTime: String?,
    var expires_in: String?
)
