package raj.helpservice.android.helpservice.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.DetailActivity
import raj.helpservice.android.helpservice.adapter.WorkerAdapter
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.Worker
import raj.helpservice.android.helpservice.spstorage.UserPreference

class ListFragment : Fragment(),WorkerAdapter.ClickOnItem {


    lateinit var listWorkers: RecyclerView
    lateinit var textNo: TextView
    lateinit var adapter: WorkerAdapter
    val workers: ArrayList<Worker> = ArrayList()
    lateinit var city: City
    lateinit var profession: Profession
    lateinit var pincode: String
    lateinit var progress: ProgressBar

    private val retrofit by lazy {
        HelpServiceApi.getApiService()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.list_fragment, container, false)
        listWorkers = view.findViewById(R.id.worker_recycler_view)
        textNo = view.findViewById(R.id.text_no_items)
        progress = view.findViewById(R.id.progress_list)
        city = arguments?.getSerializable(EXTRA_CITY) as City
        profession = arguments?.getSerializable(EXTRA_PROFFESSION) as Profession
        pincode = arguments?.getString(EXTRA_PINCODE) as String
        val linearLayoutManager = LinearLayoutManager(context)
        adapter = WorkerAdapter(workers = workers, type = profession.serviceName, clickOnItem = this@ListFragment)
        listWorkers.layoutManager = linearLayoutManager
        listWorkers.adapter = adapter
        return view

    }


    override fun onResume() {
        super.onResume()
        launch(UI) {
            progress.visibility = View.VISIBLE
            val worker: ArrayList<Worker>
            if (profession.serviceName != "All") {
                 worker = retrofit.getSearchWorkers(profession.serviceTypeID, city.cityID, pincode).await()
            }else{
                worker = retrofit.getSearchConsumerWorker(UserPreference.getUser(context!!)?.userID!!).await()
            }
            progress.visibility = View.GONE
            if (worker.size == 0) {
                listWorkers.visibility = View.GONE
                textNo.visibility = View.VISIBLE
                textNo.text = "There aren't any  " + profession.serviceName + "s"
            }
            adapter.changeValues(worker)
        }


    }

    companion object {
        const val EXTRA_CITY = "EXTRA_CITY"
        const val EXTRA_PROFFESSION = "extra_profession"
        const val EXTRA_PINCODE = "extra_pincode"
        fun newInstance(city: City, profession: Profession, pinCode: String): ListFragment {
            val fragment = ListFragment()
            fragment.arguments = Bundle().also {
                it.putSerializable(EXTRA_CITY, city)
                it.putSerializable(EXTRA_PROFFESSION, profession)
                it.putString(EXTRA_PINCODE, pinCode)
            }
            return fragment
        }

    }

    override fun clickItem(worker: Worker) {
        val bundle = Bundle().also { it.putSerializable("EXTRA_WORKER", worker) }
        val intent = Intent(activity,DetailActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}