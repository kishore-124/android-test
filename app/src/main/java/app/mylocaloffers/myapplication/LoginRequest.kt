package app.mylocaloffers.myapplication

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("email")
	var email: String? = null,

	@field:SerializedName("password")
	var password: String? = null,
)
