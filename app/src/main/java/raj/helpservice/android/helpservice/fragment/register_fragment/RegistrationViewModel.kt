package raj.helpservice.android.helpservice.fragment.register_fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import raj.helpservice.android.helpservice.Repository
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.RegistrationUser

class RegistrationViewModel : ViewModel() {

    val selectedValue = MutableLiveData<Boolean>()

    val selectedCity = MutableLiveData<City>()

    val savedUser = MutableLiveData<RegistrationUser>()


    private val repository:Repository = Repository()


    fun getRegistrationUser(registrationUser: RegistrationUser) = repository.getUser(registrationUser)


    fun selectBooleanValue(boolean: Boolean){
        selectedValue.value = boolean
    }

    fun finishRegistration(user: RegistrationUser) = repository.finishRegistration(user)


    fun selectCityValue(city: City){
        selectedCity.value = city
    }

    fun selectdRegistrationUser(registrationUser: RegistrationUser){
        savedUser.value = registrationUser
    }


}
