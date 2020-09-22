package com.repository

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.repository.extension.debugLog

/**
 * Broad Receiver to receive the Network Change BroadCast
 */
class NetworkStateReceiver : BroadcastReceiver() {

    companion object {
        const val Action_Network_Change = "com.repository.action.network.change"
    }

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context?, p1: Intent?) {
        p1?.let {
            context?.let {
                debugLog("network state Receiver")
                LocalBroadcastManager.getInstance(it).sendBroadcast(Intent(Action_Network_Change))
            }
        }
    }
}