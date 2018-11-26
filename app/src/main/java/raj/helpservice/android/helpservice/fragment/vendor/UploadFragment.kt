package raj.helpservice.android.helpservice.fragment.vendor


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import kotlinx.android.synthetic.main.fragment_upload.*
import kotlinx.android.synthetic.main.fragment_upload.view.*
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.VendorActivity
import raj.helpservice.android.helpservice.adapter.DocumentsAdapter
import raj.helpservice.android.helpservice.data.DocumentModel
import raj.helpservice.android.helpservice.spstorage.UserPreference


// TODO: Rename parameter arguments, choose names that match


/**
 * A simple [Fragment] subclass.
 * Use the [UploadFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UploadFragment : Fragment(),DocumentsAdapter.OnItemClickListener {
    override fun onItemClick(item: DocumentModel?, position: Int) {

    }

    // TODO: Rename and change types of parameters
    private lateinit var documentAdapter: DocumentsAdapter
    private lateinit var viewModel: VendorViewModel
    private lateinit var viewLayout: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VendorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewLayout = inflater.inflate(R.layout.fragment_upload, container, false)
        documentAdapter  = DocumentsAdapter(this)
        val linelayoutManager = LinearLayoutManager(context)
        viewLayout.recycler_document.layoutManager = linelayoutManager
        viewLayout.recycler_document.adapter = documentAdapter
        return viewLayout
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.upload_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val vendorActivity = activity!! as VendorActivity
        vendorActivity.replaceFragment(UploadDocumentFragment.newInstance())
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        downloadDocuments()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UploadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = UploadFragment()
    }

    fun downloadDocuments(){
        val user = UserPreference.getUser(context!!)
        viewModel.getDocuments(user?.userID!!).observe(this, Observer {
            documentAdapter.changeValues(it)
            if (it?.size == 0){

            }
        })
    }
}

