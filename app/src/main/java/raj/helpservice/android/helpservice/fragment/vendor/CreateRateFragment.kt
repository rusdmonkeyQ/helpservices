package raj.helpservice.android.helpservice.fragment.vendor


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast

import raj.helpservice.android.helpservice.R
import kotlinx.android.synthetic.main.create_rate_fragment.view.*
import raj.helpservice.android.helpservice.data.RateInterval
import raj.helpservice.android.helpservice.data.VendorLanguageText
import raj.helpservice.android.helpservice.data.VendorSetupModel
import raj.helpservice.android.helpservice.spstorage.UserPreference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CreateRateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewOfLayout: View
    private var choosenRateInterval   = RateInterval().apply { serviceMasterID = 1
                                                                serviceName = "1 Hour"
                                                                selected = "N"}
    private lateinit var viewModel: VendorViewModel
    private  var vendorSetupModel: VendorSetupModel = VendorSetupModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VendorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewOfLayout = inflater.inflate(R.layout.create_rate_fragment, container, false)
        viewOfLayout.spinner_rate_interval.attachDataSource(UserPreference.getRateInterval().map { it.serviceName })
        viewOfLayout.spinner_rate_interval.setOnItemSelectedListener(object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                choosenRateInterval = UserPreference.getRateInterval()[position]
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        })

        viewOfLayout.cancel_button.setOnClickListener{
            activity!!.supportFragmentManager.popBackStackImmediate()
        }
        viewOfLayout.save_button.setOnClickListener{
            sendSetUpToServer()

        }
        return  viewOfLayout
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = CreateRateFragment()
    }


    fun sendSetUpToServer(){
        val user= UserPreference.getUser(context!!)
        vendorSetupModel = VendorSetupModel()
        vendorSetupModel.id = user?.userID
        vendorSetupModel.serviceRate = viewOfLayout.rate_request.text.toString()
        vendorSetupModel.serviceCharges = viewOfLayout.service_charges.text.toString()
        vendorSetupModel.discountAmmount = viewOfLayout.discount_amount.text.toString()

        viewModel.sendDataToServer(vendorSetupModel).observe(this, Observer {
            when {
                it?.isSuccess()== true -> {            activity!!.supportFragmentManager.popBackStackImmediate() }
                it?.isSuccess()== false -> Toast.makeText(context,"Not closed", Toast.LENGTH_SHORT).show()
                it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
        })
    }
}
