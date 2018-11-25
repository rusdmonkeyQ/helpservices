package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class AddedRatesModel {

    @SerializedName("serviceID")
    public String serviceID;
    @SerializedName("serviceCharges")
    public String serviceCharges;
    @SerializedName("discountAmount")
    public String discountAmount;
    @SerializedName("serviceRate")
    public String serviceRate;
    @SerializedName("serviceName")
    public String serviceName;
    @SerializedName("serviceType")
    public String serviceType;

}
