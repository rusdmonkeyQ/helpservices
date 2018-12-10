package raj.helpservice.android.helpservice.api

import kotlinx.coroutines.experimental.Deferred
import raj.helpservice.android.helpservice.customviews.CreateService
import raj.helpservice.android.helpservice.data.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface{

    @GET("/api/values")
    fun getProfession():Deferred<ArrayList<Profession>>


    @GET("/api/values/getcities")
    fun getCities():Deferred<ArrayList<City>>

    @GET("/api/values/Search")
    fun getSearchConsumerWorker(@Query("id")id :String):Deferred<ArrayList<Worker>>

    @GET("/api/values/Search")
    fun getSearchWorkers(@Query("TypeId") typeId:String,@Query("CityId")
    cityId:String,@Query("PinArea")pinArea:String):Deferred<ArrayList<Worker>>

    @POST("/api/values/Login")
    fun loginUser(@Field("loginid")loginId:String,@Field("password")password:String):Deferred<User>


    @GET("/api/values/SingleVendor")
    fun getDetailInformation(@Query("id")workerId:String):Deferred<List<DetailedWorker>>

    @POST("/api/values/Validate")
    fun registerUser(@Body user:RegistrationUser):Deferred<RegistrationUser>

    @POST("api/values/VRegister")
    fun finishRegistration(@Body user : RegistrationUser):Deferred<RegistrationUser>

    @GET("/api/consumer/Home")
    fun getListConsumer(@Query("Id")id:String) : Deferred<ArrayList<ConsumerRequest>>


    @POST("/api/consumer/CloseRequest")
    fun closeRequest(@Body closeRequest: CloseRequest): Deferred<BaseResponse>

    @POST("/api/values/Login")
    fun logIn(@Body auth: Auth) : Deferred<List<RegistrationUser>>


    @GET("/api/consumer/PersonalDetails")
    fun getPersonalDetails(@Query("Id") id: String) : Deferred<Personal>

    @POST("/api/consumer/PersonalDetails")
    fun sendPersonalDetails(@Body personal: PersonalSending): Deferred<BaseResponse>

    @GET("api/consumer/Address")
    fun getAddress(@Query("Id")id: String) : Deferred<Address>

    @POST("api/consumer/Address")
    fun sendAdress(@Body address: AdressSending) :Deferred<BaseResponse>

    @POST("/api/Consumer/CreateService")
    fun sendCreateService(@Body createService: CreateService): Deferred<BaseResponse>

    @GET("/api/vendor/Home")
    fun getVendorDetails(@Query("Id") id: String):Deferred<VendorBaseDetailsModel>

    @GET("/api/vendor/OpenRequests")
    fun getRequestList(@Query("Id") id:String): Deferred<ArrayList<RequestedService>>

    @GET("/api/vendor/ConsumerDetails")
    fun getDialogInformation(@Query("Requestid") id: String): Deferred<RequestDetails>

    @GET("/api/vendor/Setup")
    fun getSetUp(@Query("Id") id: String): Deferred<VendorSetupModel>

    @POST("/api/vendor/Setup")
    fun sendSetUp(@Body vendorSetupModel: VendorSetupModel): Deferred<BaseResponse>

    @POST("/api/vendor/VSetup")
    fun sendLangWorkDesc(@Body vendorSetupModel: VendorLanguageText): Deferred<BaseResponse>

    @GET("/api/vendor/addedrates")
    fun getRates(@Query("Id") id: String): Deferred<ArrayList<AddedRatesModel>>

    @GET("/api/values/SingleVendor")
    fun getDetailedInformationVendor(@Query("id") id: String): Deferred<DetailedWorker>

    @GET("/api/vendor/uploadeddocs")
    fun getDocuments(@Query("Id") id:String): Deferred<ArrayList<DocumentModel>>

    @GET("/api/vendor/Languages")
    fun getLanguages(@Query("Id") id :String ): Deferred<ArrayList<Language>>

    @POST("/api/vendor/closerate")
    fun closeRate(@Query("ServiceId") serviceId:String): Deferred<Void>

    @POST(" /api/vendor/UploadDoc")
    fun uploadDocument(@Body uploadDocument: UploadDocument): Deferred<BaseResponse>

}