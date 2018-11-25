package raj.helpservice.android.helpservice.fragment.register_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text
import raj.helpservice.android.helpservice.NetworkUtils

import raj.helpservice.android.helpservice.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OtpFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OtpFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class OtpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null
    lateinit var validateButton:Button
    lateinit var validationOtp:TextInputEditText
    lateinit var textView:TextView
    private lateinit var viewModel: RegistrationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
            textView = view.findViewById(R.id.password_otp)
            validateButton = view.findViewById(R.id.validate_otp_number)
            validationOtp = view.findViewById(R.id.validation_otp)

        viewModel.savedUser.observe(this, Observer {
            textView.text = it?.otpNumber
        })
        validateButton.setOnClickListener{
            val optNumber = validationOtp.text.toString()
         //   textView.text = NetworkUtils.registrationUser?.otpNumber
            if (optNumber != NetworkUtils.registrationUser?.otpNumber){
                Toast.makeText(context,"Please enter the correct OTP",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
                onButtonPressed()
            }

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.changeToValidateOtP()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
        textView.text = NetworkUtils.registrationUser?.otpNumber

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
        fun changeToValidateOtP()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OtpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = OtpFragment()

    }
}
