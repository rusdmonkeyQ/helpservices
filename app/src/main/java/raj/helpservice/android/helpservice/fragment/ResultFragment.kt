package raj.helpservice.android.helpservice.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.*
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.adapter.FragmentAdapter
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.Worker
import raj.helpservice.android.helpservice.spstorage.UserPreference

class ResultFragment :Fragment(){

    lateinit var viewPager: ViewPager
    lateinit var tabs: TabLayout
    lateinit var city: City
    lateinit var pincode: String
    val retrofit by lazy {
        HelpServiceApi.getApiService()
    }

    var fragmentChange:ResulFragmentChange? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_result,container,false)
        viewPager = view.findViewById(R.id.viewpager)
        tabs = view.findViewById(R.id.result_tabs)
        val profession:Profession = arguments?.getSerializable(EXTRA_PROFFESSION) as Profession
        city = arguments?.getSerializable(EXTRA_CITY) as City
        pincode = arguments?.getSerializable(EXTRA_PINCODE) as String
        setupViewPager(viewPager,profession)
        tabs.setupWithViewPager(viewPager)
        return  view
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        return inflater!!.inflate(R.menu.menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.menu_change){
            fragmentChange?.changeToSearchFragment()
            return true
        }
        return false

    }


    companion object {
        const val EXTRA_WORKERS = "extra_worker"
        const val EXTRA_PROFFESSION = "extra_profession"
        const val EXTRA_CITY = "extra_city"
        const val EXTRA_PINCODE = "extra_pincode"

        fun newInstance(city:City,profession: Profession,pincode:String):ResultFragment{
            val fragment = ResultFragment()
            fragment.arguments = Bundle().also {
            it.putSerializable(EXTRA_PROFFESSION,profession)
            it.putSerializable(EXTRA_CITY,city)
            it.putString(EXTRA_PINCODE,pincode)}
            return fragment
        }
    }

    fun setupViewPager(viewPager: ViewPager,profession: Profession){
        val professions = UserPreference.getProfessions(context!!)
        val adapter  = FragmentAdapter(childFragmentManager,context!!)
        adapter.addFragment(ListFragment.newInstance(city,profession, pincode),profession.serviceName)
        for(p in professions!!){
            if (p.serviceName==profession.serviceName || p.serviceTypeID == profession.serviceTypeID)
                continue
            adapter.addFragment(ListFragment.newInstance(city,p, pincode),p.serviceName)
        }

        viewPager.adapter = adapter
    }

    interface ResulFragmentChange{
        fun changeToSearchFragment()
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ResultFragment.ResulFragmentChange)
            fragmentChange = context
        else
            throw RuntimeException("Fragment change")
    }

    override fun onDetach() {
        super.onDetach()
        fragmentChange = null
    }
}