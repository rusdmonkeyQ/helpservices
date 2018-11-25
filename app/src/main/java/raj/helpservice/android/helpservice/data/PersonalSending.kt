package raj.helpservice.android.helpservice.data

import com.google.gson.annotations.SerializedName

data class PersonalSending(@SerializedName("Id") var id :String, @SerializedName("name") var name :String,
                           @SerializedName("AlterMobileNo") var alterMobileNo:String, @SerializedName("Landline") var landline:String,
                           @SerializedName("Email") var email :String){

}