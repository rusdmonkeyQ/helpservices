package raj.helpservice.android.helpservice.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import org.angmarch.views.NiceSpinner
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.MainActivity
import raj.helpservice.android.helpservice.adapter.ProfessionAdapter
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.Worker

class SearchFragment : Fragment(),ProfessionAdapter.ClickOnProfession {

    val helpService by lazy {
        HelpServiceApi.getApiService()
    }
    override fun clickOnProfession(profession: Profession) {
            choosenProfession = profession
    }

    val TAG = SearchFragment.javaClass.name

    var cities:ArrayList<City> = ArrayList()
    var profession: ArrayList<Profession> = ArrayList()
    lateinit var citySpinner:NiceSpinner
     var choosenCity:City? = null
     var choosenProfession:Profession? = null
    lateinit var recyclerProfession:RecyclerView
    lateinit var adapter:ProfessionAdapter
    lateinit var searchButton:Button
    lateinit var pincodeEditText: EditText
    var fragmentChangeInterface: FragmentChangeInterface? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        cities = arguments?.getSerializable(EXTRA_CITIES) as ArrayList<City>
        profession = arguments?.getSerializable(EXTRA_PROFESSION) as ArrayList<Profession>
        choosenCity = cities[0]
        Log.e(TAG,"cities size " + cities.size)
        Log.e(TAG,"profession size " +profession.size)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.search_fragment,container,false)
        citySpinner = view.findViewById(R.id.city_spinner)
        citySpinner.attachDataSource(cities.map {it.cityName })
        citySpinner.setOnItemSelectedListener(object :AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.e(TAG,"On click item " + cities[position].cityName)
                choosenCity = cities[position]
                NetworkUtils.choosen = choosenCity

            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        })
        citySpinner.dropDownListPaddingBottom = 500
        recyclerProfession = view.findViewById(R.id.profession_recycler)
        recyclerProfession.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        adapter = ProfessionAdapter(profession,this)
        recyclerProfession.adapter = adapter
        searchButton = view.findViewById(R.id.search_button)
        pincodeEditText = view.findViewById(R.id.input_pincode)
        searchButton.setOnClickListener {
            if (choosenCity == null) {
                Toast.makeText(context, "Please Choose city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (choosenProfession == null) {
                Toast.makeText(context, "Please Select profession", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            NetworkUtils.pincode = pincodeEditText.text.toString()
                if(fragmentChangeInterface != null)
                    fragmentChangeInterface?.changeToResultFragment(city = choosenCity!!,profession = choosenProfession!!,pincode = pincodeEditText.text.toString())

        }

        return view
    }

    override fun onResume() {
        super.onResume()

    }


    companion object {
        const val EXTRA_CITIES = "EXTRA_CITIES"
        const val EXTRA_PROFESSION = "EXTRA_PROFESSION"
        fun newInstance(cities:ArrayList<City>,profession: ArrayList<Profession>): SearchFragment {
            val  fragment: SearchFragment = SearchFragment()
            val bundle = Bundle()
            bundle.putSerializable(EXTRA_CITIES,cities)
            bundle.putSerializable(EXTRA_PROFESSION, profession)
            fragment.arguments = bundle
            return fragment


        }
    }


    interface FragmentChangeInterface{
        fun changeToResultFragment(city: City,profession: Profession,pincode:String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragmentChangeInterface)
            fragmentChangeInterface = context
        else
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")

    }

    override fun onDetach() {
        super.onDetach()
        fragmentChangeInterface = null
    }


}