package com.dms.imagesearch.ui.base

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.dms.imagesearch.core.utils.ConnectivityReceiver
import com.google.android.material.snackbar.Snackbar.make
import dagger.hilt.android.AndroidEntryPoint

// Easy to switch base activity in future
typealias BaseActivity = DaggerActivity

/**
 * Base activity providing Dagger support and [ViewModel] support
 */
@AndroidEntryPoint
abstract class DaggerActivity : AppCompatActivity(),
    ConnectivityReceiver.ConnectivityReceiverListener {

    var isNetworkStatustoShow: Boolean = false
    var isNetworkConnected:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(
            ConnectivityReceiver(),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }


    protected open fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            isNetworkStatustoShow = true
        }
        isNetworkConnected = isConnected
        getSharedPreferences("imagesdb", Context.MODE_PRIVATE).edit()
            .putBoolean("networkConnectedState", isConnected)
    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this
    }

    /**
     * Callback will be called when there is change
     */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }

}
