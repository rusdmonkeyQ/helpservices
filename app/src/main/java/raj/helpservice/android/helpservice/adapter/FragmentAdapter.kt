package raj.helpservice.android.helpservice.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.Spannable
import android.text.style.ImageSpan
import android.text.SpannableString
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import raj.helpservice.android.helpservice.R


class FragmentAdapter(var supportFragmentManager: FragmentManager,val context:Context) : FragmentPagerAdapter(supportFragmentManager) {

    val fragmentList:ArrayList<Fragment> = ArrayList()
    val mFragmentTitleList:ArrayList<String> = ArrayList()




    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
      return fragmentList[position]
    }

    fun addFragment(fragment:Fragment,title:String){
        fragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val imageOriginal = ContextCompat.getDrawable(context,getDrawable(mFragmentTitleList[position]))
        val imageCopy = imageOriginal?.constantState!!.newDrawable().mutate()
        imageCopy.setBounds(0, 0, 80, 80)
        imageCopy.setColorFilter(context.resources.getColor(android.R.color.white),PorterDuff.Mode.SRC_ATOP)
        // Replace blank spaces with image icon
        val sb = SpannableString("   " + mFragmentTitleList[position])
        val imageSpan = ImageSpan(imageCopy, ImageSpan.ALIGN_BOTTOM)
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return sb
    }


    private fun getDrawable(profession: String):Int{
        when(profession){
            "Plumber"-> return R.drawable.ic_plumbing
            "Electrician" -> return R.drawable.ic_electrical
            "Painter" ->return R.drawable.ic_painting
            "Carpenter" -> return R.drawable.ic_carpenter
            "Mason" -> return R.drawable.ic_mason
        }
     return R.drawable.ic_user_login

    }

}