package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class VendorSetupModel {
    public String id;
    @SerializedName("descripion")
    public String description;
    @SerializedName("serviceCharges")
    public String serviceCharges;
    @SerializedName("discountAmount")
    public String discountAmmount;
    @SerializedName("serviceRate")
    public String serviceRate;
    @SerializedName("rateServiceMasterID")
    public String rateServiceMasterID;
    @SerializedName("languageId")
    public String languageId;
    @SerializedName("serviceTypeID")
    public String serviceTypeID;
    @SerializedName("SetPrimary")
    public String setPrimary;
}
