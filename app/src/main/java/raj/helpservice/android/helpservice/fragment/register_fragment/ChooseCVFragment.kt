package raj.helpservice.android.helpservice.fragment.register_fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import raj.helpservice.android.helpservice.NetworkUtils

import raj.helpservice.android.helpservice.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChooseCVFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChooseCVFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ChooseCVFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    lateinit var radioGroup: RadioGroup
    lateinit var radioVendor: RadioButton
    lateinit var radioConsumer: RadioButton
    lateinit var nextButton: Button
    lateinit var radioAdded: RadioButton
    var isConsumer  = true
    lateinit var viewModel:RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view  = inflater.inflate(R.layout.fragment_choose_cv, container, false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        radioGroup = view.findViewById(R.id.checkradioGroup)
        radioVendor = view.findViewById(R.id.vendor_radio)
        radioConsumer = view.findViewById(R.id.consumer_radio)
        nextButton = view.findViewById(R.id.next_button_choose)
        radioAdded = view.findViewById(R.id.added_vendor)

        nextButton.setOnClickListener {
            if (radioConsumer.isChecked || radioVendor.isChecked)
                    onButtonPressed()
            else
                Toast.makeText(context,"Please choose User type",Toast.LENGTH_SHORT).show()

        }

        radioVendor.setOnClickListener {
            onRadioButtonClicked(it)
        }
        radioConsumer.setOnClickListener{
            onRadioButtonClicked(it)
        }


        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.changeToVendorOrConsumer(isConsumer)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    fun onRadioButtonClicked(view:View){
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.consumer_radio ->
                    if (checked) {
                        radioAdded.visibility = View.GONE
                        isConsumer = true
                        viewModel.selectBooleanValue(isConsumer)
                        NetworkUtils.registrationUser?.userType = "1"


                    }
                R.id.vendor_radio ->
                    if (checked) {
                        radioAdded.visibility = View.VISIBLE
                        isConsumer = false
                        viewModel.selectBooleanValue(isConsumer)
                        NetworkUtils.registrationUser?.userType = "2"



                    }
            }
        }
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
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun changeToVendorOrConsumer(isConsumer: Boolean)

        fun changeToNextItem()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChooseCVFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = ChooseCVFragment()
    }
}
