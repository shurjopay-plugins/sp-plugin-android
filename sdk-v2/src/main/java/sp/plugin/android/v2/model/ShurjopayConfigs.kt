package sp.plugin.android.v2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 22/1/23. github: filelucker
 */
@Parcelize
data class ShurjopayConfigs(
    var SP_USER: String,
    var SP_PASS: String,
    var SHURJOPAY_API: String,
    ) : Parcelable
