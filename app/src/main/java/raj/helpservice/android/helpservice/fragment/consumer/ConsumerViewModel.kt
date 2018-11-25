package raj.helpservice.android.helpservice.fragment.consumer

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import raj.helpservice.android.helpservice.Repository
import raj.helpservice.android.helpservice.data.*

class ConsumerViewModel : ViewModel() {

    val repository = Repository()


    fun getConsumerList(id :String) = repository.getListConsumers(id)

    fun closeRequest(closeRequest: CloseRequest) = repository.closeRequest(closeRequest)

    fun getPersonalDetails(id : String) = repository.getConsumerPersonal(id)

    fun sendPersonalDetails(personal: PersonalSending)= repository.sendPersonalDetails(personal)

    fun getAddress(id: String) = repository.getConsumerAddress(id)

    fun sendAddress(address: AdressSending) = repository.sendPersonalAddress(address)

}
