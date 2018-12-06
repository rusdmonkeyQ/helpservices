package raj.helpservice.android.helpservice.fragment.vendor

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_upload_document.*
import kotlinx.android.synthetic.main.fragment_upload_document.view.*
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.activity.VendorActivity
import raj.helpservice.android.helpservice.spstorage.UserPreference
import java.io.File


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [UploadDocumentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [UploadDocumentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class UploadDocumentFragment : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var viewLayout : View
    private var mFilePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onStart() {
        super.onStart()
        checkPermission()
    }
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    private fun checkPermission() {
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewLayout = inflater.inflate(R.layout.fragment_upload_document, container, false)
        viewLayout.spinner_documents.attachDataSource(UserPreference.getDocumentModel().map { it.name })
        viewLayout.btn_choose_file.setOnClickListener{
            val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity!!.startActivityForResult(i, 12)
        }

        viewLayout.btn_update.setOnClickListener{

        }
        return  viewLayout
    }

    // TODO: Rename method, update argument and hook method into UI event





    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UploadDocumentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = UploadDocumentFragment()
    }


    fun setFilePath(filePath: String) {
        mFilePath = filePath
        viewLayout.btn_choose_file.setBackgroundColor(Color.GREEN)
        viewLayout.btn_choose_file.text = File(filePath).name
       // mTextDocument.setText("Upload Document: \n" + File(filePath).name)
    }
}
