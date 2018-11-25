package raj.helpservice.android.helpservice.fragment.vendor


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Base64

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*

import raj.helpservice.android.helpservice.R

import kotlinx.android.synthetic.main.fragment_vendor_page.view.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.spstorage.UserPreference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class VendorFragment : Fragment() {

    private lateinit var viewOfLayout: View
    private lateinit var progressBar: ProgressBar
    private lateinit var constraintDetailed: ConstraintLayout
    private lateinit var linerLayoutCommon: LinearLayout



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        viewOfLayout = inflater.inflate(R.layout.fragment_vendor_page, container, false)
        progressBar = viewOfLayout.findViewById(R.id.worker_detailed_progress)
        constraintDetailed = viewOfLayout.findViewById(R.id.worker_detailed_container)
        linerLayoutCommon = viewOfLayout.findViewById(R.id.worker_common_container)
        return viewOfLayout
    }

    private val retrofit by lazy{
        HelpServiceApi.getApiService()
    }

    override fun onResume() {
        super.onResume()
        launch(UI) {
            try {
                val user = UserPreference.getUser(context!!)
                showProgress()
                val detailedWorker = retrofit.getDetailInformation(workerId = user?.userID!!).await()[0]
                viewOfLayout.worker_name.text = detailedWorker.vendorName
                viewOfLayout.worker_rating.rating = detailedWorker.averageRatings.toFloat()
                viewOfLayout.worker_profession.text = detailedWorker.serviceName
                viewOfLayout.worker_registered_date.text = detailedWorker.registeredOn
                viewOfLayout.worker_rate.text = detailedWorker.serviceRate
                viewOfLayout.worker_discount.text = detailedWorker.discountAmount
                viewOfLayout.worker_language.text = detailedWorker.spokenLanguages
                viewOfLayout.type_of_contract.text = detailedWorker.serviceType
                viewOfLayout.worker_description.text = detailedWorker.workDescription +" Service charges: "+ detailedWorker.serviceCharges
                val buteArray = Base64.decode(detailedWorker.vendorPhoto, Base64.DEFAULT)
                Glide.with(context!!)
                        .load(buteArray)
                        .apply(RequestOptions.centerCropTransform())
                        .into(viewOfLayout.image_worker)
                hideProgress()

            }
            catch (e:Exception){
                hideProgress()
                Toast.makeText(context,"There is some problem with internet", Toast.LENGTH_LONG).show()
            }


        }
    }




    fun hideProgress(){
        progressBar.visibility = View.GONE
        constraintDetailed.visibility = View.VISIBLE
        linerLayoutCommon.visibility = View.VISIBLE
        request_button.visibility = View.VISIBLE
    }

    fun showProgress(){
        progressBar.visibility = View.VISIBLE
        constraintDetailed.visibility = View.GONE
        linerLayoutCommon.visibility = View.GONE
        request_button.visibility = View.GONE
    }


}
