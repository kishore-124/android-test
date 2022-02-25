package app.mylocaloffers.myapplication

import com.google.gson.annotations.SerializedName

data class UpdateResponse(

	@field:SerializedName("data")
	val data: UpdateData? = null,

	@field:SerializedName("errors")
	val errors: List<Any?>? = null,

	@field:SerializedName("status")
	var status: String? = null,
	var throwable: Throwable? = null
)

data class UpdateUser(

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

data class UpdateData(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: UpdateUser? = null
)
