package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class RequestedService {

    public static final String STATUS_OPEN = "Open";
    public static final String STATUS_CLOSED = "Closed";
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
    private String id;
    @SerializedName("pincode")
    private String pincode;
    @SerializedName("status")
    private String status;
    @SerializedName("createdOn")
    private String createdOn;
    @SerializedName("vendorTypeName")
    private String vendorTypeName;
    @SerializedName("serviceTypeName")
    private String serviceTypeName;
    @SerializedName("serviceRequestID")
    private String serviceRequestID;
    @SerializedName("vendorSelected")
    private int vendorSelected;
    private String consumerName;
    private String mobileNumber;

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public String getPincode() {
        return pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatusClosed() {
        this.status = STATUS_CLOSED;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getVendorTypeName() {
        return vendorTypeName;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public String getServiceRequestID() {
        return serviceRequestID;
    }

    public int getVendorSelected() {
        return vendorSelected;
    }
}
