package raj.helpservice.android.helpservice.fragment.register_fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
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
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.Worker


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegistrationServiceFragment.FinishRegistration] interface
 * to handle interaction events.
 * Use the [RegistrationServiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RegistrationServiceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var listener: FinishRegistration? = null

    lateinit var viewModel: RegistrationViewModel
    lateinit var radioButton: RadioButton
    lateinit var nextButton: Button
    lateinit var serviceSpinner: NiceSpinner
    var choosenProffession : Profession? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragmen
        val view =  inflater.inflate(R.layout.fragment_registration, container, false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        radioButton  = view.findViewById<RadioButton>(R.id.added_vendor)
        nextButton = view.findViewById(R.id.validate_button_last)
        serviceSpinner = view.findViewById(R.id.spinner_registration_service)
        serviceSpinner.attachDataSource(NetworkUtils.proffessions?.map { it.serviceName  })
        serviceSpinner.setOnItemSelectedListener(object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                choosenProffession = NetworkUtils.proffessions?.get(position)
                NetworkUtils.registrationUser?.serviceTypeId = choosenProffession?.serviceTypeID
            }

        })

        nextButton.setOnClickListener {
            if (choosenProffession == null) {
                Toast.makeText(context, "Please choose profession", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            listener?.finishRegistration()


        }

        viewModel.selectedValue.observe(this, Observer {
            if (it!!)
                radioButton.visibility = View.GONE
            else
                radioButton.visibility = View.VISIBLE
        })

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed() {
        listener?.finishRegistration()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FinishRegistration) {
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
    interface FinishRegistration {
        fun finishRegistration()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrationServiceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = RegistrationServiceFragment()
    }
}
