package raj.helpservice.android.helpservice.activity

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import com.kotlinpermissions.KotlinPermissions
import raj.helpservice.android.helpservice.R

import kotlinx.android.synthetic.main.activity_consumer.*
import kotlinx.android.synthetic.main.mobile_or_social_fragment.view.*
import org.angmarch.views.NiceSpinner
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.fragment.ResultFragment
import raj.helpservice.android.helpservice.fragment.SearchFragment
import raj.helpservice.android.helpservice.fragment.consumer.AddressFragment
import raj.helpservice.android.helpservice.fragment.consumer.ConsumerFragment
import raj.helpservice.android.helpservice.fragment.consumer.CreateRequest
import raj.helpservice.android.helpservice.fragment.consumer.PersonalFragment
import raj.helpservice.android.helpservice.spstorage.UserPreference
import android.view.ViewGroup



class ConsumerActivity : AppCompatActivity() ,ResultFragment.ResulFragmentChange {
    override fun changeToSearchFragment() {
        val dialog = Dialog(ConsumerActivity@this)
        dialog.setContentView(R.layout.spinner_dialog)
        dialog.setCancelable(true)
        val window = dialog.window
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)

        val niceSpinner = dialog.findViewById<NiceSpinner>(R.id.city_spinner)
        val pincode:EditText = dialog.findViewById(R.id.input_pincode)
        val changeTextView = dialog.findViewById<TextView>(R.id.change_button)
        val cancelTextView = dialog.findViewById<TextView>(R.id.cancel_button)
        val user = UserPreference.getUser(context = baseContext)
        niceSpinner.attachDataSource(UserPreference.getCities(context = baseContext)?.map { it.cityName })
        niceSpinner.dropDownListPaddingBottom = 500
        pincode.setText(user?.pincode)
        changeTextView.setOnClickListener {dialog.dismiss()  }
        cancelTextView.setOnClickListener { dialog.dismiss() }
        niceSpinner.setOnItemSelectedListener(object :AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        })

        dialog.show()

    }

    val TAG = ConsumerActivity::class.java.name

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigatioView: NavigationView
    lateinit var progressBar : ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer)
        drawerLayout = findViewById(R.id.consumer_activity)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        progressBar = findViewById(R.id.consumer_progress)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigatioView = this.findViewById<NavigationView>(R.id.consumer_navigation_view)
        val header = navigatioView.getHeaderView(0)
        val textName = header.findViewById(R.id.name_registered_user) as TextView
        val userPref = UserPreference.getUser(context = baseContext)
        textName.text = userPref?.name






        navigatioView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.search_service -> {

                    val cityId = UserPreference.getUser(context = baseContext)!!
                    val city : City = UserPreference.getCities(baseContext!!)?.filter { it.cityID == cityId.cityId }!!.single()
                    Log.e("city", city.toString())
                    val profession = Profession("10", "All", "1")
                    replaceFragment( ResultFragment.newInstance(city, profession, userPref?.pincode!!))
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    setActionBarTitle("Search Service")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.create_request -> {
                    replaceFragment(CreateRequest())
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    setActionBarTitle("Create request")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.responses -> {
                    replaceFragment(ConsumerFragment())
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    setActionBarTitle("Responses")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.personal -> {
                    replaceFragment(PersonalFragment())
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    setActionBarTitle("Personal")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.address -> {
                    KotlinPermissions.with(this)
                            .permissions(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
                            .onAccepted{permissons ->
                                replaceFragment(AddressFragment())
                                drawerLayout.closeDrawer(Gravity.LEFT)
                                setActionBarTitle("Address")
                            }
                            .onDenied{permissions ->
                                Toast.makeText(baseContext,"Please switch of location",Toast.LENGTH_SHORT).show()
                            }
                            .ask()

                    return@OnNavigationItemSelectedListener true
                }
                R.id.logout -> {
                    UserPreference.removeUser(context = baseContext)
                    finish()
                    setActionBarTitle("HelpService")
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        })



    }


    override fun onResume() {
        super.onResume()
        showResultForConsumer()
    }

    fun showResultForConsumer() {
        val userPref = UserPreference.getUser(context = baseContext)
        val city: City = UserPreference.getCities(baseContext)!!.filter { it.cityID == userPref?.cityId }.single()
        Log.e("city", city.toString())
        val profession = Profession("10", "All", "1")
        var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.consumer_container)
        if (fragment == null) {
            fragment = ResultFragment.newInstance(city, profession, userPref?.pincode!!)
            supportFragmentManager.beginTransaction()
                    .add(R.id.consumer_container, fragment)
                    .commitAllowingStateLoss()

        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item) || actionBarDrawerToggle.onOptionsItemSelected(item)
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.consumer_container,fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()


    }
}
