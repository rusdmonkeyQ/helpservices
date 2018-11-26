package raj.helpservice.android.helpservice.fragment.vendor

import android.arch.lifecycle.ViewModel;
import raj.helpservice.android.helpservice.Repository
import raj.helpservice.android.helpservice.data.VendorLanguageText
import raj.helpservice.android.helpservice.data.VendorSetupModel

class VendorViewModel : ViewModel() {
    val repository = Repository()

    fun getVendorInformation(id: String) = repository.getDetailInformation(id)

    fun getListOpenRequests(id: String) = repository.getAllOpenRequest(id)

    fun getDetailInformation(id: String) = repository.getDetails(id)

    fun getSetUpInformation(id: String) = repository.getSetUp(id)

    fun sendDataToServer(vendorSetupModel: VendorSetupModel) = repository.sendSetUp(vendorSetupModel)

    fun getRates(id: String) = repository.getRatest(id)

    fun getDetailedInformationVendor(id:String) = repository.getDetailedUserInformation(id)

    fun sendLangWorkDesc(vendorSetupModel: VendorLanguageText) = repository.sendLangAndWork(vendorSetupModel)

    fun getDocuments(id: String)  = repository.getDocuments(id)
}
