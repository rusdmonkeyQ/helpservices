package raj.helpservice.android.helpservice.fragment.consumer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.angmarch.views.NiceSpinner

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.ConsumerActivity
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.customviews.CreateService
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.spstorage.UserPreference

class CreateRequest : Fragment() {

    lateinit var cityTextView: TextView
    lateinit var serviceSpinner: NiceSpinner
    lateinit var contractButton: Button
    lateinit var laborButton: Button
    lateinit var bothButton: Button
    lateinit var pincodeEditText: TextInputEditText
    lateinit var createRequestButton: Button
    lateinit var workDescription: TextInputEditText
    lateinit var progressBar :ProgressBar
    var choosenCity:City? = null
    var chooseService:Profession? = null
    var cityList:ArrayList<City>? = null
    var professionList :ArrayList<Profession>? = null
    var isContract = true
    var isBoth = false
    var isLabor = false

    val retrofit by lazy { HelpServiceApi.getApiService() }

    companion object {
        fun newInstance() = CreateRequest()
    }

    private lateinit var viewModel: CreateRequestViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.create_request_fragment, container, false)
        val consumerActivity = activity as ConsumerActivity
        consumerActivity.setActionBarTitle("CreateRequest")
        cityTextView = view.findViewById(R.id.spinner_city)
        progressBar = consumerActivity.findViewById(R.id.consumer_progress)
        serviceSpinner = view.findViewById(R.id.spinner_services)
        contractButton = view.findViewById(R.id.contract_button)
        laborButton = view.findViewById(R.id.labor_button)
        bothButton = view.findViewById(R.id.both_button)
        pincodeEditText = view.findViewById(R.id.pincode_request)
        createRequestButton = view.findViewById(R.id.create_request_button)
        workDescription = view.findViewById(R.id.et_work_description)
        cityList = UserPreference.getCities(context!!)
        val user = UserPreference.getUser(context!!)
        professionList = UserPreference.getProfessions(context!!)
        choosenCity = cityList?.filter { it.cityID == user?.cityId }!!.single()
        cityTextView.text = choosenCity?.cityName
        pincodeEditText.setText(user?.pincode)
        pincodeEditText.isEnabled = false
        serviceSpinner.attachDataSource(professionList?.map { it.serviceName })

        laborButton.setOnClickListener {
            isContract = false
            isLabor = true
            isBoth = false
            setLaborOrContract()
        }
        contractButton.setOnClickListener {
            isContract = true
            isLabor = false
            isBoth = false
            setLaborOrContract()
        }
        bothButton.setOnClickListener {
            isContract = false
            isLabor = false
            isBoth = true
            setLaborOrContract()
        }

        serviceSpinner.setOnItemSelectedListener(object :AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               chooseService = professionList!![position]
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        })

        createRequestButton.setOnClickListener {
            if(chooseService == null || choosenCity ==null){
                Toast.makeText(context!!,"Please choose city and service",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var serviceRequestId  = "1"
            if(isBoth){
                serviceRequestId = "3"
            } else if (isLabor)
                serviceRequestId = "2"

            val createRequestModel = CreateService(userId = UserPreference.getUser(context!!)?.userID
            ,typeId = chooseService?.serviceTypeID, serviceTypeId = serviceRequestId,cityId = choosenCity!!.cityID,
                  pincode = pincodeEditText.text.toString(),description = workDescription.text.toString(),cityName = choosenCity!!.cityName)


            createRequest(createRequestModel)
        }

        return  view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateRequestViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun setLaborOrContract(){
        if(isContract){
            setBlueButton(contractButton)
            setGrayButton(laborButton)
            setGrayButton(bothButton)
        }else if (isLabor){
            setBlueButton(laborButton)
            setGrayButton(contractButton)
            setGrayButton(bothButton)
        }else if(isBoth){
            setBlueButton(bothButton)
            setGrayButton(contractButton)
            setGrayButton(laborButton)
        }
    }

    fun createRequest(createService: CreateService){
        progressBar.visibility =View.VISIBLE
        viewModel.createRequest(createService).observe(this, Observer {
                progressBar.visibility = View.GONE
                when {
                    it?.isSuccess()== true -> {Toast.makeText(context!!,"Service created",Toast.LENGTH_SHORT).show()}
                    it?.isSuccess()== false -> Toast.makeText(context,"Not saved Personal Details", Toast.LENGTH_SHORT).show()
                    it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(), Toast.LENGTH_SHORT).show()
                }
            })
    }


    fun setBlueButton(button: Button){
       button.background = ContextCompat.getDrawable(context!!,R.drawable.button_blue_background)
        button.setTextColor(Color.WHITE)
    }

    fun setGrayButton(button: Button){
        button.background = ContextCompat.getDrawable(context!!,R.drawable.button_background_gray)
        button.setTextColor(Color.GRAY)
    }


}
