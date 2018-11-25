package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class RequestDetails {

    @SerializedName("address")
    public String address;
    @SerializedName("landMark")
    public String landMark;
    @SerializedName("area")
    public String area;
    @SerializedName("name")
    public String name;
    @SerializedName("mobileNumber")
    public String mobileNumber;
    @SerializedName("workDescription")
    public String workDescription;

}
