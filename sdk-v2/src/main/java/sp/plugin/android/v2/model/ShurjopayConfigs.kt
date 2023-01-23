package sp.plugin.android.v2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
@Parcelize
data class ShurjopayConfigs(
    var username: String,
    var password: String,
    var baseUrl: String,
    ) : Parcelable
