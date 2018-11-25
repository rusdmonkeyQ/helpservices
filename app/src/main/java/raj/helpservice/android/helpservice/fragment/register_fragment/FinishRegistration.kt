package raj.helpservice.android.helpservice.fragment.register_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import org.angmarch.views.NiceSpinner
import raj.helpservice.android.helpservice.NetworkUtils

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.ConsumerActivity
import raj.helpservice.android.helpservice.activity.VendorActivity
import raj.helpservice.android.helpservice.spstorage.UserPreference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FinishRegistration.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FinishRegistration.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FinishRegistration : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: OnFragmentInteractionListener? = null

    lateinit var viewModel: RegistrationViewModel
    lateinit var finishButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_vendor_location, container, false)
        finishButton = view.findViewById(R.id.validate_button)
        finishButton.setOnClickListener{
            view.findViewById<ProgressBar>(R.id.finish_registration_progress).visibility = View.VISIBLE
            finishButton.visibility = View.GONE
            viewModel.finishRegistration(NetworkUtils.registrationUser!!).observe(this, Observer {
                Log.e("RegistrationUser",NetworkUtils.registrationUser.toString())
                if (it?.isRegistered == "true"){
                    NetworkUtils.registrationUser = it
                    UserPreference.saveUser(user = it,context = context!!)
                    if (it.userType == "C"){
                        val intent = Intent(context,ConsumerActivity::class.java)
                        startActivity(intent)
                        activity?.finish()

                    }else if (it.userType=="V"){
                        val intent = Intent(context,VendorActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }

                }
            })
        }
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.onFragmentInteraction()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        fun onFragmentInteraction()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FinishRegistration.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = FinishRegistration()
    }
}
