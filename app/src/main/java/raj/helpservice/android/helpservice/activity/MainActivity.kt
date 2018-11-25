package raj.helpservice.android.helpservice.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.Worker
import raj.helpservice.android.helpservice.fragment.ErrorFragment
import raj.helpservice.android.helpservice.fragment.ResultFragment
import raj.helpservice.android.helpservice.fragment.SearchFragment
import raj.helpservice.android.helpservice.fragment.consumer.AddressFragment
import raj.helpservice.android.helpservice.fragment.consumer.PersonalFragment
import raj.helpservice.android.helpservice.spstorage.UserPreference
import java.io.IOException

class MainActivity : AppCompatActivity(),SearchFragment.FragmentChangeInterface,ResultFragment.ResulFragmentChange{



    val TAG = "MainActivity"

    lateinit var drawerLayout:DrawerLayout
    lateinit var actionBarDrawerToggle:ActionBarDrawerToggle
    lateinit var navigatioView:NavigationView
    lateinit var mainProgress:ProgressBar

    private val retrofit by lazy {
        HelpServiceApi.getApiService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.activity_main)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mainProgress = findViewById(R.id.main_progress)

        navigatioView = this.findViewById(R.id.main_navigation_view)
        navigatioView .setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { p0 ->
            when(p0.itemId){
                R.id.user_register -> {Log.e(TAG,"register ")
                    val intent = Intent(this,RegisterActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.user_login -> {Log.e(TAG,"login")
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })
//        if (UserPreference.getUser(context = baseContext!!) != null){
//            val intent = Intent(this,ConsumerActivity::class.java)
//            startActivity(intent)
//        }
    }



    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()
        launch(UI) {
            try {
                main_progress.visibility = View.VISIBLE
                val professions: ArrayList<Profession> = retrofit.getProfession().await()
                NetworkUtils.proffessions = professions
                UserPreference.saveProffesions(professions,context = baseContext)
                val cities = retrofit.getCities().await()
                NetworkUtils.cities = cities
                UserPreference.saveCities(cities, baseContext)
                main_progress.visibility = View.GONE
                navigatioView.visibility = View.VISIBLE

                var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.main_container)
                if (fragment == null) {
                    fragment = SearchFragment.newInstance(cities = cities, profession = professions)
                  //  fragment = PersonalFragment()
                    supportFragmentManager.beginTransaction()
                            .add(R.id.main_container, fragment)
                            .commitAllowingStateLoss()
                    Log.i(TAG,"SearchFragment added")
                }
                Log.i(TAG,supportFragmentManager.backStackEntryCount.toString())
            } catch (e: IOException) {
                Log.e(TAG, e.message)
                supportFragmentManager.beginTransaction()
                        .add(R.id.main_container, ErrorFragment())
                        .commitAllowingStateLoss()
            }
        }
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item) || actionBarDrawerToggle.onOptionsItemSelected(item)
    }


    override fun changeToResultFragment(city: City,profession: Profession,pincode:String) {
       supportFragmentManager.beginTransaction()
               .replace(R.id.main_container,ResultFragment.newInstance(city,profession,pincode))
               .commit()
    }

    override fun changeToSearchFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_container,SearchFragment.newInstance(cities = NetworkUtils.cities!!,profession = NetworkUtils.proffessions!!))
                .commit()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if (supportFragmentManager.backStackEntryCount == 0){
                finish()
                return false
            }else{
                supportFragmentManager.popBackStack()
                removeFragment()
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    fun removeFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (currentFragment != null)
            transaction.remove(currentFragment)
        transaction.commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }
}