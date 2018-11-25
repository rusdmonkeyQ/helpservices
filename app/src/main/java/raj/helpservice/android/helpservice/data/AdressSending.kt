package raj.helpservice.android.helpservice.data

import com.google.gson.annotations.SerializedName

class AdressSending{
    @SerializedName("Id")
    var id: String? = null
    @SerializedName("LandMark")
    var landMark: String? = null
    @SerializedName("Area")
    var area: String? = null
    @SerializedName("Pincode")
    var pincode: String? = null
    @SerializedName("StateID")
    var stateId: String? = null
    @SerializedName("CityID")
    var cityId: String? = null
    @SerializedName("Newcity")
    var newcity: String? = null
    @SerializedName("Address")
    var address: String? = null
    @SerializedName("longitude")
    var longitude: String? = null
    @SerializedName("lattitude")
    var lattitude: String? = null
    @SerializedName("addressType")
    var addressType: String? = null
}
