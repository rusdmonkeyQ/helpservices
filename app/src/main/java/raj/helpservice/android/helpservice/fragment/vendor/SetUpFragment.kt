package raj.helpservice.android.helpservice.fragment.vendor


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.data.Language
import raj.helpservice.android.helpservice.data.VendorLanguageText
import raj.helpservice.android.helpservice.data.VendorSetupModel
import raj.helpservice.android.helpservice.spstorage.UserPreference


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SetUpFragment : Fragment() {

    private lateinit var viewModel: VendorViewModel
    lateinit var serviceText: TextView
    lateinit var language: TextView
    lateinit var workDescription: TextInputEditText
    lateinit var createRequestButton: Button
    lateinit var cancelButton: Button
    lateinit var progressBar : ProgressBar
    lateinit var  materialDialog: MaterialDialog
    var languges :ArrayList<Language> = UserPreference.getLanguages()
    var selectedIndex = arrayListOf<Int>()
    var vendorSetupModel: VendorLanguageText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VendorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        val view = inflater.inflate(R.layout.fragment_set_up, container, false)
        serviceText = view.findViewById(R.id.service_name)
        language = view.findViewById(R.id.spinner_language)
        workDescription = view.findViewById(R.id.et_work_description)
        createRequestButton = view.findViewById(R.id.save_button)
        createRequestButton.setOnClickListener {
            sendSetUpToServer()
        }
        cancelButton = view.findViewById(R.id.cancel_button)
        progressBar = activity?.findViewById(R.id.vendor_progress)!!
        materialDialog = MaterialDialog.Builder(context!!)
                .title("Choose languages")
                .items(languges.map { it.name })
                .itemsCallbackMultiChoice(null) { dialog, which, text ->
                    /**
                     * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                     * returning false here won't allow the newly selected check box to actually be selected
                     * (or the newly unselected check box to be unchecked).
                     * See the limited multi choice dialog example in the sample project for details.
                     */
                    /**
                     * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                     * returning false here won't allow the newly selected check box to actually be selected
                     * (or the newly unselected check box to be unchecked).
                     * See the limited multi choice dialog example in the sample project for details.
                     */
                    val sb = StringBuilder()
                    text.forEach {
                      sb.append(it.toString()+" ")
                    }
                    selectedIndex.clear()
                    which.forEach {
                        selectedIndex.add(it)
                    }
                    language.text = sb.toString()

                    false
                }
                .positiveText("Select")
                .negativeText("Cancel")
                .cancelListener { dialog -> dialog.dismiss() }
                .build()

        language.setOnClickListener { materialDialog.show()}
        return view
    }


    override fun onResume() {
        super.onResume()
        downloadSetUpInformation()
        getLanguages()

    }

    fun downloadSetUpInformation(){
        progressBar.visibility = View.VISIBLE
        val user  = UserPreference.getUser(context!!)
        viewModel.getSetUpInformation(user?.userID!!).observe(this, Observer { vendorSetUp ->
            val serviceType = UserPreference.getProfessions(context!!)?.filter { user.serviceTypeId == it.serviceTypeID }?.single()
            serviceText.text = serviceType!!.serviceName
            workDescription.setText(vendorSetUp?.description)
            progressBar.visibility = View.GONE



        })
    }

    fun sendSetUpToServer(){
        progressBar.visibility = View.VISIBLE
        val user= UserPreference.getUser(context!!)
        vendorSetupModel = VendorLanguageText()
        vendorSetupModel?.description = workDescription.text.toString()
        if (selectedIndex.size == 0){
            Toast.makeText(context,"Please choose new languages",Toast.LENGTH_SHORT).show()
            return
        }
        vendorSetupModel?.languageId = selectedIndex.map { it+1 }.joinToString("/")
        vendorSetupModel?.id = user?.userID
        viewModel.sendLangWorkDesc(vendorSetupModel!!).observe(this, Observer {
            when {
                it?.isSuccess()== true -> {
                    downloadSetUpInformation()
                    getLanguages()
                }
                it?.isSuccess()== false -> Toast.makeText(context,"Not closed", Toast.LENGTH_SHORT).show()
                it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
            progressBar.visibility = View.GONE
        })
    }

    fun getLanguages(){
        progressBar.visibility = View.VISIBLE
        val user  = UserPreference.getUser(context!!)
        viewModel.getLanguages(user?.userID!!).observe(this, Observer { languages->
            val selectedLanguage = languages?.filter { it.selected == "Y" }?.map { it.name }!!.joinToString()
            language.text = selectedLanguage
            progressBar.visibility = View.GONE
        })
    }



}
