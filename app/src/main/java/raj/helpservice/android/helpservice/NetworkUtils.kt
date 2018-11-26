package raj.helpservice.android.helpservice

import android.content.Context
import android.net.ConnectivityManager
import raj.helpservice.android.helpservice.data.City
import raj.helpservice.android.helpservice.data.Profession
import raj.helpservice.android.helpservice.data.RegistrationUser

object NetworkUtils{
    fun isNetworkAvailable(context:Context): Boolean {
    val cm : ConnectivityManager  = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

    var choosen:City? = null
    var proffessions:ArrayList<Profession>? = null
    var pincode:String? = null
    var cities:ArrayList<City>? = null
    @Volatile var registrationUser:RegistrationUser? = null
    @Volatile var choosenRegistrationCity:City? = null
    val documentType = "[{\"documentID\":\"1\",\"name\":\"Aadhar Card\",\"selected\":\"N\"},{\"documentID\":\"2\",\"name\":\"Pan Card\",\"selected\":\"N\"},{\"documentID\":\"3\",\"name\":\"Voter Id\",\"selected\":\"N\"},{\"documentID\":\"4\",\"name\":\"Driving License\",\"selected\":\"N\"},{\"documentID\":\"5\",\"name\":\"Passport\",\"selected\":\"N\"},{\"documentID\":\"6\",\"name\":\"My Photo\",\"selected\":\"N\"},{\"documentID\":\"7\",\"name\":\"Registration\",\"selected\":\"N\"}]"
    val serviceTime = "[{\"serviceMasterID\":\"2\",\"serviceName\":\"1 Hour\",\"selected\":\"N\"},{\"serviceMasterID\":\"3\",\"serviceName\":\"2 Hours\",\"selected\":\"N\"},{\"serviceMasterID\":\"4\",\"serviceName\":\"Half Day\",\"selected\":\"N\"},{\"serviceMasterID\":\"5\",\"serviceName\":\"Full Day\",\"selected\":\"N\"}]"
    val language = "[\n" +
            "  {\n" +
            "    \"languageId\": \"1\",\n" +
            "    \"name\": \"English\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"2\",\n" +
            "    \"name\": \"Kannada\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"3\",\n" +
            "    \"name\": \"Hindi\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"4\",\n" +
            "    \"name\": \"Tamil\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"5\",\n" +
            "    \"name\": \"Telugu\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"6\",\n" +
            "    \"name\": \"Marathi\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"7\",\n" +
            "    \"name\": \"Konkani\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"8\",\n" +
            "    \"name\": \"Malayalam\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"9\",\n" +
            "    \"name\": \"Gujarati\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"10\",\n" +
            "    \"name\": \"Oriya\",\n" +
            "    \"selected\": \"N\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"languageId\": \"11\",\n" +
            "    \"name\": \"Assamese\",\n" +
            "    \"selected\": \"N\"\n" +
            "  }\n" +
            "]"


}