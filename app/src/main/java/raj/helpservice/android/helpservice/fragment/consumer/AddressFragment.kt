package raj.helpservice.android.helpservice.fragment.consumer

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.*
import android.widget.*
import com.google.android.gms.maps.MapView
import org.angmarch.views.NiceSpinner

import raj.helpservice.android.helpservice.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.activity.ConsumerActivity
import raj.helpservice.android.helpservice.data.Address
import raj.helpservice.android.helpservice.data.AdressSending
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.spstorage.UserPreference
import java.util.*
import android.location.LocationListener
import android.text.method.TextKeyListener.clear
import android.location.LocationManager
import android.support.annotation.NonNull
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.kotlinpermissions.KotlinPermissions


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddressFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    lateinit var fullAdress : TextInputEditText
    lateinit var landMark : TextInputEditText
    lateinit var pincode :TextInputEditText
    lateinit var area : TextInputEditText
    lateinit var citySpinner: NiceSpinner
    lateinit var adressContainer : LinearLayout
    lateinit var mapContainer: RelativeLayout
    lateinit var buttonContainer: LinearLayout
    lateinit var textShowMap : TextView
    lateinit var defaultText: TextView
    lateinit var saveButton: Button
    lateinit var cancelButton: Button
    lateinit var mapView: MapView

    lateinit var getCoder: Geocoder
    lateinit var progressBar:ProgressBar
    var cities = NetworkUtils.cities
    var latitude:String? = null
    var longitute:String? = null
    var choosenCity: City? = null
    private lateinit var viewModel: ConsumerViewModel
    var mapShowed = false
    var isConsumer = true
    private var mGoogleMap: GoogleMap? = null
    private var mLocationManager: LocationManager? = null
    private var mLastKnownLocation: Location? = null


    val LOCATION_UPDATE_MIN_DISTANCE = 50.toFloat()
    val LOCATION_UPDATE_MIN_TIME = 9000.toLong()
    private val mDefaultLocation = LatLng(-33.8523341, 151.2106085)

    private val mLikelyPlaceNames: Array<String>? = null
    private val mLikelyPlaceAddresses: Array<String>? = null
    private val mLikelyPlaceAttributions: Array<String>? = null
    private val mLikelyPlaceLatLngs: Array<LatLng>? = null

    var editable = false



    private val mLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location?) {
            if (location != null) {
                Log.d("Location",String.format("%f, %f", location.latitude, location.longitude))
                drawMarker(location)
                setInEditText(location)
                mLocationManager!!.removeUpdates(this)
            } else {
                Log.e("Location","Location is null")
            }
        }

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {

        }

        override fun onProviderEnabled(s: String) {

        }

        override fun onProviderDisabled(s: String) {

        }
    }

    fun setInEditText(location: Location) {
        latitude = location.latitude.toString()
        longitute = location.longitude.toString()
        val addresses = getCoder.getFromLocation(location.latitude, location.longitude, 1)
        val address: String? = addresses.get(0).getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        fullAdress.setText(address)
        val city: String? = addresses.get(0).getLocality()
        val state: String? = addresses.get(0).getAdminArea()
        val country: String? = addresses.get(0).getCountryName()
        val postalCode: String? = addresses.get(0).getPostalCode()
       // if (postalCode?.isNotEmpty()) {
      //      val postalReplace = postalCode?.replace("-","")
        //    pincode.setText(postalReplace)
        //}
        val knownName: String? = addresses.get(0).getFeatureName()
        val areaS: String? = addresses[0].subAdminArea
        area.setText("State ${state} area ${areaS}")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)

        // Construct a GeoDataClient.
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.consumer_personal,menu)
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        editableVisibility()
        return true
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        isConsumer = activity!! is ConsumerActivity
        val view = inflater.inflate(R.layout.fragment_address, container, false)
        fullAdress = view.findViewById(R.id.full_address)
        landMark = view.findViewById(R.id.land_mark)
        pincode = view.findViewById(R.id.pincode_adress)
        area = view.findViewById(R.id.area_adress)
        saveButton  = view.findViewById(R.id.save_button)
        cancelButton = view.findViewById(R.id.cancel_button)

        if (isConsumer)
        progressBar = activity?.findViewById<ProgressBar>(R.id.consumer_progress)!!
        else
            progressBar = activity?.findViewById<ProgressBar>(R.id.vendor_progress)!!

        mLocationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        saveButton.setOnClickListener {
            if (mapShowed){
                mapVisibility()
                getCurrentLocation()
                return@setOnClickListener
            }
            if (choosenCity == null){
                Toast.makeText(context!!,"Please choose city",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val address = AdressSending()
            address.id = UserPreference.getUser(context!!)?.userID
            address.address = fullAdress.text.toString()
            address.area = area.text.toString()
            address.cityId = choosenCity?.cityID
            address.landMark = landMark.text.toString()
            address.lattitude = latitude
            address.longitude = longitute
            address.pincode = pincode.text.toString()
            address.stateId = "1"
            address.newcity =""
            sendData(address)
        }
        cancelButton.setOnClickListener {
            editableVisibility()
        }

        citySpinner = view.findViewById(R.id.spinner_city_adress)
        citySpinner.setOnItemSelectedListener(object :AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                choosenCity = cities!![position]
                citySpinner.selectedIndex = position

            }

            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        })
        citySpinner.dropDownListPaddingBottom = 300

        adressContainer = view.findViewById(R.id.edit_text_container)
        mapContainer = view.findViewById(R.id.map_container)
        buttonContainer = view.findViewById(R.id.button_container)
        textShowMap = view.findViewById(R.id.show_map_adress)
        textShowMap.setOnClickListener {
            mapVisibility()
        }
        defaultText = view.findViewById(R.id.default_text_adress)
        mapView = view.findViewById(R.id.mapView_adress)
        mapView.onCreate(savedInstanceState)
        getCoder = Geocoder(context, Locale.getDefault())


        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Throwable) {
            e.printStackTrace()
        }



        mapView.getMapAsync { mMap ->
            mGoogleMap = mMap
            initMap()
            getCurrentLocation()
            // Prompt the user for permission.


      //      getLocationPermission()

            // Turn on the My Location layer and the related control on the map.
         //   updateLocationUI()

            // Get the current location of the device and set the position of the map.
         //   getCurrentLocation()
        //    getDeviceLocation()
            }




        return view
    }

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: Int = 12

    private fun getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    if (ContextCompat.checkSelfPermission(context!!.applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
        mLocationPermissionGranted = true;
    } else {
        ActivityCompat.requestPermissions(activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

}


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ->{
                if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    mLocationPermissionGranted = true
            }
        }
        updateLocationUI()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsumerViewModel::class.java)
        citySpinner.attachDataSource(NetworkUtils.cities?.map { it.cityName })
        downloadAdressAndSet()

    }

    fun mapVisibility() {
        mapShowed = !mapShowed
        mapContainer.visibility = if (mapShowed) View.VISIBLE else View.GONE
        adressContainer.visibility = if(mapShowed) View.GONE else View.VISIBLE
    }

    fun editableVisibility(){
        editable = !editable
        setVisibilityContainer(textShowMap)
        setVisibilityContainer(defaultText)
        setVisibilityContainer(buttonContainer)
        setEditable(fullAdress)
        setEditable(landMark)
        setEditable(pincode)
        setEditable(area)
        setEditable(citySpinner)

        if(mapShowed){
            mapVisibility()
        }
    }

    fun setVisibilityContainer(viewGroup:View){
        viewGroup.visibility = if (editable) View.VISIBLE else View.INVISIBLE
    }


    fun setEditable(view: View){
        view.isEnabled = editable
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    fun downloadAdressAndSet(){
        progressBar.visibility = View.VISIBLE
        val user = UserPreference.getUser(context!!)
        viewModel.getAddress(user?.userID!!).observe(this,android.arch.lifecycle.Observer { address ->
            progressBar.visibility = View.GONE
            fullAdress.setText(address?.address)
            area.setText(address?.area)
            landMark.setText(address?.landMark)
            pincode.setText(address?.pincode)
        })
    }

    fun sendData(address: raj.helpservice.android.helpservice.data.AdressSending){
        progressBar.visibility = View.VISIBLE
        viewModel.sendAddress(address = address).observe(this, android.arch.lifecycle.Observer {
            progressBar.visibility = View.VISIBLE
            when {
                it?.isSuccess()== true -> {downloadAdressAndSet()
                editableVisibility()}
                it?.isSuccess()== false -> Toast.makeText(context,"Data not saved ", Toast.LENGTH_SHORT).show()
                it?.getResponseStatus() !=  "Success" -> Toast.makeText(context,it?.getErrorMessage(), Toast.LENGTH_SHORT).show()
            }
        })
    }


    @SuppressLint("MissingPermission")
    fun initMap() {
		val googlePlayStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context)
		if (googlePlayStatus != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(googlePlayStatus, activity, -1).show()
		} else {
			if (mGoogleMap != null) {
				mGoogleMap!!.setMyLocationEnabled(true);
				mGoogleMap!!.getUiSettings().setMyLocationButtonEnabled(true);
				mGoogleMap!!.getUiSettings().setAllGesturesEnabled(true);
			}
		}
	}

    private var mLocationPermissionGranted: Boolean = false

    private fun updateLocationUI() {
        if (mGoogleMap == null) {
            return
        }
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap?.setMyLocationEnabled(true)
                mGoogleMap?.getUiSettings()?.setMyLocationButtonEnabled(true)
            } else {
                mGoogleMap?.setMyLocationEnabled(false)
                mGoogleMap?.getUiSettings()?.setMyLocationButtonEnabled(false)
                mLastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }

    }
//    private fun getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (mLocationPermissionGranted){
//                val locationResult = mFusedLocationProviderClient!!.lastLocation
//                locationResult.addOnCompleteListener(activity!!) {task ->
//                    if (task.isSuccessful){
//                        mLastKnownLocation = task.result
//                        mGoogleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mLastKnownLocation!!.latitude, mLastKnownLocation!!.longitude),15f))
//         //
//                    }
//                    else {
//                        mGoogleMap!!.moveCamera(CameraUpdateFactory
//                                .newLatLngZoom(mDefaultLocation, 15f));
//                        mGoogleMap!!.getUiSettings().setMyLocationButtonEnabled(false);
//                    }
//                }
//            }
//        }catch (e:SecurityException){
//
//        }
//    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AddressFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        updateLocationUI()
        getCurrentLocation()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        mLocationManager!!.removeUpdates(mLocationListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

  fun drawMarker(location: Location) {
		if (mGoogleMap != null) {
			mGoogleMap!!.clear()
			val gps = LatLng(location.getLatitude(), location.getLongitude());
			mGoogleMap!!.addMarker(MarkerOptions()
					.position(gps)
					.title("Current Position"))
			mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12.toFloat()))

		}

	}


    @SuppressLint("MissingPermission")
   private fun getCurrentLocation() {
       if (mLocationPermissionGranted) {
           val isGPSEnabled = mLocationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
           val isNetworkEnabled = mLocationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

           var location: Location? = null
           if (!(isGPSEnabled || isNetworkEnabled))
               Snackbar.make(mapView, "GPS not enabled", Snackbar.LENGTH_LONG).show()
           else {
               if (isNetworkEnabled) {
                   mLocationManager!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                           LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener)
                   location = mLocationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
               }

               if (isGPSEnabled) {
                   mLocationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                           LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, mLocationListener)
                   location = mLocationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
               }
           }
           if (location != null) {
               Log.e("TAG", location.toString())
               drawMarker(location)
               setInEditText(location)
           }
       }
   }

}
