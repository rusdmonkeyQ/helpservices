package raj.helpservice.android.helpservice.fragment.vendor


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast

import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.adapter.AddedRatesAdapter
import raj.helpservice.android.helpservice.data.AddedRatesModel
import raj.helpservice.android.helpservice.spstorage.UserPreference

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RateFragment : Fragment() ,AddedRatesAdapter.OnItemClickListener{


    lateinit var recyclerRates : RecyclerView
    lateinit var adapter: AddedRatesAdapter
    lateinit var progressBar: ProgressBar

    private lateinit var viewModel: VendorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rate, container, false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VendorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        adapter = AddedRatesAdapter(this)
        recyclerRates = view.findViewById(R.id.recycler_rates)
        recyclerRates.layoutManager = LinearLayoutManager(context)
        recyclerRates.adapter = adapter
        progressBar = activity!!.findViewById(R.id.vendor_progress)
        return view
    }

    override fun onResume() {
        super.onResume()
        downloadRateInformation()
    }

    fun downloadRateInformation(){
        progressBar.visibility = View.VISIBLE
        val user = UserPreference.getUser(context!!)
        viewModel.getRates(user?.userID!!).observe(this, Observer {
         adapter.changeValues(it)
         if (it?.size==0){
             Toast.makeText(context,"There is not any rates",Toast.LENGTH_LONG).show()
         }
         progressBar.visibility = View.GONE
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(item: AddedRatesModel?, position: Int) {

    }




}
