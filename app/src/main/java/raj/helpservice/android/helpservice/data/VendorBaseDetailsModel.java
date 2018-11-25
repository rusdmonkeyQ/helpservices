package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yuriy Ozhyrko on 28.03.2018.
 * <p>
 * {"id":"B02AFEA4-7722-4276-9945-204A7623251A","totalUsersViewed":null,"enquiriesMade":"1","ratingsReceived":"4",
 * "subscriptionValidTill":"Jun-02-2018","vendorType":"Plumber","hasServiceRequest":true,"isMultiVendor":false}
 */

public class VendorBaseDetailsModel {

    @SerializedName("totalUsersViewed")
    public String totalUsersViewed;
    @SerializedName("enquiriesMade")
    public String enquiriesMade;
    @SerializedName("ratingsReceived")
    public String ratingsReceived;
    @SerializedName("subscriptionValidTill")
    public String subscriptionValidTill;
    @SerializedName("vendorType")
    public String vendorType;
    @SerializedName("hasServiceRequest")
    public boolean hasServiceRequest;
    @SerializedName("isMultiVendor")
    public boolean isMultiVendor;

}
