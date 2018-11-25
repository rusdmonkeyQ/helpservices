package raj.helpservice.android.helpservice.data

import java.io.Serializable

data class Worker(val userID:String , val shopName:String, val
address:String, val area:String, val pinCode:String, val hasOffer:Int,
                   val singleVendor:String, val spokenLanguages:String,
                   val offerDescription:String, val longitude:String ,
                  val lattitude:String,val addressType:String):Serializable