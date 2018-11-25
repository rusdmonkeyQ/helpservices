package raj.helpservice.android.helpservice.data;

import com.google.gson.annotations.SerializedName;

/**
 */

public class MultiVendorDetails {
    /*
    "userID": "68AB4932-521A-4183-B346-348A75671D02",
"shopName": "Raju Electricals",
"address": "N0 12/A, DVG Road",
"landMark": "Near Ice Thunder",
"area": "Gandhi Bazaar",
"cityName": "Bengaluru",
"pinCode": "560085",
"firstName": "Raju Gowda",
"lastName": "Gowda",
"emailAddress": "",
"mobileNumber": "9448048195",
"alternateMobileNumber": "9886039655",
"landlineNumber": "08026123416",
"offerStartsOn": "2018-02-02",
"offerEndsOn": "2018-06-02",
"offerDescription": "10% discount on Wires",
"offerMinBillingAmount": " Rs",
"offerMaxAmount": "0.0000 Rs",
"description": "We supply all kind of Electrical items"
     */

    @SerializedName("userID")
    public String userId;
    @SerializedName("shopName")
    public String shopName;
    @SerializedName("address")
    public String address;
    @SerializedName("landMark")
    public String landMark;
    @SerializedName("area")
    public String area;
    @SerializedName("cityName")
    public String cityName;
    @SerializedName("pinCode")
    public String pinCode;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("mobileNumber")
    public String mobileNumber;
    @SerializedName("landlineNumber")
    public String landlineNumber;

    @SerializedName("offerStartsOn")
    public String offerStartsOn;
    @SerializedName("offerEndsOn")
    public String offerEndsOn;
    @SerializedName("offerDescription")
    public String offerDescription;
    @SerializedName("offerMinBillingAmount")
    public String offerMinBillingAmount;
    @SerializedName("offerMaxAmount")
    public String offerMaxAmount;
    @SerializedName("description")
    public String description;
}
