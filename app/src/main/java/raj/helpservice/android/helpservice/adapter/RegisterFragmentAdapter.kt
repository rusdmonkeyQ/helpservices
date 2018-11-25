package raj.helpservice.android.helpservice.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import raj.helpservice.android.helpservice.fragment.register_fragment.*

class RegisterFragmentAdapter(manager:FragmentManager) : FragmentPagerAdapter(manager){

    private val list = ArrayList<Fragment>()
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list[position]
    }


    fun removeItem(position:Int){
        list.removeAt(position)
    }

    fun addItem(fragment: Fragment,position: Int){
        list.add(position,fragment)
    }


}