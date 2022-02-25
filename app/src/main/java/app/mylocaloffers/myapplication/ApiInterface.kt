package app.mylocaloffers.myapplication

import com.google.gson.JsonObject
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @POST("login")
    fun Login(@Body loginRequest: LoginRequest): Call<LoginResponse>?

    @POST("users")
    fun CreateUser(@Body createRequest: HashMap<String, JsonObject>): Call<LoginResponse>?

    @GET("home")
    fun Home(@Header("Authorization") token: String): Call<HomeResponse>?

    @PUT("users/{id}")
    fun editProfile(@Header("Authorization") token: String, @Path("id") id: Int, @Body createRequest: HashMap<String, JsonObject>): Call<UpdateData>?
    @DELETE("logout")
    fun LogOut(@Header("Authorization") token: String): Call<LoginResponse>?

}