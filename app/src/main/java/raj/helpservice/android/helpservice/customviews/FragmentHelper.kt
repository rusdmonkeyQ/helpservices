package raj.helpservice.android.helpservice.customviews

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import raj.helpservice.android.helpservice.R

class FragmentHelper{
    companion object {
        fun add(activity: FragmentActivity, @IdRes id: Int, fragment: Fragment) {
            add(activity, id, fragment, false)
        }

        fun add(activity: FragmentActivity, @IdRes id: Int, fragment: Fragment, addToBackStack: Boolean) {
            val tag = fragment.javaClass.simpleName
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.add(id, fragment, tag)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag)
            }
            fragmentTransaction.commit()
        }

        fun replace(activity: FragmentActivity, @IdRes id: Int, fragment: Fragment) {
            replace(activity, id, fragment, false, false)
        }

        fun replace(activity: FragmentActivity, @IdRes id: Int, fragment: Fragment, addToBackStack: Boolean) {
            replace(activity, id, fragment, addToBackStack, false)
        }

        fun replace(activity: FragmentActivity, @IdRes id: Int, fragment: Fragment, addToBackStack: Boolean, animate: Boolean) {
            val tag = fragment.javaClass.simpleName
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            if (animate) {
                fragmentTransaction.setCustomAnimations(R.anim.open_enter, R.anim.open_exit, R.anim.close_enter, R.anim.close_exit)
            }
            fragmentTransaction.replace(id, fragment, tag)
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(tag)
            }
            fragmentTransaction.commit()
        }
    }
}