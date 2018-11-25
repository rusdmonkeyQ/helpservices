package raj.helpservice.android.helpservice.data

import com.google.gson.annotations.SerializedName

data class User(
        @SerializedName("UserID") val UserId:String, @SerializedName("LoginMessage")val loginMessage:String,
        @SerializedName("UserType")val userType:String){

}