package raj.helpservice.android.helpservice.activity

import android.arch.lifecycle.ViewModel
import raj.helpservice.android.helpservice.Repository
import raj.helpservice.android.helpservice.data.Auth

class LoginViewModel :ViewModel(){

    val repository = Repository()


    fun logIn(auth: Auth) = repository.login(auth)
}