package raj.helpservice.android.helpservice.customviews

import com.google.gson.annotations.SerializedName

data class CreateService(
        @SerializedName("Id")var userId:String? = null,
        @SerializedName("typeId")var typeId:String? = null,
        @SerializedName("serviceTypeId")var serviceTypeId:String? = null,
        @SerializedName("cityId")var cityId: String? = null,
        @SerializedName("Pincode")var pincode: String ="",
        @SerializedName("Description") var description: String = "",
        @SerializedName("CityName") var cityName: String = ""

)
