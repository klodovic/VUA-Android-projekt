package hr.algebra.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.android.framework.startActivity

class AndroidReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity<HostActivity>()
    }
}