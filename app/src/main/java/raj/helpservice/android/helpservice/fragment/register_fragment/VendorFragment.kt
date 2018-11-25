package raj.helpservice.android.helpservice.fragment.register_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import org.angmarch.views.NiceSpinner
import raj.helpservice.android.helpservice.NetworkUtils

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.spstorage.UserPreference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [VendorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class VendorFragment : Fragment() {
    private var listener: VendorNextFragment? = null

    lateinit var spinner: NiceSpinner
    lateinit var nextButton :Button
    lateinit var pincode: TextInputEditText
    lateinit var radioButton: RadioButton
    var choosenCity:City? = null

    lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_vendor, container, false)
        spinner = view.findViewById<NiceSpinner>(R.id.spinner_registration)
        nextButton = view.findViewById(R.id.validate_button)
        nextButton.setOnClickListener{onButtonPressed()}
        pincode = view.findViewById(R.id.register_pincode)
        radioButton = view.findViewById(R.id.added_vendor)
        spinner.attachDataSource(NetworkUtils.cities?.map { it.cityName })
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                choosenCity = NetworkUtils.cities?.get(position)
                NetworkUtils.choosenRegistrationCity = choosenCity
                NetworkUtils.registrationUser?.cityId = choosenCity?.cityID
                viewModel.selectCityValue(city = choosenCity!!)


            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        })
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewModel.selectedValue.observe(this, Observer {
          radioButton.visibility = if (it!!) View.GONE else View.VISIBLE
        })
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        if (choosenCity == null){
            Toast.makeText(context,"Please choose city",Toast.LENGTH_SHORT).show()
            return
        }
        if (!pincode.text.isEmpty()){
            NetworkUtils.registrationUser?.pincode = pincode.text.toString()
        }
        NetworkUtils.registrationUser?.cityId = choosenCity?.cityID
        UserPreference.saveCityId(city = choosenCity!!,context = context!!)
        listener?.changeFromVendorToLast()
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is VendorNextFragment) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface VendorNextFragment {
        // TODO: Update argument type and name
        fun changeFromVendorToLast()
    }

}
