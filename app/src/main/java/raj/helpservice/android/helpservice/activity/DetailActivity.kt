package raj.helpservice.android.helpservice.activity

import android.os.Bundle
import android.app.Activity
import android.support.constraint.ConstraintLayout
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import raj.helpservice.android.helpservice.R

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.Worker

class DetailActivity : Activity() {

    lateinit var worker:Worker
    val button:Button? = null
    lateinit var progressBar:ProgressBar
    lateinit var constraintDetailed:ConstraintLayout
    lateinit var linerLayoutCommon:LinearLayout

    val retrofit by lazy { HelpServiceApi.getApiService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        worker = intent.extras["EXTRA_WORKER"] as Worker
        progressBar = findViewById(R.id.worker_detailed_progress)
        constraintDetailed = findViewById(R.id.worker_detailed_container)
        linerLayoutCommon = findViewById(R.id.worker_common_container)


    }

    override fun onResume() {
        super.onResume()

        launch(UI) {
            try {
                showProgress()
                val detailedWorker = retrofit.getDetailInformation(workerId = worker.userID).await()[0]
                worker_name.text = detailedWorker.vendorName
                worker_rating.rating = detailedWorker.averageRatings.toFloat()
                worker_profession.text = detailedWorker.serviceName
                worker_registered_date.text = detailedWorker.registeredOn
                worker_rate.text = detailedWorker.serviceRate
                worker_discount.text = detailedWorker.discountAmount
                worker_language.text = detailedWorker.spokenLanguages
                type_of_contract.text = detailedWorker.serviceType
                worker_description.text = detailedWorker.workDescription +" Service charges: "+ detailedWorker.serviceCharges
                val buteArray = Base64.decode(detailedWorker.vendorPhoto,Base64.DEFAULT)
                Glide.with(baseContext)
                        .load(buteArray)
                        .apply(RequestOptions.centerCropTransform())
                        .into(image_worker)
                hideProgress()

            }
            catch (e:Exception){
                hideProgress()
                Toast.makeText(applicationContext,"There is some problem with internet",Toast.LENGTH_LONG).show()
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
