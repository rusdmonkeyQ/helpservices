package raj.helpservice.android.helpservice.spstorage

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import raj.helpservice.android.helpservice.NetworkUtils
import raj.helpservice.android.helpservice.data.*


object UserPreference{

    private const val USER = "REGISTRATION_USER"
    private const val CITY = "REGISTRATION_CITY"
    private const val CITY_ID = "REGISTRATION_CITY_ID"
    private const val PROFFESSION = "PROFFESSIONS"
    private const val LANGUAGE = "LANGUAGE"
    fun saveUser(user: RegistrationUser, context: Context){
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = manager.edit()
        val savedJson = Gson().toJson(user)
        editor.putString(USER,savedJson)
        editor.apply()
    }

    fun getUser(context: Context):RegistrationUser?{
        var user: RegistrationUser? = null
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        user = Gson().fromJson(manager.getString(USER,null),RegistrationUser::class.java)
        return user
    }

    fun removeUser(context: Context){
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.remove(USER)
        editor.apply()
    }

    fun saveCities(list: ArrayList<City>, context: Context){
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = manager.edit()
        val savedJson = Gson().toJson(list)
        editor.putString(CITY,savedJson)
        editor.apply()
    }

    fun getCities(context: Context): ArrayList<City>? {
        var user: ArrayList<City>? = null
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        val type = object : TypeToken<ArrayList<City>>() {}.type
        user = Gson().fromJson(manager.getString(CITY,null),type)
        return user
    }
    fun saveProffesions(list: ArrayList<Profession>, context: Context){
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = manager.edit()
        val savedJson = Gson().toJson(list)
        editor.putString(PROFFESSION,savedJson)
        editor.apply()
    }

    fun getProfessions(context: Context): ArrayList<Profession>? {
        var user: ArrayList<Profession>? = null
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        val type = object : TypeToken<ArrayList<Profession>>() {}.type
        user = Gson().fromJson(manager.getString(PROFFESSION,null),type)
        return user
    }

    fun saveCityId(city: City, context: Context){
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = manager.edit()
        val savedJson = Gson().toJson(city)
        editor.putString(CITY_ID,savedJson)
        editor.apply()
    }

    fun getCityId(context: Context): City? {
        var city:City? = null
        val manager = PreferenceManager.getDefaultSharedPreferences(context)
        city = Gson().fromJson(manager.getString(CITY_ID,null),City::class.java)
        return city
    }
    fun getLanguages(): ArrayList<Language>{
        val type = object : TypeToken<ArrayList<Language>>() {}.type
        return Gson().fromJson(NetworkUtils.language,type)
    }

    fun getRateInterval(): ArrayList<RateInterval>{
        val type = object : TypeToken<ArrayList<RateInterval>>() {}.type
        return Gson().fromJson(NetworkUtils.serviceTime,type)
    }

    fun getDocumentModel(): ArrayList<DocumentNameModel>{
        val type = object : TypeToken<ArrayList<DocumentNameModel>>() {}.type
        return Gson().fromJson(raj.helpservice.android.helpservice.NetworkUtils.documentType,type)
    }


}
