package raj.helpservice.android.helpservice.data

import com.google.gson.annotations.SerializedName

class BaseResponse{
    val DEFAULT_ERROR = "Unknown error"

    // {"responseStatus":"Failed","isSuccess":false,"errorCode":null,"errorMessage":"Unable to Close service request.","actualErrorMessage":"System.NullReferenceException: Object reference not set to an instance of an object.\r\n   at BusinessDataManagement.ConsumerRepository.CloseConsumerRequest(clsCloseRequest objCls) in C:\\Users\\Santosh\\source\\repos\\ConstroAPI\\BusinessDataManagement\\ConsumerRepository.cs:line 214"}
    @SerializedName("responseStatus")
    private val responseStatus: String? = null
    @SerializedName("isSuccess")
    private val isSuccess: Boolean = false
    @SerializedName("errorMessage")
    private val errorMessage: String? = null
    @SerializedName("actualErrorMessage")
    private val actualErrorMessage: String? = null

    fun getResponseStatus(): String? {
        return responseStatus
    }

    fun isSuccess(): Boolean {
        return isSuccess
    }

    fun getErrorMessage(): String? {
        return errorMessage
    }

    fun getActualErrorMessage(): String? {
        return actualErrorMessage
    }
}