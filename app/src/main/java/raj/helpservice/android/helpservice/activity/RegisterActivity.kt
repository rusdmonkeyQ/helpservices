package raj.helpservice.android.helpservice.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.adapter.RegisterFragmentAdapter
import raj.helpservice.android.helpservice.customviews.NotSwipeable
import raj.helpservice.android.helpservice.fragment.register_fragment.*

class RegisterActivity : AppCompatActivity() ,MobileOrSocialFragment.ChangeFragment,OtpFragment.OnFragmentInteractionListener,ChooseCVFragment.OnFragmentInteractionListener,VendorFragment.VendorNextFragment,RegistrationServiceFragment.FinishRegistration,FinishRegistration.OnFragmentInteractionListener{
    override fun changeToNextItem() {
        nextItem()
    }

    override fun onFragmentInteraction() {

    }


    override fun finishRegistration() {
        nextItem()
    }

    override fun changeFromVendorToLast() {
        nextItem()
    }

    override fun changeToValidate() {
        nextItem()
    }


    override fun changeToValidateOtP() {
        nextItem()
    }

    lateinit var adapter: RegisterFragmentAdapter
    lateinit var viewPager: NotSwipeable


    override fun changeToVendorOrConsumer(isConsumer:Boolean) {
        if (isConsumer) {
            adapter.removeItem(4)
            adapter.notifyDataSetChanged()
        }
        nextItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewPager = findViewById(R.id.registration_page)
        adapter = RegisterFragmentAdapter(supportFragmentManager)
        adapter.addItem(MobileOrSocialFragment.newInstance(),0)
        adapter.addItem(OtpFragment.newInstance(),1)
        adapter.addItem(ChooseCVFragment.newInstance(),2)
        adapter.addItem(VendorFragment(),3)
        adapter.addItem(RegistrationServiceFragment.newInstance(),4)
        adapter.addItem(FinishRegistration.newInstance(),5)
        viewPager.adapter = adapter
    }




    fun nextItem(){
        viewPager.currentItem = viewPager.currentItem +1
    }
}
