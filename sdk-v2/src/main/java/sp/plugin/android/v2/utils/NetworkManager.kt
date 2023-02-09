package sp.plugin.android.v2.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import android.text.format.Formatter
import android.text.format.Formatter.formatIpAddress
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*


/**
 *  @author Moniruzzaman. github: filelucker
 */
object NetworkManager {

  private const val TAG = "NetworkManager"

  @RequiresApi(Build.VERSION_CODES.M)
  fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
      if (capabilities != null) {
        when {
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
           Log.i(TAG, "NetworkCapabilities.TRANSPORT_CELLULAR")
            return true
          }
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
            Log.i(TAG, "NetworkCapabilities.TRANSPORT_WIFI")
            return true
          }
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
            Log.i(TAG, "NetworkCapabilities.TRANSPORT_ETHERNET")
            return true
          }
        }
      }
    return false
  }


  fun getLocalIpAddress(): String? {
    try {
      val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
      while (en.hasMoreElements()) {
        val intf: NetworkInterface = en.nextElement()
        val enumIpAddr: Enumeration<InetAddress> = intf.getInetAddresses()
        while (enumIpAddr.hasMoreElements()) {
          val inetAddress: InetAddress = enumIpAddr.nextElement()

          if (!inetAddress.isLoopbackAddress()) {
//            return inetAddress.getHostAddress()
//            return inetAddress.hostAddress
            val ipAddress: String = Formatter.formatIpAddress( inetAddress.hostAddress.toInt())
return  ipAddress;

          }
        }
      }
    } catch (ex: Exception) {
      Log.e("IP Address", ex.toString())
    }
    return null
  }

}

