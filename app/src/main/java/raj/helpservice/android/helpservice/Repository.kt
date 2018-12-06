package raj.helpservice.android.helpservice

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.LiveData
import android.support.annotation.MainThread
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import raj.helpservice.android.helpservice.api.ApiInterface
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.customviews.CreateService
import raj.helpservice.android.helpservice.data.*


class Repository {
    private var webservice: ApiInterface? = null

    init {
        webservice = HelpServiceApi.getApiService()
    }

    /***
     * Auth Service
     */
    fun login(auth: Auth): LiveData<RegistrationUser> {
        val data = MutableLiveData<RegistrationUser>()
        var returnedUser: List<RegistrationUser>?
        try {
            launch(UI) {
                returnedUser = webservice?.logIn(auth)?.await()
                data.value = returnedUser!![0]
            }
        }catch (e:Exception){

        }

        return data
    }


    fun getUser(user:RegistrationUser): LiveData<RegistrationUser> {
        val data = MutableLiveData<RegistrationUser>()
        var returnedUser:RegistrationUser?
        try {
            launch(UI) {
                returnedUser = webservice?.registerUser(user)?.await()
                data.value = returnedUser
            }
        }catch (e:Exception){

        }

        return data
    }


    fun finishRegistration(user: RegistrationUser): LiveData<RegistrationUser>{
        val data = MutableLiveData<RegistrationUser>()
        var returnedUser: RegistrationUser?
        try {
            launch(UI) {
                returnedUser = webservice?.finishRegistration(user)?.await()
                data.value = returnedUser
            }
        }catch (e:Exception){

        }

        return data
    }

    /***
     * Consumer Request
     */

    fun getListConsumers(id :String) :LiveData<ArrayList<ConsumerRequest>>{
        val data = MutableLiveData<ArrayList<ConsumerRequest>>()
        launch(UI) {
            val consumers = webservice?.getListConsumer(id)?.await()
            data.value = consumers
        }

        return data
    }

    fun closeRequest(closeRequest: CloseRequest): MutableLiveData<BaseResponse>{
        val data = MutableLiveData<BaseResponse>()
        launch(UI) {
            val consumers = webservice?.closeRequest(closeRequest)?.await()
            data.value = consumers
        }

        return data
    }

    fun getConsumerPersonal(id :String) : LiveData<Personal>{
        val data = MutableLiveData<Personal>()
        launch(UI) {
            val consumerRequest  = webservice?.getPersonalDetails(id)?.await()
            data.value = consumerRequest
        }
        return data
    }

    fun sendPersonalDetails(personal: PersonalSending) : MutableLiveData<BaseResponse> {
        val data = MutableLiveData<BaseResponse>()
        launch(UI) {
            val base= webservice?.sendPersonalDetails(personal)?.await()
            data.value = base
        }
        return data
    }

    fun getConsumerAddress(id :String) : LiveData<Address>{
        val data = MutableLiveData<Address>()
        launch(UI) {
            val consumerRequest  = webservice?.getAddress(id)?.await()
            data.value = consumerRequest
        }
        return data
    }

    fun sendPersonalAddress(address: AdressSending) : MutableLiveData<BaseResponse> {
        val data = MutableLiveData<BaseResponse>()
        launch(UI) {
            val base= webservice?.sendAdress(address)?.await()
            data.value = base
        }
        return data
    }


    fun createService(createService: CreateService):MutableLiveData<BaseResponse>{
        val data = MutableLiveData<BaseResponse>()
        launch(UI) {
            val base = webservice?.sendCreateService(createService)?.await()
            data.value  = base
        }
        return data
    }
    /***
     * Vendor Request
     */

    fun getDetailInformation(id: String): MutableLiveData<VendorBaseDetailsModel>{
        val data = MutableLiveData<VendorBaseDetailsModel>()
        launch(UI) {
            val base = webservice?.getVendorDetails(id)?.await()
            data.value  = base
        }
        return data
    }

    fun getAllOpenRequest(id :String): MutableLiveData<ArrayList<RequestedService>> {
        val data = MutableLiveData<ArrayList<RequestedService>>()
        launch(UI) {
            val base = webservice?.getRequestList(id)?.await()
            data.value  = base
        }
        return data
    }

    fun getDetails(requestId: String): MutableLiveData<RequestDetails>{
        val data = MutableLiveData<RequestDetails>()
        launch(UI) {
            val base = webservice?.getDialogInformation(requestId)?.await()
            data.value  = base
        }
        return data

    }

    fun getSetUp(id :String): MutableLiveData<VendorSetupModel> {
        val data = MutableLiveData<VendorSetupModel>()
        launch(UI) {
            val base = webservice?.getSetUp(id)?.await()
            data.value  = base
        }
        return data
    }


    fun sendSetUp(vendorSetupModel: VendorSetupModel):MutableLiveData<BaseResponse>{
        val data = MutableLiveData<BaseResponse>()
        launch(UI) {
            val base = webservice?.sendSetUp(vendorSetupModel)?.await()
            data.value  = base
        }
        return data
    }

    fun getRatest(id :String): MutableLiveData<ArrayList<AddedRatesModel>> {
        val data = MutableLiveData<ArrayList<AddedRatesModel>>()
        launch(UI) {
            val base = webservice?.getRates(id)?.await()
            data.value  = base
        }
        return data
    }

    fun getDetailedUserInformation(id: String){
        val data = MutableLiveData<DetailedWorker>()
        launch {
            val base = webservice?.getDetailedInformationVendor(id)?.await()
            data.value = base
        }
    }

    fun sendLangAndWork(vendorSetupModel: VendorLanguageText):MutableLiveData<BaseResponse>{
        val data = MutableLiveData<BaseResponse>()
        launch(UI) {
            val base = webservice?.sendLangWorkDesc(vendorSetupModel)?.await()
            data.value  = base
        }
        return data
    }

    fun getLanguage(id: String): MutableLiveData<ArrayList<Language>>{
        val data = MutableLiveData<ArrayList<Language>>()
        launch(UI) {
            val base = webservice?.getLanguages(id)?.await()
            data.value = base
        }
        return data
    }

    fun getDocuments(id: String): MutableLiveData<ArrayList<DocumentModel>>{
        val data = MutableLiveData<ArrayList<DocumentModel>>()
        launch(UI) {
            val base = webservice?.getDocuments(id)?.await()
            data.value = base
        }
        return data
    }
}