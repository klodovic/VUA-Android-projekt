package hr.algebra.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.algebra.android.databinding.ActivityHostBinding



class HostActivity :  AppCompatActivity() {
    private lateinit var binding: ActivityHostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firstFragment=ItemsFragment()
        val secondFragment=AboutFragment()
        val thirdFragment=MapsFragment()
        setCurrentFragment(firstFragment)

        binding.bottomNav.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_home->setCurrentFragment(firstFragment)
                R.id.nav_about->setCurrentFragment(secondFragment)
                R.id.nav_map->setCurrentFragment(thirdFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return true
    }
*/


/*
    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(getString(R.string.Exit))
            setMessage(getString(R.string.Are_you_sure))
            setIcon(R.drawable.ic_baseline_exit_to_app_24)
            setCancelable(true)
            setNegativeButton(getString(R.string.Cancel), null)
            setPositiveButton(getString(R.string.Ok)) { _, _ -> finish()}
            show()
        }
    }
*/

}