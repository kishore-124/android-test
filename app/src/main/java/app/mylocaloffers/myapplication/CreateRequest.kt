package app.mylocaloffers.myapplication

import com.google.gson.annotations.SerializedName

data class CreateRequest(

	@field:SerializedName("user")
	val user: CreateUser? = null
)

data class CreateUser(

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("city")
	var city: String? = null,

	@field:SerializedName("name")
	var name: String? = null,

	@field:SerializedName("phone_number")
	var phoneNumber: String? = null,

	@field:SerializedName("email")
	var email: String? = null
)
