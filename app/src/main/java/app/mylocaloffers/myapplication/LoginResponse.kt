package app.mylocaloffers.myapplication

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("errors")
    val errors: List<Any>? = null,

    @field:SerializedName("status")
    var status: String? = null,

    var code: Int? = null,
    var throwable: Throwable? = null
)

data class Data(

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("exp")
    val exp: String? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("token")
    val token: String? = null
)

data class User(

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone_number")
    val phoneNumber: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("email")
    val email: String? = null
)
