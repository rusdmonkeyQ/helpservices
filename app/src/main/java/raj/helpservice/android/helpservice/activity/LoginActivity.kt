package raj.helpservice.android.helpservice.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.R
import raj.helpservice.android.helpservice.api.HelpServiceApi
import raj.helpservice.android.helpservice.data.Auth
import raj.helpservice.android.helpservice.data.RegistrationUser
import raj.helpservice.android.helpservice.spstorage.UserPreference

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    private val retrofit by lazy {
        HelpServiceApi.getApiService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel =  ViewModelProviders.of(this).get(LoginViewModel::class.java)


        login_button.setOnClickListener {
            val phoneNumber = phone_number.text.toString()
            val password = password_edit.text.toString()

            if (phoneNumber.isEmpty() || password.isEmpty()){
                Toast.makeText(baseContext,"Please Enter phone number and password",Toast.LENGTH_SHORT).show()
            }

            val auth = Auth()
            auth.loginid = phoneNumber
            auth.password = password
            viewModel.logIn(auth).observe(this, Observer {
               var registationUser = RegistrationUser()
               registationUser = it!!
                NetworkUtils.registrationUser= registationUser
               UserPreference.saveUser(registationUser,context = baseContext)
               if (it.userType == "1"){
                   val intent = Intent(this, ConsumerActivity::class.java)
                   startActivity(intent)
                   finish()
               }
               else if (it.userType == "2"){
                   val intent = Intent(this, VendorActivity::class.java)
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                   startActivity(intent)
                   finish()
               }else{
                   Toast.makeText(baseContext, it.displayMessage,Toast.LENGTH_LONG).show()

               }
           })


        }

    }


}
