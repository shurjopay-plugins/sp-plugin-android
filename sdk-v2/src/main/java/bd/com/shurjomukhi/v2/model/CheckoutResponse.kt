package bd.com.shurjomukhi.v2.model

/**
 * Checkout Response data class
 *
 * @author  Rz Rasel
 * @since   2021-08-07
 */
data class CheckoutResponse(
    var checkout_url: String,
    var amount: Int,
    var currency: String,
    var sp_order_id: String,
    var customer_order_id: String,
    var customer_name: String,
    var customer_address: String,
    var customer_city: String,
    var customer_phone: String,
    var customer_email: String,
    var client_ip: String,
    var intent: String,
    var transactionStatus: String
)
