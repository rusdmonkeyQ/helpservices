package raj.helpservice.android.helpservice.data

import com.google.gson.annotations.SerializedName

class ConsumerRequest{
    /*
    "id": "9C7EED7D-889B-40AF-9614-0AAF0D76437C",
"createdOn": "2018-02-23",
"pincode": "560085",
"status": "Open",
"vendorTypeName": "Electrical Appliances",
"serviceTypeName": "Contract",
"vendorName": null,
"vendorPhone": null,
"vendorSelected": "0",
"serviceRequestID": "9a24011c-85ed-41ed-8e8e-4df0f04b3800"
     */
    @SerializedName("id")
    val id: String? = null
    @SerializedName("pincode")
    val pincode: String? = null
    @SerializedName("status")
    var status: String? = null
        private set
    @SerializedName("createdOn")
    val createdOn: String? = null
    @SerializedName("vendorTypeName")
    val vendorTypeName: String? = null
    @SerializedName("serviceTypeName")
    val serviceTypeName: String? = null
    val vendorPhone: String? = null
    val vendorName: String? = null
    @SerializedName("serviceRequestID")
    val serviceRequestID: String? = null
    @SerializedName("vendorSelected")
    val vendorSelected: String? = null

    fun setStatusClosed() {
        this.status = STATUS_CLOSED
    }

    companion object {

        val STATUS_OPEN = "Open"
        val STATUS_CLOSED = "Closed"
    }
}
