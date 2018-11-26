package raj.helpservice.android.helpservice.activity

import android.Manifest
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.kotlinpermissions.KotlinPermissions
import raj.helpservice.android.helpservice.R

import kotlinx.android.synthetic.main.activity_vendor.*
import raj.helpservice.android.helpservice.customviews.FragmentHelper
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.fragment.ResultFragment
import raj.helpservice.android.helpservice.fragment.consumer.AddressFragment
import raj.helpservice.android.helpservice.fragment.consumer.ConsumerFragment
import raj.helpservice.android.helpservice.fragment.consumer.CreateRequest
import raj.helpservice.android.helpservice.fragment.consumer.PersonalFragment
import raj.helpservice.android.helpservice.fragment.vendor.*
import raj.helpservice.android.helpservice.spstorage.UserPreference

class VendorActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var navigatioView: NavigationView
    lateinit var progressBar : ProgressBar
    val RESULT_LOAD_IMAGE = 12


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor)

        drawerLayout = findViewById(R.id.vendor_activity)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        progressBar = findViewById(R.id.vendor_progress)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigatioView = this.findViewById<NavigationView>(R.id.vendor_navigation_view)
        val header = navigatioView.getHeaderView(0)
        val textName = header.findViewById(R.id.name_registered_user) as TextView
        val userPref = UserPreference.getUser(context = baseContext)
        textName.text = userPref?.name







        navigatioView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { p0 ->
            when (p0.itemId) {
                R.id.menu_request_list-> {
                    setActionBarTitle("Requests list")
                    replaceFragment(RequestListFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_set_up_service -> {
                    setActionBarTitle("Set-up service")
                    replaceFragment(SetUpFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_rates_of_service -> {
                    setActionBarTitle("Rates of service")
                    replaceFragment(RateFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_vendor_page -> {
                    setActionBarTitle("Vendor's page")
                    replaceFragment(VendorFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_own_request -> {
                    setActionBarTitle("Own requests")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.menu_documents -> {
                    setActionBarTitle("Documents")
                    val tag = UploadFragment.javaClass.getSimpleName()

                    replaceFragment(UploadFragment.newInstance())
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
                            .permissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                            .onAccepted{permissons ->
                                replaceFragment(AddressFragment())
                                drawerLayout.closeDrawer(Gravity.LEFT)
                                setActionBarTitle("Address")
                            }
                            .onDenied{permissions ->
                                Toast.makeText(baseContext,"Please switch of location", Toast.LENGTH_SHORT).show()
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

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun replaceFragment(fragment: Fragment){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.vendor_container,fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()


    }

    override fun onResume() {
        super.onResume()
        showRequestList()
    }


    fun showRequestList() {
        var fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.vendor_container)
        if (fragment == null) {
            fragment = RequestListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .add(R.id.vendor_container, fragment)
                    .commitAllowingStateLoss()

        }
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item) || actionBarDrawerToggle.onOptionsItemSelected(item)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            cursor!!.moveToFirst()

            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()

            val fragment = supportFragmentManager.findFragmentById(R.id.vendor_container) as UploadDocumentFragment
            if (fragment.isVisible) {
                fragment.setFilePath(picturePath)
            }
        }
    }

}
