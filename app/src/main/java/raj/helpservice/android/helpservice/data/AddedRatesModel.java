package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 */

public class AddedRatesModel implements Serializable {

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
