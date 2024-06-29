package com.anmp.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anmp.myapplication.model.HobbyDatabase
import com.anmp.myapplication.model.User
import com.anmp.myapplication.model.UserDAO
import com.anmp.myapplication.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {
    val userLiveData = MutableLiveData<User>()
    val statusRegisterLiveData = MutableLiveData<String>()
    val statusLoginLiveData = MutableLiveData<String>()
    val statusUpdateProfileLiveData = MutableLiveData<String>()
    val statusUpdatePasswordLiveData = MutableLiveData<String>()

    private val userDao: UserDAO= buildDb(application).userDao()

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun registerUser(user: User) {
        launch {
            try {
                val userId = userDao.registerUser(user)
                if (userId > 0) {
                    statusRegisterLiveData.postValue("Registration successful")
                } else {
                    statusRegisterLiveData.postValue("Registration failed")
                }
            } catch (e: Exception) {
                statusRegisterLiveData.postValue("Error: ${e.message}")
            }
        }
    }

    fun loginUser(username: String, password: String) {
        launch {
            val user = userDao.loginUser(username, password)
            if (user != null) {
                userLiveData.postValue(user!!)
                statusLoginLiveData.postValue("Login successful")
            } else {
                statusLoginLiveData.postValue("Login failed")
            }
        }
    }

    fun updateUserProfile(id: Int, firstName: String, lastName: String) {
        launch {
            try {
                userDao.updateUserProfile(firstName, lastName, id)
                statusUpdateProfileLiveData.postValue("Profile updated successfully")
            } catch (e: Exception) {
                statusUpdateProfileLiveData.postValue("Error updating profile: ${e.message}")
            }
        }
    }


    fun updatePassword (userId: Int,newPassword: String) {
        launch {
            try {
                userDao.updateUserPassword(userId, newPassword)
                statusUpdatePasswordLiveData.postValue("Password updated successfully")
            } catch (e: Exception) {
                statusUpdatePasswordLiveData.postValue("Error updating password: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel() // Cancel all coroutines when ViewModel is cleared
    }


//    private val TAG = "volleyTag"
//    private var queue: RequestQueue? = null



//    fun register(username:String,firstName:String,lastName: String,email:String,password:String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/anmp/register.php"
//
//        val userData = JSONObject()
//        userData.put("username", username)
//        userData.put("first_name", firstName)
//        userData.put("last_name", lastName)
//        userData.put("email", email)
//        userData.put("password", password)
//
//        val stringRequest = object : StringRequest(
//            Method.POST, url,
//            Response.Listener { response ->
//                try {
//                    val jsonResponse = JSONObject(response)
//                    val status = jsonResponse.getString("status")
//
//
//                    if (status == "success") {
//                        statusRegisterLiveData.value = status
//
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    statusRegisterLiveData.value = "error"
//                }
//            },
//            Response.ErrorListener { error ->
//                error.printStackTrace()
//                statusRegisterLiveData.value = "error"
//            }) {
//            override fun getParams(): MutableMap<String, String> {
//                return jsonObjectToMap(userData).toMutableMap()
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//    fun login(username: String, password: String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/anmp/login.php"
//
//        val userData = JSONObject()
//        userData.put("username", username)
//        userData.put("password", password)
//
//        val stringRequest = object : StringRequest(
//            Method.POST, url,
//            Response.Listener { response ->
//                try {
//                    val jsonResponse = JSONObject(response)
//                    val status = jsonResponse.getString("status")
//                    statusLoginLiveData.value = status
//
//                    if (status == "success") {
//                        val userJson = jsonResponse.getJSONObject("user")
//
//                        val idAsString = userJson.getString("id")
//
//
//                        val id = idAsString.toInt()
//                        val username = userJson.getString("username")
//                        val firstName = userJson.getString("first_name")
//                        val lastName = userJson.getString("last_name")
//                        val email = userJson.getString("email")
//                        val password = userJson.getString("password")
//
//                        val loggedInUser = User(id, username, firstName, lastName, email, password)
//                        userLiveData.value = loggedInUser
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    statusLoginLiveData.value = "error"
//                }
//            },
//            Response.ErrorListener { error ->
//                error.printStackTrace()
//                statusLoginLiveData.value = "error"
//            }) {
//            override fun getParams(): MutableMap<String, String> {
//                return jsonObjectToMap(userData).toMutableMap()
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//    fun updateProfile(id:Int,firstName: String,lastName: String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/anmp/update_profile.php"
//
//        val userData = JSONObject()
//        userData.put("id", id)
//        userData.put("first_name", firstName)
//        userData.put("last_name",lastName)
//
//        val stringRequest = object : StringRequest(
//            Method.POST, url,
//            Response.Listener { response ->
//                try {
//                    val jsonResponse = JSONObject(response)
//                    val status = jsonResponse.getString("status")
//
//
//                    if (status == "success") {
//                        statusUpdateProfileLiveData.value=status
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    statusUpdateProfileLiveData.value = "error"
//                }
//            },
//            Response.ErrorListener { error ->
//                error.printStackTrace()
//                statusLoginLiveData.value = "error"
//            }) {
//            override fun getParams(): MutableMap<String, String> {
//                return jsonObjectToMap(userData).toMutableMap()
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//    fun updatePassword(id:Int, password: String) {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/anmp/update_password.php"
//
//        val userData = JSONObject()
//        userData.put("id", id)
//        userData.put("password", password)
//
//
//        val stringRequest = object : StringRequest(
//            Method.POST, url,
//            Response.Listener { response ->
//                try {
//                    val jsonResponse = JSONObject(response)
//                    val status = jsonResponse.getString("status")
//
//
//                    if (status == "success") {
//                        statusUpdatePasswordLiveData.value=status
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    statusUpdatePasswordLiveData.value= "error"
//                }
//            },
//            Response.ErrorListener { error ->
//                error.printStackTrace()
//                statusLoginLiveData.value = "error"
//            }) {
//            override fun getParams(): MutableMap<String, String> {
//                return jsonObjectToMap(userData).toMutableMap()
//            }
//        }
//
//        stringRequest.tag = TAG
//        queue?.add(stringRequest)
//    }
//
//    fun jsonObjectToMap(jsonObject: JSONObject): Map<String, String> {
//        val map = mutableMapOf<String, String>()
//        val keys = jsonObject.keys()
//        while (keys.hasNext()) {
//            val key = keys.next()
//            map[key] = jsonObject.getString(key)
//        }
//        return map
//    }

}