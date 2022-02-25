package app.mylocaloffers.myapplication

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var signUpResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    var signInResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    var deleteResponse: MutableLiveData<LoginResponse> = MutableLiveData()
    var updateProfileResponse: MutableLiveData<UpdateData> = MutableLiveData()
    var homeResponse: MutableLiveData<HomeResponse> = MutableLiveData()

    fun updateProfile(context: Context, createRequest: HashMap<String, JsonObject>) {
        val apiClient: ApiInterface = ApiClient.createService(ApiInterface::class.java, context)
        apiClient.editProfile(
            "Bearer " + SharedPrefranceHelper(context).getValue(
                SharedPrefranceHelper.token
            ),
            SharedPrefranceHelper(context).getValue(SharedPrefranceHelper.uid).toInt(),
            createRequest
        )?.enqueue(object : Callback<UpdateData> {
            override fun onResponse(
                call: Call<UpdateData>,
                response: Response<UpdateData>
            ) {
                if (response.isSuccessful) {
                    updateProfileResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UpdateData>, t: Throwable) {
                val registerResponse = UpdateData()
                updateProfileResponse.postValue(registerResponse)
            }
        })
    }


    fun signIn(loginRequest: LoginRequest, context: Context) {
        val apiClient: ApiInterface = ApiClient.createService(ApiInterface::class.java, context)
        apiClient.Login(loginRequest)?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if (response.isSuccessful) {
                    val registerResponse: LoginResponse = response.body()!!
                    registerResponse.status = "success"
                    registerResponse.code = 200
                    signInResponse.postValue(response.body())
                } else {
                    if (response.code() == 401) {
                        val registerResponse = LoginResponse()
                        registerResponse.status = "false"
                        registerResponse.code = 401
                        signInResponse.postValue(registerResponse)
                    } else {
                        val registerResponse = LoginResponse()
                        registerResponse.status = "false"
                        registerResponse.code = response.code()
                        signInResponse.postValue(registerResponse)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val registerResponse = LoginResponse()
                registerResponse.throwable = t
                signInResponse.postValue(registerResponse)
            }
        })
    }

    fun signUp(createRequest: HashMap<String, JsonObject>, context: Context) {
        val apiClient: ApiInterface = ApiClient.createService(ApiInterface::class.java, context)
        apiClient.CreateUser(createRequest)?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {


                if (response.isSuccessful) {
                    signUpResponse.postValue(response.body())
                } else if (response.code() == 401) {
                    val registerResponse = LoginResponse()
                    registerResponse.status = "false"
                    registerResponse.code = 401
                    signUpResponse.postValue(registerResponse)

                } else {
                    val registerResponse = LoginResponse()
                    registerResponse.status = "false"
                    registerResponse.code = response.code()
                    signUpResponse.postValue(registerResponse)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val registerResponse = LoginResponse()
                registerResponse.throwable = t
                signUpResponse.postValue(registerResponse)
            }
        })
    }

    fun logout(context: Context) {
        val apiClient: ApiInterface = ApiClient.createService(ApiInterface::class.java, context)
        apiClient.LogOut("Bearer " + SharedPrefranceHelper(context).getValue(SharedPrefranceHelper.token))
            ?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        deleteResponse.postValue(response.body())

                    } else if (response.code() == 401 || response.code() == 400) {
                        val registerResponse = LoginResponse()
                        registerResponse.status = "false"
                        registerResponse.code = 401
                        deleteResponse.postValue(registerResponse)

                    } else {
                        val registerResponse = LoginResponse()
                        registerResponse.status = "false"
                        registerResponse.code = response.code()
                        deleteResponse.postValue(registerResponse)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    val registerResponse = LoginResponse()
                    registerResponse.throwable = t
                    deleteResponse.postValue(registerResponse)
                }
            })
    }

    fun home(context: Context) {
        val apiClient: ApiInterface = ApiClient.createService(ApiInterface::class.java, context)
        apiClient.Home("Bearer " + SharedPrefranceHelper(context).getValue(SharedPrefranceHelper.token))
            ?.enqueue(object : Callback<HomeResponse> {
                override fun onResponse(
                    call: Call<HomeResponse>,
                    response: Response<HomeResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.code() == 200) {
                            homeResponse.postValue(response.body())
                        }
                    } else {
                        val registerResponse: HomeResponse = response.body()!!
                        homeResponse.postValue(registerResponse)
                    }
                }

                override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                    val registerResponse = HomeResponse()
                    registerResponse.throwable = t
                    homeResponse.postValue(registerResponse)
                }
            })

    }

}