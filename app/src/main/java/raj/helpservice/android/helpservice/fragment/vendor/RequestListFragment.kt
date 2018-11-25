package raj.helpservice.android.helpservice.fragment.vendor

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.adapter.VendorOpenRequestAdapter
import raj.helpservice.android.helpservice.data.RequestedService
import raj.helpservice.android.helpservice.spstorage.UserPreference

class RequestListFragment : Fragment() ,VendorOpenRequestAdapter.ClickRequestService{
    override fun clickOnItemRequestService(requestService: RequestedService) {
    }

    lateinit var serviceName: TextView
    lateinit var pincodeTextCity: TextView
    lateinit var enquiresMade: TextView
    lateinit var averateRating: TextView
    lateinit var recylcerOpenRequest: RecyclerView
    lateinit var adapter:VendorOpenRequestAdapter

    companion object {
        fun newInstance() = RequestListFragment()
    }
    private lateinit var viewModel: VendorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VendorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        val view = inflater.inflate(R.layout.request_list_fragment, container, false)
        serviceName = view.findViewById(R.id.service_name)
        pincodeTextCity = view.findViewById(R.id.city_pincode)
        enquiresMade = view.findViewById(R.id.enquires_made)
        averateRating = view.findViewById(R.id.average_ratings)
        recylcerOpenRequest = view.findViewById(R.id.vendor_request_list)
        adapter = VendorOpenRequestAdapter(this)
        recylcerOpenRequest.layoutManager = LinearLayoutManager(context!!)
        recylcerOpenRequest.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        downloadPersonalInformation()
        updateRecycleView()
    }


    fun downloadPersonalInformation(){
        val user = UserPreference.getUser(context!!)
        viewModel.getVendorInformation(user?.userID!!).observe(this, Observer {
            serviceName.text = it?.vendorType
            val city = UserPreference.getCities(context!!)?.filter { it.cityID == user.cityId }?.single()
            pincodeTextCity.text = "${city?.cityName}, ${user?.pincode}"
            enquiresMade.text = "Enquires made:${it?.enquiriesMade}"
            averateRating.text = "Average rating ${it?.ratingsReceived}"
        })
    }

    fun updateRecycleView(){
        val user = UserPreference.getUser(context!!)
        viewModel.getListOpenRequests(user?.userID!!).observe(this, Observer {
            adapter.changeValue(it!!)
        })
    }

}
