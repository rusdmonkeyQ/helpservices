package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 *
 */

public class RateInterval {

    @SerializedName("serviceMasterID")
    public int serviceMasterID;
    @SerializedName("serviceName")
    public String serviceName;
    @SerializedName("selected")
    public String selected;

}
