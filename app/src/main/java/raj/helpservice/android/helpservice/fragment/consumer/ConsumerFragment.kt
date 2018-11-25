package raj.helpservice.android.helpservice.fragment.consumer

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepstone.apprating.AppRatingDialog
import com.stepstone.apprating.listener.RatingDialogListener
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.adapter.ConsumerAdapter
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.ConsumerRequest
import raj.helpservice.android.helpservice.spstorage.UserPreference
import android.content.DialogInterface
import android.os.Build
import android.support.v7.app.AlertDialog
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import raj.helpservice.android.helpservice.activity.ConsumerActivity
import raj.helpservice.android.helpservice.data.CloseRequest


class ConsumerFragment : Fragment(),ConsumerAdapter.AssignOrClose,RatingDialogListener {

    lateinit var recyclerView: RecyclerView
    lateinit var progressBar : ProgressBar

    val retrofit  by lazy { HelpServiceApi.getApiService() }

    var consumerRequest :ConsumerRequest? = null
    var rating : Int? = 0
    var comment :String? = null
    companion object {
        fun newInstance() = ConsumerFragment()
    }




    private lateinit var viewModel: ConsumerViewModel

    private lateinit var adapter: ConsumerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.consumer_fragment, container, false)

        val consumerActivity = activity as ConsumerActivity
        consumerActivity.setActionBarTitle("Responces")
        recyclerView = view.findViewById(R.id.consumer_search_recycler)
        progressBar = view.findViewById<ProgressBar>(R.id.consumer_request_progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val consumers = ArrayList<ConsumerRequest>()
        adapter = ConsumerAdapter(consumers,this@ConsumerFragment)
        recyclerView.adapter = adapter
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsumerViewModel::class.java)
        downloadListConsumerRequest()
    }

    fun downloadListConsumerRequest() {
          val userId = UserPreference.getUser(context!!)?.userID
        progressBar.visibility = View.VISIBLE
        viewModel.getConsumerList(userId!!).observe(this, Observer {
            adapter.updateData(it!!)
            progressBar.visibility = View.GONE

        })
    }

    override fun clickOnAssignButton(consumerRequest: ConsumerRequest) {
        this.consumerRequest = consumerRequest
        showRatingDialogWithStars()
    }

    override fun clickOnCloseButton(consumerRequest: ConsumerRequest) {
        this.consumerRequest = consumerRequest
        if (consumerRequest.vendorSelected == "1"){
            showRatingDialogWithStars()
        }else if (consumerRequest.vendorSelected == "0"){
            showRatingDialogWithoutStars()
        }

    }

    override fun onNegativeButtonClicked() {
    }

    override fun onNeutralButtonClicked() {
    }

    override fun onPositiveButtonClicked(rate: Int, comment: String) {
        sendDataToServer(consumerRequest?.serviceRequestID!!,rate,comment)
    }

    private fun showRatingDialogWithStars() {
        AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setDefaultRating(2)
                .setTitle("Are you sure to close the Request ?")
                .setDescription("Please select some stars and give your feedback")
                .setStarColor(R.color.colorStarActivated)
                .setNumberOfStars(5)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.titleTextColor)
                .setDescriptionTextColor(R.color.descriptionTextColor)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogSlideHorizontalAnimation)
                .setHint("Please write your comment here ...")
                .setHintTextColor(R.color.colorStarActivated)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(activity!!)
                .setTargetFragment(this, 0)
                .show()
    }

    private fun showRatingDialogWithoutStars(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)

        builder.setTitle("Close Request")
                .setMessage("Are you sure to close the Request ?")
                .setPositiveButton("Submit") { dialog, which ->
                    sendDataToServer(consumerRequest?.serviceRequestID!!)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    // do nothing
                    dialog.dismiss()
                }
                .create().show()
    }

    fun sendDataToServer(serviceId:String,rate:Int? = null,comment: String? = null){
        val closeRequest = CloseRequest(serviceId,"","")
        viewModel.closeRequest(closeRequest).observe(this, Observer {
            when {
                it?.isSuccess()== true -> downloadListConsumerRequest()
                it?.isSuccess()== false -> Toast.makeText(context,"Not closed",Toast.LENGTH_SHORT).show()
                it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(),Toast.LENGTH_SHORT).show()
            }
        })


    }

}
