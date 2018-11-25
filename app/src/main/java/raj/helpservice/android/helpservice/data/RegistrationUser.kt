package raj.helpservice.android.helpservice.data

import com.google.gson.annotations.SerializedName

data class RegistrationUser(@SerializedName("mobileNumber")var mobileNumber:String? = null,
                       @SerializedName("name") var name:String?  = null,
                       @SerializedName("password") var password:String? = null,
                       @SerializedName("isRegistered") var isRegistered:String? = null,
                       @SerializedName("userID")var userID:String? = null,
                       @SerializedName("otpNumber")var otpNumber:String? = null,
                       @SerializedName("userType") var userType:String? = null,
                       @SerializedName("cityId")  var cityId:String? = null,
                       @SerializedName("pincode")  var pincode:String?= "",
                       @SerializedName("displayMessage")  var displayMessage:String? = "",
                       @SerializedName("serviceTypeId")  var serviceTypeId:String? = "")


