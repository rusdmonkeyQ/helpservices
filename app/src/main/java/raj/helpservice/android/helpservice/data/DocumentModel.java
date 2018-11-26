package raj.helpservice.android.helpservice.data;

/**
 *
 */

import com.google.gson.annotations.SerializedName;

public class DocumentModel {
    @SerializedName("documentID")
    public String documentID;
    @SerializedName("documentName")
    public String documentName;
    @SerializedName("documentNo")
    public String documentNo;
    @SerializedName("isAadhar")
    public boolean isAadhar;
    @SerializedName("docVerified")
    public String docVerified;
}
