package hr.algebra.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.android.databinding.ActivitySplashScreenBinding
import hr.algebra.android.framework.*

const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.android.data_imported"
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.ivSplash.startAnimation(R.anim.rotate)
        binding.tvSplash.startAnimation(R.anim.blink)
    }

    private fun redirect() {

        if (getBooleanPreference(DATA_IMPORTED))
            {
                callDelayed(DELAY) {startActivity<HostActivity>()}
            }
        else
            {
                if (isOnline()){

                    Intent(this, AndroidService::class.java).apply {
                        AndroidService.enqueue(
                            this@SplashScreenActivity,
                            this
                        )
                    }
                }
                else {
                    binding.tvSplash.text = getString(R.string.no_internet)
                    callDelayed(DELAY) {finish()}
                }
            }

    }

}