package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yuriy Ozhyrko on 28.03.2018.
 */

public class VendorRequest {

    @SerializedName("id")
    public String id;
    @SerializedName("pincode")
    public String pincode;
    @SerializedName("createdOn")
    public String createdOn;
    @SerializedName("vendorTypeName")
    public String vendorTypeName;
    @SerializedName("serviceTypeName")
    public String serviceTypeName;
    @SerializedName("serviceRequestID")
    public String serviceRequestID;
    @SerializedName("consumerName")
    public String consumerName;
    @SerializedName("mobileNumber")
    public String mobileNumber;

}
