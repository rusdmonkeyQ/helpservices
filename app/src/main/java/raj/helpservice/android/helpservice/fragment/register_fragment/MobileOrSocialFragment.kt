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
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import raj.helpservice.android.helpservice.NetworkUtils

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.RegistrationUser

class MobileOrSocialFragment : Fragment() {

    lateinit var registrationButton: Button
    lateinit var registrationName: TextInputEditText
    lateinit var registrationNumber: TextInputEditText
    lateinit var registraionPassword: TextInputEditText
    lateinit var progressBar: ProgressBar
     var listener: ChangeFragment? = null
    val retroft by lazy { HelpServiceApi.getApiService() }


    companion object {
        fun newInstance() = MobileOrSocialFragment()
    }

    private lateinit var viewModel: RegistrationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.mobile_or_social_fragment, container, false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        registrationButton = view.findViewById(R.id.register_button)
        registrationName = view.findViewById(R.id.register_name)
        registrationNumber = view.findViewById(R.id.register_phone)
        registraionPassword = view.findViewById(R.id.register_password)
        progressBar = view.findViewById(R.id.registration_progress)
        registrationButton.setOnClickListener {
            if (checkEditText(registraionPassword) || checkEditText(registrationName) ||checkEditText(registrationNumber)){
                Toast.makeText(context,"Please fill all field",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (registraionPassword.length() < 5){
                Toast.makeText(context,"Minimum password length 5",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = RegistrationUser()
            user.name = registrationName.text.toString()
            user.mobileNumber = registrationNumber.text.toString()
            user.password = registraionPassword.text.toString()
            user.otpNumber = ""
            progressBar.visibility = View.VISIBLE
            viewModel.getRegistrationUser(user).observe(MobileOrSocialFragment@this, Observer {
                if (it?.isRegistered == "false") {
                    viewModel.selectdRegistrationUser(registrationUser = it)
                    saveRegistrationUser(it)
                    listener?.changeToValidate()
                }else{
                    Toast.makeText(context,"Provided mobile number is already in use",Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    return@Observer
                }
            })
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun checkEditText(text: TextInputEditText) = text.text.toString().isEmpty()


    fun saveRegistrationUser(registrationUser: RegistrationUser){
        NetworkUtils.registrationUser = registrationUser
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChangeFragment) {
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
    interface ChangeFragment {
        // TODO: Update argument type and name
        fun changeToValidate()
    }
}
