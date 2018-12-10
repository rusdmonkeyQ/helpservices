package raj.helpservice.android.helpservice.fragment.vendor

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import android.util.Base64
import android.util.Log
import android.widget.AdapterView
import android.widget.Toast
import raj.helpservice.android.helpservice.data.DocumentModel
import raj.helpservice.android.helpservice.data.DocumentNameModel
import raj.helpservice.android.helpservice.data.UploadDocument
import java.io.ByteArrayOutputStream
import java.util.*


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
    private lateinit var viewModel: VendorViewModel
    private var documentNameModel = DocumentNameModel().apply {
        this.documentID = 1
        this.name = "Aadhar Card"
        this.selected ="N"
    }




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
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(VendorViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        viewLayout = inflater.inflate(R.layout.fragment_upload_document, container, false)
        viewLayout.spinner_documents.attachDataSource(UserPreference.getDocumentModel().map { it.name })
        viewLayout.spinner_documents.setOnItemSelectedListener(object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                documentNameModel = UserPreference.getDocumentModel()[position]
            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        })
        viewLayout.btn_choose_file.setOnClickListener{
            val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity!!.startActivityForResult(i, 12)
        }

        viewLayout.btn_update.setOnClickListener{
            if (mFilePath == null){
                Toast.makeText(context,"Please choose file",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val documentNumber =  viewLayout.et_document_no.text.toString()
            if (documentNumber.isEmpty()){
                Toast.makeText(context,"Please set document number ",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val imageBytes = getStringBytes(mFilePath!!)!!
            val documentModel = UploadDocument(id = UserPreference.getUser(context!!)?.userID!!,
            FileName = btn_choose_file.text.toString(),
            DocumentNo = documentNumber,
            UploadMessage = "", Imagebytes = imageBytes,documentID = documentNameModel.documentID)

            uploadDocument(documentModel)
        }
        return  viewLayout
    }






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

    fun uploadDocument(uploadDocument: UploadDocument){
        viewModel.uploadDocument(uploadDocument).observe(this, Observer {
            Log.e("ImageBytes",uploadDocument.Imagebytes)
            when {
                it?.isSuccess()== true -> {         activity!!.supportFragmentManager.popBackStackImmediate() }
                it?.isSuccess()== false -> Toast.makeText(context,"Not closed", Toast.LENGTH_SHORT).show()
                it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun setFilePath(filePath: String) {
        mFilePath = filePath
        viewLayout.btn_choose_file.setBackgroundColor(Color.GREEN)
        viewLayout.btn_choose_file.text = File(filePath).name
       // mTextDocument.setText("Upload Document: \n" + File(filePath).name)
    }

     fun getStringBytes(filePath: String): String?{
        val imageFile = File(filePath)
        if (!imageFile.exists())
            return null
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath) ?: return null

        return Base64.encodeToString(getBytesFromBitmap(bitmap), Base64.DEFAULT)

    }


     fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray()
    }
}
