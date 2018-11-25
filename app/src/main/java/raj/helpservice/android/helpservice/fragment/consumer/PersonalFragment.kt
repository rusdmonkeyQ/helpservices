package raj.helpservice.android.helpservice.fragment.consumer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.ConsumerActivity
import raj.helpservice.android.helpservice.data.Personal
import raj.helpservice.android.helpservice.data.PersonalSending
import raj.helpservice.android.helpservice.spstorage.UserPreference


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [PersonalFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class PersonalFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    lateinit var phoneNumber: TextInputEditText
    lateinit var phoneLanding : TextInputEditText
    lateinit var consumeNameEditContainer: TextInputLayout
    lateinit var workerName : TextView
    lateinit var workerNameEditText : TextInputEditText
    lateinit var phoneEmail: TextInputEditText
    lateinit var saveButton: Button
    lateinit var closeButton: Button
    lateinit var viewLine : View
    lateinit var surname: TextInputEditText
    lateinit var surNameContainer: TextInputLayout
    lateinit var viewLineTwo : View
    private var editable: Boolean = false
    lateinit var progressBar: ProgressBar
    private lateinit var viewModel: ConsumerViewModel
    private var isConsumer = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        isConsumer = activity!! is ConsumerActivity
        val view = inflater.inflate(R.layout.fragment_personal, container, false)
        phoneNumber = view.findViewById<TextInputEditText>(R.id.phone_number)
        workerName = view.findViewById(R.id.worker_name)
        surname = view.findViewById(R.id.surname_name_editext)
        surNameContainer = view.findViewById(R.id.surname_container)
        viewLineTwo = view.findViewById(R.id.view_visible_two)
        phoneLanding = view.findViewById<TextInputEditText>(R.id.landing_phone)
        consumeNameEditContainer = view.findViewById<TextInputLayout>(R.id.name_container)
        phoneEmail = view.findViewById<TextInputEditText>(R.id.phone_email)
        workerNameEditText  = view.findViewById(R.id.phone_name_editext)
        saveButton = view.findViewById(R.id.save_button)
        saveButton.setOnClickListener { sendNewPersonalDetails() }
        closeButton = view.findViewById(R.id.cancel_button)
        viewLine = view.findViewById(R.id.view_visible)
        if (isConsumer) {
            progressBar = activity?.findViewById(R.id.consumer_progress)!!
        }else{
            progressBar = activity?.findViewById(R.id.vendor_progress)!!
        }
        closeButton.setOnClickListener { setEditableViews() }
        return view
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.consumer_personal,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        setEditableViews()
        return true
    }

    fun setEditableViews() {
        editable = !editable
        setEditable()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsumerViewModel::class.java)
        downloadPersonal()
    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

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
        fun onFragmentInteraction(uri: Uri)
    }

    private fun setEditable(){
        setEnabled(phoneEmail)
        setEnabled(phoneLanding)
        setEnabled(phoneNumber)
        setVisibility(consumeNameEditContainer)
        setVisibility(surNameContainer)
        setVisibility(closeButton)
        setVisibility(saveButton)
        setVisibility(viewLine)
        setVisibility(viewLineTwo)
    }

    private fun setVisibility(view: View){
        if (editable) view.visibility = View.VISIBLE else view.visibility = View.GONE
    }

    private fun setEnabled(view:View){
        view.isEnabled = editable
    }

    private fun downloadPersonal(){
        progressBar.visibility = View.VISIBLE
   val user = UserPreference.getUser(context!!)
        viewModel.getPersonalDetails(user?.userID!!).observe(this, Observer {
            Log.e("TAG",it?.toString())
            phoneEmail.setText(it?.email)
            workerName.text = it?.name
            workerNameEditText.setText(it?.name)
            phoneLanding.setText(it?.landline)
            phoneNumber.setText(it?.alterMobileNo)
            progressBar.visibility = View.GONE

        })

    }

    fun sendNewPersonalDetails(){
        progressBar.visibility = View.VISIBLE
        val userPreference = UserPreference.getUser(context!!)
        val personal = PersonalSending(
                id = userPreference?.userID!!,
                name = workerNameEditText.text.toString(),
                alterMobileNo = phoneNumber.text.toString(),
                email = phoneEmail.text.toString(),
                landline = phoneLanding.text.toString())
        viewModel.sendPersonalDetails(personal).observe(this, Observer {
            progressBar.visibility = View.GONE
            when {
                it?.isSuccess()== true -> {downloadPersonal()
                setEditableViews()}
                it?.isSuccess()== false -> Toast.makeText(context,"Not saved Personal Details", Toast.LENGTH_SHORT).show()
                it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
        })
    }



}
