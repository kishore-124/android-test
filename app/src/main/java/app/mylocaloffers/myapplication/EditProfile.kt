package app.mylocaloffers.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_edit_profile.*


class EditProfile : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        ic_back.setOnClickListener { finish() }
        loginViewModel = ViewModelProvider.NewInstanceFactory().create(LoginViewModel::class.java)
        et_name.setText(SharedPrefranceHelper(this).getValue(SharedPrefranceHelper.firstName.toString()))
        et_email.setText(SharedPrefranceHelper(this).getValue(SharedPrefranceHelper.emailId.toString()))
        et_mobile.setText(SharedPrefranceHelper(this).getValue(SharedPrefranceHelper.contact.toString()))
        et_city.setText(SharedPrefranceHelper(this).getValue(SharedPrefranceHelper.city.toString()))
        loginViewModel.updateProfileResponse.observe(this) {
            btn_go.visibility = View.VISIBLE
            progress_rating.visibility = View.GONE
            SharedPrefranceHelper(this).setValue(SharedPrefranceHelper.firstName,it.user?.name.toString())
            SharedPrefranceHelper(this).setValue(SharedPrefranceHelper.emailId,it.user?.email.toString())
            SharedPrefranceHelper(this).setValue(SharedPrefranceHelper.contact,it.user?.phoneNumber.toString())
            SharedPrefranceHelper(this).setValue(SharedPrefranceHelper.city,it.user?.city.toString())
            finish()
        }
        btn_go.setOnClickListener {
            var value = JsonObject()
            var jsonObject = HashMap<String, JsonObject>()
            value.addProperty("name", et_name.text.toString())
            value.addProperty("email", et_email.text.toString())
            value.addProperty("phone_number", et_mobile.text.toString())
            value.addProperty("city", et_city.text.toString())
            jsonObject["user"] = value
            btn_go.visibility = View.GONE
            progress_rating.visibility = View.VISIBLE
            loginViewModel.updateProfile(this, jsonObject)

        }
    }
}