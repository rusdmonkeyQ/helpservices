package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class SingleVendorDetails {
    @SerializedName("userID")
    public String userId;
    @SerializedName("averageRatings")
    public int averageRatings;
    @SerializedName("totalEnquiries")
    public int totalEnquiries;
    @SerializedName("serviceType")
    public String serviceType;
    @SerializedName("documentVerified")
    public String documentVerified;
    @SerializedName("registeredOn")
    public String registeredOn;
    @SerializedName("serviceCharges")
    public String serviceCharges;
    @SerializedName("discountAmount")
    public String discountAmount;
    @SerializedName("serviceRate")
    public String serviceRate;
    @SerializedName("serviceName")
    public String serviceName;
    @SerializedName("spokenLanguages")
    public String spokenLanguages;
    @SerializedName("workDescription")
    public String workDescription;
    @SerializedName("vendorName")
    public String vendorName;
    @SerializedName("vendorPhoto")
    public String vendorPhoto;
}
