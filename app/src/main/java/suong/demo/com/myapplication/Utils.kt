package suong.demo.com.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by Thu Suong on 8/22/2017.
 */
object Utils {

    fun isNetworkConnected (context:Context):Boolean {
var connectivityManager:ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info:NetworkInfo = connectivityManager.activeNetworkInfo
        return info.isConnected|| info.isConnectedOrConnecting
    }
}