package raj.helpservice.android.helpservice.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import raj.helpservice.android.helpservice.R

class ErrorFragment :Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.error_layout,container,false)
    }


    override fun onResume() {
        super.onResume()
    }
}