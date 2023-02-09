package sp.plugin.android.v2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by @author Moniruzzaman on 17/1/23. github: filelucker
 */
@Parcelize
data class PaymentReq(
    var prefix: String,
    var amount: Double,
    var orderId: String,
    var currency: String,
    var customerName: String,
    var customerAddress: String,
    var customerPhone: String,
    var customerCity: String,
    var customerPostcode: String?,
    var customerEmail: String?

    ) : Parcelable
