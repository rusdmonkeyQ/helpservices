package raj.helpservice.android.helpservice.customviews

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.text.Charsets
import com.google.android.gms.common.util.IOUtils.toByteArray
import java.io.ByteArrayOutputStream
import java.io.PrintWriter


class NotSwipeable :ViewPager {

    var swipeable: Boolean = false

    constructor(context: Context,attributeSet: AttributeSet):super(context,attributeSet)


    constructor(context: Context):super(context)



    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (swipeable) {
            return super.onTouchEvent(ev)
        }

        return false
    }

    fun setSwipeableEnabled(swipeable: Boolean){
        this.swipeable = swipeable
    }

    fun getActiveFragment(fragmentManager: FragmentManager, position: Int): Fragment {
        val name = makeFragmentName(id, position)
        val fragmentByTag = fragmentManager.findFragmentByTag(name)
        if (fragmentByTag == null) {
            val outputStream = ByteArrayOutputStream()
            fragmentManager.dump("", null, PrintWriter(outputStream, true), null)
            val s = String(outputStream.toByteArray(), Charsets.UTF_8)
            throw IllegalStateException("Could not find fragment via hacky way.\n" +
                    "We were looking for position: " + position + " name: " + name + "\n" +
                    "Fragment at this position does not exists, or hack stopped working.\n" +
                    "Current fragment manager dump is: " + s)
        }
        return fragmentByTag
    }

    private fun makeFragmentName(viewId: Int, index: Int): String {
        return "android:switcher:$viewId:$index"
    }

}