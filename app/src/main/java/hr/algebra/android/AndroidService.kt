package hr.algebra.android

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.android.api.BeerFetcher


private const val JOB_ID = 1
class AndroidService : JobIntentService(){

    override fun onHandleWork(intent: Intent) {
        BeerFetcher(this).fetchItems()
    }

    companion object{
        fun enqueue(context: Context, intent: Intent)
            = enqueueWork(context, AndroidService::class.java, JOB_ID, intent)
    }
}

